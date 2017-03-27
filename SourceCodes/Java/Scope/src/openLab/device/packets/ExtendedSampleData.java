package openLab.device.packets;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import openLab.device.CommandCode;
import sun.misc.CRC16;

/**
 * A packet that holds sample data which may either be sent to the OpenLab
 * device or has been received from such.
 *
 * @author OpenLab
 *
 */
public class ExtendedSampleData extends GenericPacket {

	private long packetNumber = 0;
	private long sampleRate = 0;
	private short sampleBitwidth = 0;
	private int sampleCount = 0;

	private ArrayList<Byte> sampleData = new ArrayList<Byte>();

	/**
	 * Default constructor
	 */
	public ExtendedSampleData() {
		setCmdCode(CommandCode.EXTENDED_SAMPLE_DATA);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see openLab.device.packets.GenericPacket#getPayloadCrc()
	 */
	@Override @Deprecated
	protected CRC16 getPayloadCrc() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * openLab.device.packets.GenericPacket#getRawPacketData(fhtw.openLab.
	 * device.serial.CommunicationMode)
	 */
	@Override @Deprecated
	public List<Byte> getRawPacketData() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * openLab.device.packets.GenericPacket#payloadFromByteArray(byte[])
	 */
	@Override
	public void payloadFromByteArray(byte[] pldData) {
		if(pldData == null) {
			return;
		}

		/*
		 * Make sure to remove all old samples
		 */
		clearSampleData();

		ArrayList<Byte> tempPldData = new ArrayList<Byte>(pldData.length);
		for(int i=0;i<pldData.length;i++) {
			tempPldData.add(new Byte(pldData[i]));
		}

		byte[] tempPacketNumber = {0,0,0,0};
		for(int i=0;i<Integer.BYTES;i++) {
			tempPacketNumber[i] = tempPldData.remove(0);
		}
		ByteBuffer bbPacketNum = ByteBuffer.wrap(tempPacketNumber);
		setPacketNumber(bbPacketNum.getInt());

		byte[] tempSampleRate = {0,0,0,0};
		for(int i=0;i<Integer.BYTES;i++) {
			tempSampleRate[i] = tempPldData.remove(0);
		}
		ByteBuffer bbSampleRate = ByteBuffer.wrap(tempSampleRate);
		setSampleRate(bbSampleRate.getInt());
		//setPacketNumber(bbSampleRate.getInt());

		setSampleBitwidth(tempPldData.remove(0));

		byte[] tempSampleCount = {0,0};
		for(int i=0;i<Short.BYTES;i++) {
			tempSampleCount[i] = tempPldData.remove(0);
		}
		ByteBuffer bbSampleCount = ByteBuffer.wrap(tempSampleCount);
		setSampleCount(bbSampleCount.getShort());

		sampleData.addAll(tempPldData);
	}

	@Override
	public int getPldSize() {
		return sampleData.size();
	}

	/**
	 * Clear all currently stored samples
	 */
	public void clearSampleData() {
		sampleData.clear();
	}

	/**
	 * Adds the provided sample data to this packets payload
	 *
	 * @param samples
	 *            Samples to be added.
	 */
	public void addSampleData(List<Byte> samples) {
		if ((samples != null) && (samples.size() > 0)) {
			sampleData.addAll(samples);
		}
	}

	/**
	 * Provides direct access to the sample data vector
	 *
	 * @return Sample data
	 */
	public ArrayList<Byte> getSampleData() {
		return sampleData;
	}

	/**
	 * @return the packetNumber
	 */
	public long getPacketNumber() {
		return packetNumber;
	}

	/**
	 * @param packetNumber the packetNumber to set
	 */
	public void setPacketNumber(long packetNumber) {
		this.packetNumber = packetNumber;
	}

	/**
	 * @return the sampleRate
	 */
	public long getSampleRate() {
		return sampleRate;
	}

	/**
	 * @param sampleRate the sampleRate to set
	 */
	public void setSampleRate(long sampleRate) {
		this.sampleRate = sampleRate;
	}

	/**
	 * @return the sampleBitwidth
	 */
	public short getSampleBitwidth() {
		return sampleBitwidth;
	}

	/**
	 * @param sampleBitwidth the sampleBitwidth to set
	 */
	public void setSampleBitwidth(short sampleBitwidth) {
		this.sampleBitwidth = sampleBitwidth;
	}

	/**
	 * @return the sampleCount
	 */
	public int getSampleCount() {
		return sampleCount;
	}

	/**
	 * @param sampleCount the sampleCount to set
	 */
	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}
}

/* EOF */