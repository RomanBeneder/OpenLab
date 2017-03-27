package openLab.device.packets;

import java.util.List;

import openLab.device.CommandCode;
import sun.misc.CRC16;

/**
 * @author Harald Schloffer
 *
 */
public class GetSoftwareVersionPacket extends GenericPacket {

	public GetSoftwareVersionPacket() {
		setCmdCode(CommandCode.GET_SOFTWARE_VERSION);
	}

	@Override
	public CRC16 getPayloadCrc() {
		/*
		 * This packet does not have any payload data.
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
		 * This packet does not have any payload data.
		 */
	}

	@Override
	public int getPldSize() {
		/*
		 * This packet does not have any payload data. Just return zero.
		 */

		return 0;
	}
}
