package openLab.device.packets;

import java.util.List;

import openLab.device.CommandCode;
import sun.misc.CRC16;

/**
 * @author Harald Schloffer
 */
public class HardwareVersion extends GenericPacket {

	int majorVersion = 0;
	int minorVersion = 0;
	int subVersion = 0;
	int reservedVersion = 0;

	/**
	 * Default constructor
	 */
	public HardwareVersion() {
		setCmdCode(CommandCode.HARDWARE_VERSION);
	}

	/**
	 * Constructor that sets initial values for the hardware version
	 *
	 * @param major
	 *            Major hardware version, valid range 0..255
	 * @param minor
	 *            Minor hardware version, valid range 0..255
	 * @param sub
	 *            Sub hardware version, valid range 0..255
	 */
	public HardwareVersion(int major, int minor, int sub) {
		setCmdCode(CommandCode.HARDWARE_VERSION);
		setMajorVersion(major);
		setMinorVersion(minor);
		setSubVersion(sub);
	}

	@Override
	public CRC16 getPayloadCrc() {
		crc.reset();
		crc.update((byte) getMajorVersion());
		crc.update((byte) getMinorVersion());
		crc.update((byte) getSubVersion());
		crc.update((byte) getReservedVersion());
		return null;
	}

	@Override
	public List<Byte> getRawPacketData()
	{
		List<Byte> rawData = getRawHeaderData(false);

		rawData.add(new Byte((byte) getMajorVersion()));
		rawData.add(new Byte((byte) getMinorVersion()));
		rawData.add(new Byte((byte) getSubVersion()));
		rawData.add(new Byte((byte) getReservedVersion()));
		int pldCRC = getPayloadCrc().value;
		rawData.add(new Byte((byte) (pldCRC >>> 8)));
		rawData.add(new Byte((byte) (pldCRC & 0xFF)));

		return rawData;
	}

	@Override
	public void payloadFromByteArray(byte[] pldData) {
		if (pldData.length == 4) {
			setMajorVersion(pldData[0]);
			setMinorVersion(pldData[1]);
			setSubVersion(pldData[2]);
			setReservedVersion(pldData[3]);
		} else {
			setCmdCode(CommandCode.INVALID_COMMAND);
		}
	}

	/**
	 * @return the reservedVersion
	 */
	public int getReservedVersion() {
		return reservedVersion;
	}

	/**
	 * @param reservedVersion
	 *            the reservedVersion to set
	 */
	public void setReservedVersion(int reservedVersion) {
		this.reservedVersion = reservedVersion;
	}

	@Override
	public int getPldSize() {
		return 4;
	}

	/**
	 * @return the majorVersion
	 */
	public int getMajorVersion() {
		return majorVersion;
	}

	/**
	 * @param majorVersion
	 *            the majorVersion to set
	 */
	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	/**
	 * @return the minorVersion
	 */
	public int getMinorVersion() {
		return minorVersion;
	}

	/**
	 * @param minorVersion
	 *            the minorVersion to set
	 */
	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	/**
	 * @return the subVersion
	 */
	public int getSubVersion() {
		return subVersion;
	}

	/**
	 * @param subVersion
	 *            the subVersion to set
	 */
	public void setSubVersion(int subVersion) {
		this.subVersion = subVersion;
	}
}
