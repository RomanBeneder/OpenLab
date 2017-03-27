package openLab.device.packets;

import java.util.List;

import openLab.device.CommandCode;
import sun.misc.CRC16;

/**
 *
 * @author Harald Schloffer
 */
public class GetHardwareVersionPacket extends GenericPacket {

	public GetHardwareVersionPacket() {
		setCmdCode(CommandCode.GET_HARDWARE_VERSION);
	}

	@Override
	protected CRC16 getPayloadCrc() {
		/*
		 * This packet does not have any payload data. Just return the crc as
		 * is.
		 */
		return crc;
	}

	@Override
	public List<Byte> getRawPacketData() {
		/*
		 * Just return the header data because this packet does not contain any
		 * payload data
		 */
		return getRawHeaderData(true);
	}

	@Override
	public void payloadFromByteArray(byte[] pldData) {
		/*
		 * This packet has no payload, so nothing to be done here.
		 */
	}

	@Override
	public int getPldSize() {
		/*
		 * This packet has no payload data so just return zero.
		 */
		return 0;
	}
}
