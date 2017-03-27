package openLab.device.packets;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import openLab.device.CommandCode;
import sun.misc.CRC16;

/**
 * Packet to transmit the "Set Time Base" command to OpenLab devices.
 *
 * @author Harald Schloffer
 *
 */
public class SetTimeBasePacket extends GenericPacket {

	protected long samplesPerSecond = 0;

	/**
	 * Default constructor
	 */
	public SetTimeBasePacket() {
		setCmdCode(CommandCode.SET_TIME_BASE);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see openLab.device.packets.GenericPacket#getPldSize()
	 */
	@Override
	public int getPldSize() {
		return Integer.BYTES;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see openLab.device.packets.GenericPacket#getPayloadCrc()
	 */
	@Override
	protected CRC16 getPayloadCrc() {
		byte[] bytes = ByteBuffer.allocate(Integer.BYTES).putInt((int) getSamplesPerSecond()).array();
		crc.reset();
		for (int i = 0; i < bytes.length; i++) {
			crc.update(bytes[i]);
		}
		return crc;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * openLab.device.packets.GenericPacket#getRawPacketData(fhtw.openLab.
	 * device.serial.CommunicationMode)
	 */
	@Override
	public List<Byte> getRawPacketData()
	{
		List<Byte> rawData = getRawHeaderData(true);

		byte[] bytes = ByteBuffer.allocate(Integer.BYTES).putInt((int) getSamplesPerSecond()).array();
		for (int i = 0; i < bytes.length; i++) {
			rawData.add(new Byte(bytes[i]));
		}
		/*
		 * Add CRC value of the payload data
		 */
		int pldCRC = getPayloadCrc().value;
		rawData.add(new Byte((byte) (pldCRC >>> 8)));
		rawData.add(new Byte((byte) (pldCRC & 0xFF)));

		return rawData;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * openLab.device.packets.GenericPacket#payloadFromByteArray(byte[])
	 */
	@Override
	public void payloadFromByteArray(byte[] pldData) {
		if ((pldData != null) && (pldData.length == 4)) {
			ByteArrayInputStream inB = new ByteArrayInputStream(pldData);
			DataInputStream inData = new DataInputStream(inB);
			try {
				setSamplesPerSecond((inData.readInt()));
			} catch (IOException e) {
				setSamplesPerSecond(0);
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the samplesPerSecond
	 */
	public long getSamplesPerSecond() {
		return samplesPerSecond;
	}

	/**
	 * @param samplesPerSecond
	 *            the samplesPerSecond to set
	 */
	public void setSamplesPerSecond(long samplesPerSecond) {
		this.samplesPerSecond = samplesPerSecond;
	}
}
