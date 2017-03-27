package openLab.device.packets;

import java.util.List;
import java.util.Vector;

import openLab.device.CommandCode;
import openLab.device.ErrorCode;
import sun.misc.CRC16;

/**
 * Indicates whether certain commands, as defined in the OpenLab communication
 * specification, did succeed or not. In case of failure there may be an error
 * code or an error description available.
 *
 * @author Harald Schloffer
 *
 */
public class AcknowledgePacket extends GenericPacket {

	ErrorCode errorCode = null;

	/**
	 * Simple constructor that needs only the information if this packet shall
	 * indicate a successful operation result or not.
	 *
	 * @param successfull
	 *            Set true if the operation to which this acknowledge will
	 *            belong was successful. Set to false if not.
	 */
	public AcknowledgePacket(boolean successfull) {
		if (successfull) {
			setCmdCode(CommandCode.ACKNOWLEDGE);
		} else {
			setCmdCode(CommandCode.NOT_ACKNOWLEDGED);
		}
	}

	/**
	 * Constructor that will accept additional information for this acknowledge
	 * packet in case the operation to which to response belongs to did not
	 * succeed.
	 *
	 * @param successfull
	 *            Set true if the operation to which this acknowledge will
	 *            belong was successful. Set to false if not.
	 * @param errorCode
	 *            {@link ErrorCode} that should be provided to the receiver.
	 */
	public AcknowledgePacket(boolean successfull, ErrorCode errorCode) {
		if (successfull) {
			setCmdCode(CommandCode.ACKNOWLEDGE);
		} else {
			setCmdCode(CommandCode.NOT_ACKNOWLEDGED);
			setErrorCode(errorCode);
		}
	}

	@Override
	public int getPldSize() {
		if (getCmdCode() == CommandCode.ACKNOWLEDGE) {
			return 0;
		} else if (getCmdCode() == CommandCode.NOT_ACKNOWLEDGED) {
			int pldSize = 0;
			/*
			 * If an error code has been set the packet contains one byte
			 */
			if (getErrorCode() != null) {
				pldSize += 1;
			}
			return pldSize;
		} else {
			return 0;
		}
	}

	/**
	 * @return the errorCode
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	protected CRC16 getPayloadCrc() {
		if (getCmdCode() == CommandCode.ACKNOWLEDGE) {
			/*
			 * This is just a positive acknowledge, no payload section.
			 */
			return crc;
		} else if (getCmdCode() == CommandCode.NOT_ACKNOWLEDGED) {
			/*
			 * This packet shall indicate not acknowledge so there may be some
			 * additional data to be transfered. Therefore a payload checksum
			 * needs to be calculated.
			 */
			if (getErrorCode() != null) {
				crc.reset();
				crc.update((byte) getErrorCode().getValue());
			}
			return crc;
		} else {
			/*
			 * Maybe command code got set to another value which is not valid
			 */
			return crc;
		}
	}

	@Override
	public List<Byte> getRawPacketData()
	{
		List<Byte> rawPld = new Vector<>(0);

		rawPld = getRawHeaderData(false);

		if (getCmdCode() == CommandCode.NOT_ACKNOWLEDGED)
		{
			/*
			 * If an error code has been set add it to the payload data to
			 * be transmitted.
			 */
			if (getErrorCode() != null)
			{
				rawPld.add(new Byte((byte) getErrorCode().getValue()));
				/*
				 * Calculate the payload CRC and add it to the data about to
				 * get transmitted.
				 */
				int crcVal = getPayloadCrc().value;
				rawPld.add(new Byte((byte) ((crcVal >>> 8) & 0xFF)));
				rawPld.add(new Byte((byte) (crcVal & 0xFF)));
			}
		}

		return rawPld;
	}

	@Override
	public void payloadFromByteArray(byte[] pldData) {
		if ((pldData != null) && (pldData.length == 1)) {
			setErrorCode(ErrorCode.fromInt(pldData[0]));
		}
		return;
	}
}
