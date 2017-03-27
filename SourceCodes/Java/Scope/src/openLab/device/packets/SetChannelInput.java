package openLab.device.packets;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

import openLab.device.CommandCode;
import openLab.device.CouplingType;
import sun.misc.CRC16;

/**
 * A packet that holds the channel configuration information as specified within
 * the OpenLab communication protocol specification.
 *
 * @author Harald Schloffer
 *
 */
public class SetChannelInput extends GenericPacket {

	protected int inputGain = 1;
	protected CouplingType coupling = CouplingType.COUPLING_TYPE_OFF;

	/**
	 * Default constructor
	 */
	public SetChannelInput() {
		setCmdCode(CommandCode.SET_CHANNEL_SETTINGS);
	}

	/**
	 * Constructor that accepts initial values for the voltage scaling and
	 * coupling type.
	 *
	 * @param inputGain
	 *            Voltage scaling to set in volts.
	 * @param coupling
	 *            {@link CouplingType} to set
	 */
	public SetChannelInput(int inputGain, CouplingType coupling) {
		setCmdCode(CommandCode.SET_CHANNEL_SETTINGS);
		setInputGain(inputGain);
		setCoupling(coupling);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see openLab.device.packets.GenericPacket#getPldSize()
	 */
	@Override
	public int getPldSize() {
		return 2;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see openLab.device.packets.GenericPacket#getPayloadCrc()
	 */
	@Override
	protected CRC16 getPayloadCrc() {
		crc.reset();
		crc.update((byte) getInputGain());
		crc.update((byte) (getCoupling().getValue()));
		return crc;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see openLab.device.packets.GenericPacket#getRawPacketData(fhtw.openLab.
	 * device.serial.CommunicationMode)
	 */
	@Override
	public List<Byte> getRawPacketData()
	{
		List<Byte> rawData = getRawHeaderData(true);

		rawData.add(new Byte((byte) getInputGain()));
		rawData.add(new Byte((byte) getCoupling().getValue()));
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
	 * @see openLab.device.packets.GenericPacket#payloadFromByteArray(byte[])
	 */
	@Override
	public void payloadFromByteArray(byte[] pldData) {
		if ((pldData != null) && (pldData.length == 2)) {
			DataInputStream inData = new DataInputStream(new ByteArrayInputStream(pldData));
			try {
				setInputGain((inData.readByte()));
				setCoupling(CouplingType.fromInt(inData.readByte()));
			} catch (IOException e) {
				setInputGain(0);
				setCoupling(CouplingType.COUPLING_TYPE_OFF);
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return The currently set input gain step. This depends on the actual
	 *         device used so take care.
	 */
	public int getInputGain() {
		return inputGain;
	}

	/**
	 * @param gainStep
	 *            The input gain stepping to be set. Valid values range from 0
	 *            to 9.
	 */
	public void setInputGain(int gainStep) {
		this.inputGain = gainStep;
	}

	/**
	 * @return the coupling
	 */
	public CouplingType getCoupling() {
		return coupling;
	}

	/**
	 * @param coupling
	 *            the coupling to set
	 */
	public void setCoupling(CouplingType coupling) {
		this.coupling = coupling;
	}

}
