package openLab.device.packets;

import java.util.ArrayList;
import java.util.List;

import openLab.device.CommandCode;
import sun.misc.CRC16;

/**
 * A packet that holds sample data which may either be sent to the OpenLab
 * device or has been received from such.
 *
 * @author Harald Schloffer
 *
 */
public class SampleDataPacket extends GenericPacket {
	/**
	 * Holds the actual sample data
	 */
	private ArrayList<Byte> sampleData = new ArrayList<Byte>();
	private List<Byte> rawPacketData = null;
	/**
	 * Default constructor
	 */
	public SampleDataPacket() {
		setCmdCode(CommandCode.SAMPLE_DATA);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see openLab.device.packets.GenericPacket#getPayloadCrc()
	 */
	@Override
	protected CRC16 getPayloadCrc() {
		crc.reset();
		for (int i = 0; i < sampleData.size(); i++) {
			crc.update(sampleData.get(i).byteValue());
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
		rawPacketData.clear();
		rawPacketData = getRawHeaderData(true);

		/* START OF BINARY ENCODING */
		/* If sample data have been provided add them to packet data */
		if (sampleData.size() > 0)
		{
			rawPacketData.addAll(sampleData);
			/* Add CRC value of the payload data */
			int pldCRC = getPayloadCrc().value;
			rawPacketData.add(new Byte((byte) (pldCRC >>> 8)));
			rawPacketData.add(new Byte((byte) (pldCRC & 0xFF)));
		}

		return rawPacketData;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * openLab.device.packets.GenericPacket#payloadFromByteArray(byte[])
	 */
	@Override
	public void payloadFromByteArray(byte[] pldData) {
		/*
		 * Make sure to remove all old samples
		 */
		clearSampleData();
		/*
		 * Now add all samples within the byte array, if there are any...
		 */
		if (pldData != null) {
			for (int i = 0; i < pldData.length; i++) {
				sampleData.add(new Byte(pldData[i]));
			}
		}
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
}

/* EOF */