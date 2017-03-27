package openLab.device.packets;

import java.nio.ByteBuffer;
import java.util.List;

import openLab.device.CommandCode;
import openLab.device.TriggerMode;
import openLab.device.TriggerType;
import sun.misc.CRC16;

/**
 * @author Harald Schloffer
 *
 */
public class SetTriggerSettings extends GenericPacket {

	public final int PAYLOAD_SIZE = 7;
	private final short BIT_MASK_TRIGGER_TYPE = 0x1F;
	private final short BIT_MASK_TRIGGER_MODE = 0xE0;

	protected TriggerType triggerType = TriggerType.DISABLE;
	protected TriggerMode triggerMode = TriggerMode.AUTO;
	protected int triggerLevel = 0;
	protected long holdOff = 0;

	/**
	 * Default constructor
	 */
	public SetTriggerSettings() {
		setCmdCode(CommandCode.SET_TRIGGER_SETTINGS);
	}

	/**
	 * Constructor that accepts initial values.
	 *
	 * @param triggerType
	 * 			{@link TriggerType} to bet set
	 * @param triggerMode
	 * 			{@link TriggerMode} to bet set
	 * @param triggerLevel
	 * 			 Trigger level within the ADC range
	 * @param holdoff
	 * 	 	     Dead time in micro seconds between to
	 * 			 consecutive trigger events to be set
	 */
	public SetTriggerSettings(TriggerType triggerType,TriggerMode triggerMode,int triggerLevel,long holdOff) {
		setCmdCode(CommandCode.SET_TRIGGER_SETTINGS);
		setTriggerType(triggerType);
		setTriggerMode(triggerMode);
		setTriggerLevel(triggerLevel);
		setHoldOff(holdOff);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see openLab.device.packets.GenericPacket#getPldSize()
	 */
	@Override
	public int getPldSize() {
		return PAYLOAD_SIZE;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see openLab.device.packets.GenericPacket#getPayloadCrc()
	 */
	@Override
	protected CRC16 getPayloadCrc() {
		crc.reset();

		crc.update((byte) mergeTriggerModeType());
		/*
		 * The next field in the payload data section is the trigger level which
		 * is a short (16bit/2byte) value. For this we first need to convert the
		 * value into an byte array in order to update the CRC.
		 */
		byte[] bytes = ByteBuffer.allocate(Short.BYTES).putShort((short) getTriggerLevel()).array();
		for (int i = 0; i < bytes.length; i++) {
			crc.update(bytes[i]);
		}

		bytes = ByteBuffer.allocate(Integer.BYTES).putInt((int) getHoldOff()).array();
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
	public List<Byte> getRawPacketData() {
		List<Byte> rawData = getRawHeaderData(true);

		rawData.add((byte)mergeTriggerModeType());
		/*
		 * The next field in the payload data section is the trigger level
		 * which is a short (16bit/2byte) value. For this we first need to
		 * convert the value into an byte array in order to update the CRC.
		 */
		byte[] bytes = ByteBuffer.allocate(Short.BYTES).putShort((short) getTriggerLevel()).array();
		for (int i = 0; i < bytes.length; i++) {
			rawData.add(new Byte(bytes[i]));
		}
		/*
		 * Same as before for the dead time value.
		 */
		bytes = ByteBuffer.allocate(Integer.BYTES).putInt((int) getHoldOff()).array();
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
	@Override @Deprecated
	public void payloadFromByteArray(byte[] pldData) {
		return;
	}

	private short mergeTriggerModeType() {
		short tempTriggerType = (short) (getTriggerType().getValue() & (BIT_MASK_TRIGGER_TYPE));
		short tempTriggerMode = (short) ((getTriggerMode().getValue()<<5) & (BIT_MASK_TRIGGER_MODE));
		short triggerTypeMode = (short) (tempTriggerType | tempTriggerMode);

		return triggerTypeMode;
	}

	/**
	 * @return the triggerType
	 */
	public TriggerType getTriggerType() {
		return triggerType;
	}

	/**
	 * @param triggerType the triggerType to set
	 */
	public void setTriggerType(TriggerType triggerType) {
		this.triggerType = triggerType;
	}

	/**
	 * @return the triggerMode
	 */
	public TriggerMode getTriggerMode() {
		return triggerMode;
	}

	/**
	 * @param triggerMode the triggerMode to set
	 */
	public void setTriggerMode(TriggerMode triggerMode) {
		this.triggerMode = triggerMode;
	}

	/**
	 * @return the triggerLevel
	 */
	public int getTriggerLevel() {
		return triggerLevel;
	}

	/**
	 * @param triggerLevel the triggerLevel to set
	 */
	public void setTriggerLevel(int triggerLevel) {
		this.triggerLevel = triggerLevel;
	}

	/**
	 * @return the holdOff
	 */
	public long getHoldOff() {
		return holdOff;
	}

	/**
	 * @param holdOff the holdOff to set
	 */
	public void setHoldOff(long holdOff) {
		this.holdOff = holdOff;
	}


}
