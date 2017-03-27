/**
 * @author Harald Schloffer
 */
package openLab.device.packets;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

import openLab.device.CommandCode;
import openLab.device.DeviceType;
import sun.misc.CRC16;

/**
 * This class is provides functions to parse input data to reconstruct valid
 * packets according to the OpenLab protocol specification. If the parsing
 * yields a valid packet it will be created and returned to the caller.
 *
 * @author Harald Schloffer
 *
 */
public class PacketFactory
{
	private static DataInputStream dataReader = null;
	private static GenericPacket packet = null;
	private static boolean CRCisValid = false;

	private static byte[] receivedHeaderBytes = new byte[7];
	private static CRC16 crcCheck = new CRC16();

	//Debug purpose only!
	private static long crcErrorCnt = 0;
	private static long nackCnt = 0;

	/**
	 * Default constructor
	 */
	public PacketFactory() {

	}

	/**
	 * This function tries to reconstruct a valid OpenLab packet according to
	 * the protocol specification from an {@link InputStream} taking the
	 * selected {@link CommunicationMode} into account. If a valid packet has
	 * been reconstructed by parsing the data provided by the {@link InutStream}
	 * object it will be returned to the caller. If something went wrong it will
	 * return null.
	 *
	 * @param inStream
	 *            {@link InputStream} that will provide data that should be
	 *            parsed to reconstruct an OpenLab packet.
	 * @param mode
	 *            {@link CommunicationMode} to be used for parsing.
	 * @return Reconstructed packet if parsing did not detect any errors. If
	 *         parsing fails null will be returned. Make sure to always check if
	 *         the returned reference is null or not.
	 */
	public static GenericPacket fromStream(InputStream inStream) {
		if (inStream != null) {
			return PacketFactory.fromStreamBinary(inStream);
		}
		return null;
	}

	/**
	 * Implements the data parsing for {@link CommunicationMode} binary.
	 *
	 * @param inStream
	 *            {@link InputStream} to read data from
	 * @return Returns a valid OpenLab packet if the parsing was successful,
	 *         otherwise null.
	 */
	protected static GenericPacket fromStreamBinary(InputStream inStream)
	{
		dataReader = new DataInputStream(inStream);
		crcCheck.reset();

		try {			
			while(true) {
				receivedHeaderBytes[0] = (byte) dataReader.readUnsignedByte();

				if((receivedHeaderBytes[0] >= 8 && receivedHeaderBytes[0] <= 11) || (receivedHeaderBytes[0] >= 126 && receivedHeaderBytes[0] <= 127)) {
					crcCheck.update(receivedHeaderBytes[0]);

					receivedHeaderBytes[1] = (byte) dataReader.readUnsignedByte();

					if(receivedHeaderBytes[1] >= 0 && receivedHeaderBytes[1] <= 1) {
						crcCheck.update(receivedHeaderBytes[1]);

						receivedHeaderBytes[2] = (byte) dataReader.readUnsignedByte();

						if(receivedHeaderBytes[2] >= 0 && receivedHeaderBytes[2] <= 1) {
							crcCheck.update(receivedHeaderBytes[2]);
							break;
						} 
					}					
				}
				crcCheck.reset();
				continue;
			}	

			/*
			 * First we need to receive the packet header which holds crucial
			 * information like the packet type, payload size and other generic
			 * informations. To ease checksum calculation all data will first be
			 * put into an byte array.
			 */

			/*
			 * The last two bytes will contain the header checksum, so we need
			 * not to receive them yet.
			 */
			for (int i = 3; i < receivedHeaderBytes.length - 2; i++) {
				receivedHeaderBytes[i] = (byte) dataReader.readUnsignedByte();
				crcCheck.update(receivedHeaderBytes[i]);
			}			

			/*
			 * Now get the two remaining bytes holding the header checksum
			 */
			int crcReceived = dataReader.readUnsignedShort();			
			//			System.out.println("CRC GUI: " + crcCheck.value +  " CRC FPGA: " + crcReceived );			
			if (crcReceived == crcCheck.value) {

				/*
				 * OK! Received CRC and calculated CRC match. Now the received
				 * bytes need to be separated into the individual header
				 * information values. For this we will use the DataInputStream
				 * on the byte array.
				 */
				CommandCode cmdCode = CommandCode.fromInt(receivedHeaderBytes[0]);
				DeviceType devType = DeviceType.fromInt(receivedHeaderBytes[1]);
				int chanNumber = receivedHeaderBytes[2];
				int pldSize = ((receivedHeaderBytes[3]) & 0xFF) << 8;
				pldSize |= (receivedHeaderBytes[4]) & 0xFF;
				CRCisValid = true;
				/*
				 * Now receive payload data if some is expected
				 */
				byte[] pldData = null;
				if (pldSize > 0) {
					DataInputStream inData = new DataInputStream(inStream);
					pldData = new byte[pldSize];
					crcCheck.reset();
					try {
						for (int i = 0; i < pldSize; i++) {
							pldData[i] = (byte) inData.readUnsignedByte();
							crcCheck.update(pldData[i]);
						}
						/*
						 * Check if calculated CRC for the received data matches
						 * the sent CRC
						 */
						if (crcCheck.value != inData.readUnsignedShort()) {
							CRCisValid = false;
						}
					} catch (IOException e) {
						CRCisValid = false;
						e.printStackTrace();
					}
				}
				if (CRCisValid) {
					/*
					 * Next determine what type of packet shall actually get
					 * created
					 */
					switch (cmdCode) {
					case ACKNOWLEDGE:
						packet = new AcknowledgePacket(true);
						packet.setChannelNum(chanNumber);
						packet.setDeviceType(devType);
						break;
					case NOT_ACKNOWLEDGED:
						packet = new AcknowledgePacket(false);
						packet.payloadFromByteArray(pldData);
						packet.setChannelNum(chanNumber);
						packet.setDeviceType(devType);
						System.err.println("NOT ACKNOWLEDGED: " + (nackCnt++));
						break;
					case GET_HARDWARE_VERSION:
						break;
					case GET_SOFTWARE_VERSION:
						break;
					case HARDWARE_VERSION:
						packet = new HardwareVersion();
						packet.payloadFromByteArray(pldData);
						packet.setChannelNum(chanNumber);
						packet.setDeviceType(devType);
						break;
					case INVALID_COMMAND:
						break;
					case SAMPLE_DATA:
						packet = new SampleDataPacket();
						packet.payloadFromByteArray(pldData);
						packet.setChannelNum(chanNumber);
						packet.setDeviceType(devType);
						break;
					case EXTENDED_SAMPLE_DATA:
						packet = new ExtendedSampleData();
						packet.payloadFromByteArray(pldData);
						packet.setChannelNum(chanNumber);
						packet.setDeviceType(devType);
						break;
					case SET_SAMPLING_MODE:
						break;
					case SET_CHANNEL_SETTINGS:
						break;
					case SET_LOOP_BACK:
						break;
					case SET_TRIGGER_SETTINGS:
						break;
					case SET_TIME_BASE:
						break;
					case SOFTWARE_VERSION:
						packet = new SoftwareVersion();
						packet.payloadFromByteArray(pldData);
						packet.setChannelNum(chanNumber);
						packet.setDeviceType(devType);
						break;
					default:
						break;
					}
				} else {
					Date dateX = new Date();
					System.err.println("DEVICE PAYLOAD - CRC-ERROR cnt: "+(crcErrorCnt++)+" @: "+ new Timestamp(dateX.getTime()));
					packet = null;
				}
			} else {
				Date dateX = new Date();
				System.err.println("DEVICE HEADER - CRC-ERROR cnt: "+(crcErrorCnt++)+" @: "+ new Timestamp(dateX.getTime()));
				packet = null;
			}
		} catch (Exception ex) {
			/*
			 * Something went wrong during reconstruction. The exact cause does
			 * not really matter because we can't provide a new packet anyways.
			 */
			packet = null;
		}
		return packet;
	}
}