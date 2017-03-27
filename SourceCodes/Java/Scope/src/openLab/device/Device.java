/**
 *
 */
package openLab.device;

import java.util.List;

import openLab.device.packets.GenericPacket;

/**
 * Interface definition for the OpenLab device. All child classes that implement
 * functions for a specific physical interface shall be inherited from this.
 *
 * @author Harald Schloffer
 *
 */
public abstract class Device {
	protected DeviceEventListener eventListener = null;

	/**
	 * @return the eventListener
	 */
	public DeviceEventListener getEventListener() {
		return eventListener;
	}

	/**
	 * @param eventListener
	 *            the eventListener to set
	 */
	public void setEventListener(DeviceEventListener eventListener) {
		this.eventListener = eventListener;
	}

	/**
	 * Connect to serial port defined by the provided path string.
	 *
	 * @param path
	 *            Path string identifying the intended serial port.
	 * @Return Returns true if connection attempt was successful, otherwise
	 *         false
	 */
//	public abstract boolean connect(String path);

	/**
	 * Disconnect from currently opened port.
	 * 
	 * @param Set true if all analog channels have to be switched off
	 *         
	 */
	public abstract boolean disconnect(boolean switchOffChannels);

	/**
	 * Just checks if a serial port is currently opened and used for
	 * communication.
	 *
	 * @return True if a serial port has been successfully opened, false
	 *         otherwise.
	 */
	public abstract boolean isConnected();

	/**
	 * Sends a packet to the OpenLab device using the currently set
	 * communication mode.
	 *
	 * @param packet
	 *            Packet to be transmitted
	 * @return Returns true if transmission was successful else false
	 */
	public abstract boolean sendPacket(GenericPacket packet);

	/**
	 * Receive a packet from the OpenLab device. Take care this function may
	 * block until a new packet is available.
	 *
	 * @param timeOutMs
	 *            Sets the time out in milliseconds to wait for a new packet. It
	 *            no packet could be received until the time out is reached null
	 *            be be returned.
	 * @return Received packet or null.
	 */
	public abstract GenericPacket receivePacket(int timeOutMs);

	/**
	 * Tries to receive the hardware version from the OpenLab device
	 *
	 * @return Returns the hardware version as a string
	 *         </ul>
	 */
	public abstract boolean getHardwareVersionString();

	/**
	 * Tries to receive the software version from the OpenLab device
	 *
	 * @return Returns the software version as a string
	 */
	public abstract boolean getSoftwareVersionString();

	/**
	 * Probes if the connected device may likely be an OpenLab device.
	 * 
	 * @param autoConnect 
	 * 				Set if auto connect should be performed.
	 * 
	 * @return True if the device may be an OpenLab device, otherwise false
	 */
	public abstract boolean openLabDeviceVerification(boolean autoConnect); 

	/**
	 * Transmits sample data to the OpenLab device.
	 *
	 * @param device
	 *            Device to which the samples should get transmitted, most of
	 *            the time this would be the signal generator
	 * @param channelNum
	 *            Channel number to output the signal defined by the provided
	 *            samples
	 * @param waveSamples
	 *            Signal samples to transmit
	 * @return True if transmission was successful, otherwise false
	 */
	public abstract boolean setSamples(DeviceType device, int channelNum, List<Byte> waveSamples);

	/**
	 * Tries to set the provided configuration for the selected in or output.
	 *
	 * @param device
	 *            Target device, see {@link DeviceType}
	 * @param channelNum
	 *            Target channel number
	 * @param ampStage
	 *            Input range to set in V
	 * @param couplingType
	 *            Selected coupling. This parameter can also be used to
	 *            deactivate the selected channel by settings the Coupling to
	 *            OFF.
	 * @return Returns true of the command has been executed successfully on the
	 *         OpenLab device.
	 */
	public abstract boolean setInputConfiguration(DeviceType device, int channelNum, int ampStage, CouplingType couplingType);

	/**
	 * Tries to set the adjusted sample rate
	 *
	 * @param device
	 *            Target device, see {@link DeviceType}
	 * @param sample Rate
	 *            sample rate which needs to be adjusted
	 * @return Returns true of the command has been executed successfully on the
	 *         OpenLab device.
	 */
	public abstract boolean setSampleRate(DeviceType device, int sampleRate);

	/**
	 * Tries to set the provided trigger configuration for the selected input
	 * channel
	 *
	 * @param device
	 *            Device to apply trigger configuration, most likely this will
	 *            be OSCILLOSCOPE.
	 * @param channelNum
	 *            The channel number to which the trigger will be sensitive
	 * @param triggerType
	 *            Type of trigger
	 * @param triggerMode
	 *            Mode of the Trigger (either Auto (0) or Normal (1))
	 * @param triggerLevel
	 *            Trigger level
	 * @param holdOff
	 *            holdoff time after an active trigger event
	 * @return Returns true of the command has been executed successfully on the
	 *         OpenLab device.
	 */
	public abstract boolean setTriggerConfiguration(DeviceType device, int channelNum,TriggerType triggerType,
			TriggerMode triggerMode,int triggerLevel,long holdOff);

	/**
	 * Tries to set the provided trigger configuration for the selected input
	 * channel
	 *
	 * @param device
	 *            Device to apply trigger configuration, most likely this will
	 *            be OSCILLOSCOPE <P>
	 *
	 * @param samplingMode
	 * 			{@link SamplingMode} to bet set <P>
	 * @param samplesAcqRound
	 * 			 Number of samples that shall be taken per signal
	 * 			 acquisition loop <P>
	 * @param acquisitionRound
	 *			 Number of acquisition rounds. In case of realtime
 	 *			 sampling this will be one, if equivalent time sampling
	 *			 is requested this field determines how many sampling
	 *			 loops shall be performed <P>
	 * @return Returns true of the command has been executed successfully on the
	 *         OpenLab device
	 */
	public abstract boolean setSamplingMode(DeviceType device,SamplingMode samplingMode,
			int samplesAcqRound,int acquisitionRound);

}
