package openLab.device.packets;

import java.nio.ByteBuffer;
import java.util.List;

import openLab.device.CommandCode;
import openLab.device.SamplingMode;
import sun.misc.CRC16;

/**
 * @author OpenLab
 *
 */
public class SetSamplingMode extends GenericPacket {

	public final int PAYLOAD_SIZE = 5;

	protected SamplingMode samplingMode = SamplingMode.REALTIME_SAMPLING;
	protected int samplesAcqRound = 0;
	protected int acquisitionRound = 0;

	/**
	 * Default constructor
	 */
	public SetSamplingMode() {
		setCmdCode(CommandCode.SET_SAMPLING_MODE);
	}

	/**
	 * Constructor that accepts initial values.
	 *
	 * @param samplingMode
	 * 			{@link SamplingMode} to bet set
	 *
	 * @param samplesAcqRound
	 * 			 Number of samples that shall be taken per signal
	 * 			 acquisition loop
	 *
	 * @param acquisitionRound
	 *			 Number of acquisition rounds. In case of realtime
 	 *			 sampling this will be one, if equivalent time sampling
	 *			 is requested this field determines how many sampling
	 *			 loops shall be performed
	 *
	 */
	public SetSamplingMode(SamplingMode samplingMode,int samplesAcqRound,int acquisitionRound) {
		setCmdCode(CommandCode.SET_SAMPLING_MODE);
		setSamplingMode(samplingMode);
		setSamplesAcqRound(samplesAcqRound);
		setAcquisitionRound(acquisitionRound);
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

		crc.update((byte) getSamplingMode().getValue());
		/*
		 * The next field in the payload data section is the samples per acquisition round which
		 * is a short (16bit/2byte) value. For this we first need to convert the
		 * value into an byte array in order to update the CRC.
		 */
		byte[] bytes = ByteBuffer.allocate(Short.BYTES).putShort((short) getSamplesAcqRound()).array();
		for (int i = 0; i < bytes.length; i++) {
			crc.update(bytes[i]);
		}

		bytes = ByteBuffer.allocate(Short.BYTES).putShort((short) getAcquisitionRound()).array();
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

		rawData.add((byte)getSamplingMode().getValue());

		/*
		 * The next field in the payload data section is the trigger level
		 * which is a short (16bit/2byte) value. For this we first need to
		 * convert the value into an byte array in order to update the CRC.
		 */
		byte[] bytes = ByteBuffer.allocate(Short.BYTES).putShort((short) getSamplesAcqRound()).array();
		for (int i = 0; i < bytes.length; i++) {
			rawData.add(new Byte(bytes[i]));
		}

		bytes = ByteBuffer.allocate(Short.BYTES).putShort((short) getAcquisitionRound()).array();
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

	/**
	 * @return the samplingMode
	 */
	public SamplingMode getSamplingMode() {
		return samplingMode;
	}

	/**
	 * @param samplingMode the samplingMode to set
	 */
	public void setSamplingMode(SamplingMode samplingMode) {
		this.samplingMode = samplingMode;
	}

	/**
	 * @return the samplesAcqRound
	 */
	public int getSamplesAcqRound() {
		return samplesAcqRound;
	}

	/**
	 * @param samplesAcqRound the samplesAcqRound to set
	 */
	public void setSamplesAcqRound(int samplesAcqRound) {
		this.samplesAcqRound = samplesAcqRound;
	}

	/**
	 * @return the acquisitionRound
	 */
	public int getAcquisitionRound() {
		return acquisitionRound;
	}

	/**
	 * @param acquisitionRound the acquisitionRound to set
	 */
	public void setAcquisitionRound(int acquisitionRound) {
		this.acquisitionRound = acquisitionRound;
	}


}
