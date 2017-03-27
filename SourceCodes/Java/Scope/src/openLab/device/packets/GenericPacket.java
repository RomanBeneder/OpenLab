/**
 *
 */
package openLab.device.packets;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import openLab.device.CommandCode;
import openLab.device.DeviceType;
import sun.misc.CRC16;

/**
 * Wraps OpenLab communication messages into an object. This is a general class
 * it may be sub classed to provide more fine grained control over specific
 * fields of certain packets.
 *
 * @author Harald Schloffer
 *
 */
public abstract class GenericPacket {

	protected CommandCode cmdCode = CommandCode.INVALID_COMMAND;
	protected int channelNum = 0;
	protected DeviceType deviceType = DeviceType.DEVICE;
	protected CRC16 crc = new CRC16();
	protected Date timeStamp = Date.from(Instant.now());

	private ArrayList<Byte> rawHeaderData = new ArrayList<Byte>(0);
	/**
	 * @return the cmdCode
	 */
	public CommandCode getCmdCode() {
		return cmdCode;
	}

	/**
	 * @param cmdCode
	 *            the cmdCode to set
	 */
	public void setCmdCode(CommandCode cmdCode) {
		this.cmdCode = cmdCode;
	}

	/**
	 * @return the channelNum
	 */
	public int getChannelNum() {
		return channelNum;
	}

	/**
	 * @param channelNum
	 *            the channelNum to set
	 */
	public void setChannelNum(int channelNum) {
		this.channelNum = channelNum;
	}

	/**
	 * @return the deviceType
	 */
	public DeviceType getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the size of the payload in bytes
	 */
	public abstract int getPldSize();

	/**
	 * Packs the header information into a list of Bytes containing payload size
	 * and header checksum.
	 *
	 * @param mode
	 *            Intended communication mode for which the formating shall be
	 *            done.
	 * @param isCommand
	 *            Whether this packet is a command or a reply
	 * @return Packed header data.
	 */
	protected List<Byte> getRawHeaderData(boolean isCommand)
	{
		rawHeaderData.clear();

		/* At least enough space to hold the header bytes */
		ByteArrayOutputStream bout = new ByteArrayOutputStream(7);
		DataOutputStream out = new DataOutputStream(bout);
		try
		{
			/* Add command or reply code */
			out.writeByte(getCmdCode().getValue());
			/* Add device type */
			out.writeByte(getDeviceType().getValue());
			/* Add channel number */
			out.writeByte(getChannelNum());
			/* Add payload size */
			out.writeShort(getPldSize());
			/* Add header CRC */
			out.writeShort(getHeaderCrc().value);
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		byte[] hdrData = bout.toByteArray();

		for (int i = 0; i < hdrData.length; i++) {
			/* Copy header data the Byte vector */
			rawHeaderData.add(new Byte(hdrData[i]));
		}

		return rawHeaderData; /* Return the packed data */
	}

	/**
	 * @return The header checksum
	 */
	protected CRC16 getHeaderCrc() {
		ByteArrayOutputStream bout = new ByteArrayOutputStream(5);
		DataOutputStream out = new DataOutputStream(bout);
		try {
			/* Add command or reply code */
			out.writeByte(getCmdCode().getValue());
			/* Add device type */
			out.writeByte(getDeviceType().getValue());
			/* Add channel number */
			out.writeByte(getChannelNum());
			/* Add payload size */
			out.writeShort(getPldSize());
		} catch (IOException e) {
			e.printStackTrace();
		}
		crc.reset(); /* Reset CRC calculator */
		byte[] hdrData = bout.toByteArray();
		for (int i = 0; i < hdrData.length; i++) {
			/* Update CRC with header data bytes */
			crc.update(hdrData[i]);
		}
		return crc;
	}

	/**
	 * @return the timeStamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/*
	 * Now follow all abstract functions that need to be implemented by the
	 * child classes
	 */

	/**
	 *
	 * @return The payload checksum
	 */
	protected abstract CRC16 getPayloadCrc();

	/**
	 * Prepares the packet data for transmission. The whole packet will be
	 * packed into a list of bytes in the correct format including the header
	 * CRC ready for transmission.
	 *
	 * @param mode
	 *            The communication mode to be used to transmit this packet data
	 * @return All packet data packed into a list of bytes ready for
	 *         transmission
	 */
	public abstract List<Byte> getRawPacketData();

	/**
	 * Extracts the data bytes provided in the byte array to reconstruct the
	 * child packets data
	 *
	 * @param inStream
	 *            Payload data provided as byte array
	 */
	public abstract void payloadFromByteArray(byte[] pldData);

}
