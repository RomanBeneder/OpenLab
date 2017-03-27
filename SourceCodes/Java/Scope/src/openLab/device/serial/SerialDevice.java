package openLab.device.serial;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import dialog.StandardDialog;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import openLab.device.CommandCode;
import openLab.device.CouplingType;
import openLab.device.DeviceEventListener;
import openLab.device.DeviceType;
import openLab.device.SamplingMode;
import openLab.device.TriggerMode;
import openLab.device.TriggerType;
import openLab.device.packets.ExtendedSampleData;
import openLab.device.packets.GenericPacket;
import openLab.device.packets.GetHardwareVersionPacket;
import openLab.device.packets.GetSoftwareVersionPacket;
import openLab.device.packets.HardwareVersion;
import openLab.device.packets.PacketFactory;
import openLab.device.packets.SampleDataPacket;
import openLab.device.packets.SetChannelInput;
import openLab.device.packets.SetSamplingMode;
import openLab.device.packets.SetTimeBasePacket;
import openLab.device.packets.SetTriggerSettings;
import openLab.device.packets.SoftwareVersion;
import openLab.message.packet.DeviceMSG;
import openLab.message.packet.MessageCode;
import openLab.message.packet.UIControlMSG;
import openLab.message.packet.UIControlMSG.Components;
import openLab.messageX.SerialInterfaceMSG;
import openLab.resource.MSQ_Handler;
import openLab.resource.OpenLabDevice;
import purejavacomm.CommPortIdentifier;
import purejavacomm.NoSuchPortException;
import purejavacomm.PortInUseException;
import purejavacomm.SerialPort;
import purejavacomm.UnsupportedCommOperationException;

/**
 * Serial device class that will handle all the communication related tasks to
 * and from the OpenLab devices if they are connected via a serial interface.
 *
 * @author Harald Schloffer, Markus Lechner
 *
 */
public class SerialDevice extends openLab.device.Device implements Runnable{
	//Error Codes
	public final int SERIAL_PORT_TRANSMISION_SUCCESSFUL = 1;
	public final int ERR_SERIAL_PORT_COULD_NOT_ACCESSED = 100;
	public final int ERR_SERIAL_PORT_RETRANSMISSION_FAILED = 101;
	public final int ERR_SERIAL_PORT_WRONG_COMMAND_RECEIVED = 102;

	//HW & SW Version
	private String hwVersion = null;
	private String swVersion = null;

	//Serial Port Objects	
	private final String TTYS_PORT = "ttyS";
	private final int SERIAL_TIME_OUT = 200;
	private final int PORT_OPEN_TIMEOUT = 1000;
	public final int DEFAULT_BAUDRATE = 1200000;
	private final int MAX_RETRANSMIT_PROCESS = 8;
	private boolean isConnected = false;

	private int portListSize = -1;
	private SerialPort activePort = null;
	private CommPortIdentifier portId = null;
	private Enumeration<CommPortIdentifier> portIdentifiers = null;
	private ArrayList<String> tempPortList = new ArrayList<String>(10);	

	String selectedPort = null;
	String previousPort = null;	

	private Thread serialPortIdenThread = null;
	private Thread backgroundReceiverThread = null;
	private BackgroundReceiver backgroundReceiver = null;

	private PrintStream txBuffer = null;
	private BufferedInputStream rxBuffer = null;
	
	private SetChannelInput setChannelInputPacketCH1 = null;
	private SetChannelInput setChannelInputPacketCH2 = null;

	public enum SelectedPortAccess {
		SET_SELECTED_PORT,
		GET_SELECTED_PORT
	}

	/**
	 * Default constructor
	 */
	public SerialDevice() {		
		backgroundReceiver = new BackgroundReceiver();
		backgroundReceiverThread = new Thread(backgroundReceiver);
		backgroundReceiverThread.setName("SerialDevice.BackgroundReceiver");
		backgroundReceiverThread.setDaemon(true);
		backgroundReceiverThread.start();

		serialPortIdenThread = new Thread(this);
		serialPortIdenThread.setName("SerialDevice.InterfaceVeryfication");
		serialPortIdenThread.setDaemon(true);
		serialPortIdenThread.start();
	}


	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		
		sendIntSerialDeviceState("Scanning Serial Ports...", Components.DISABLE, false);		
		while(true) {
			tempPortList.clear();
			//Get an enumeration of all ports known to JavaComm
			portIdentifiers = CommPortIdentifier.getPortIdentifiers();

			if(portIdentifiers != null) {
				while (portIdentifiers.hasMoreElements()) {
					portId = portIdentifiers.nextElement();

					if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)	{
						if(!portId.getName().substring(0, 4).equals(TTYS_PORT))	{
							tempPortList.add(portId.getName());
						}
					}
				}
			}			

			//Send the portList to the RequestProcess,
			if(portListSize != tempPortList.size()) {
				sendPortList(tempPortList);
			}			

			//Verify if the detected device is an OpenLab device
			serialPortVerification();

			if((!isConnected) && (portListSize != tempPortList.size())) {				
				if(!startAutoConnect(tempPortList)) {
					activePort = null;
				}
			}

			portListSize = tempPortList.size();

			try	{
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * @param portList
	 */
	private void sendPortList(ArrayList<String> portList) {
		DeviceMSG deviceMSG = new DeviceMSG();
		MSQ_Handler msq_Handler = MSQ_Handler.getInstance();

		deviceMSG.setMsgCode(MessageCode.DEVICE_INTER_UPD);
		deviceMSG.setPortList(portList);

		msq_Handler.getMsqProcessReq().add(deviceMSG);
		return;
	}


	/**
	 * 
	 */
	private void serialPortVerification() {
		String tempSelectedPort = accessSelectedPort(SelectedPortAccess.GET_SELECTED_PORT, null);

		if(activePort == null) {
			return;
		}

		if(activePort.getName() != null) {
			if((tempSelectedPort != null) && (!tempPortList.contains(activePort.getName()))) {
				disconnect(false);

				if(activePort != null) {
					StandardDialog standardDialog = new StandardDialog(null);
					Platform.runLater(() -> {
						standardDialog.setTitle(SerialInterfaceMSG.dialogTitleSerialError);
						standardDialog.setContentText(SerialInterfaceMSG.dialogTextSerialError);
						standardDialog.createDialog(AlertType.ERROR);
					});
				}
				accessSelectedPort(SelectedPortAccess.SET_SELECTED_PORT, null);
				//TODO inform user
				System.out.println("Disconnected");
			}
		}
		return;
	}


	/**
	 * @param selectedPortAccess
	 * @param selectedPort
	 * @return
	 */
	public synchronized String accessSelectedPort(SelectedPortAccess selectedPortAccess, String selectedPort) {
		if(selectedPortAccess.equals(SelectedPortAccess.SET_SELECTED_PORT)) {
			this.selectedPort = selectedPort;
		} else if(selectedPortAccess.equals(SelectedPortAccess.GET_SELECTED_PORT)) {
			return this.selectedPort;
		}
		return null;
	}


	/**
	 * @param tempPortList
	 * @return
	 */
	private boolean startAutoConnect(ArrayList<String> tempPortList) {
		for(int i=0; i<tempPortList.size(); i++) {
			accessSelectedPort(SelectedPortAccess.SET_SELECTED_PORT, tempPortList.get(i));
			if(connect(DEFAULT_BAUDRATE, true)) {
				return true;
			}
		}
		return false;
	}



	@Override
	public boolean getHardwareVersionString() {
		GetHardwareVersionPacket packet = new GetHardwareVersionPacket();
		packet.setDeviceType(DeviceType.DEVICE);
		packet.setChannelNum(0);

		if(transmitPacket(packet) == SERIAL_PORT_TRANSMISION_SUCCESSFUL) {
			return true;
		}
		return false;
	}

	@Override
	public boolean getSoftwareVersionString() {
		GetSoftwareVersionPacket packet = new GetSoftwareVersionPacket();
		packet.setDeviceType(DeviceType.DEVICE);
		packet.setChannelNum(0);

		if(transmitPacket(packet) == SERIAL_PORT_TRANSMISION_SUCCESSFUL) {
			return true;
		} 
		return false;
	}

	@Override
	public boolean setSamples(DeviceType device, int channelNum, List<Byte> waveSamples) {
		SampleDataPacket packet = new SampleDataPacket();
		packet.setChannelNum(channelNum);
		packet.setDeviceType(device);
		packet.addSampleData(waveSamples);

		return startTransmissionProgress(packet);
	}

	@Override
	public boolean setSampleRate(DeviceType device, int sampleRate) {
		OpenLabDevice openLabDevice = OpenLabDevice.getInstance();

		if(openLabDevice.getDevice().equals(OpenLabDevice.DEVICE_FPGA_DE0)) {
			sampleRate = (int)(openLabDevice.getSystemClock() / sampleRate);
		}

		SetTimeBasePacket packet = new SetTimeBasePacket();
		packet.setChannelNum(0);
		packet.setDeviceType(device);
		packet.setSamplesPerSecond(sampleRate);

		return startTransmissionProgress(packet);
	}

	@Override
	public synchronized boolean setInputConfiguration(DeviceType device, int channelNum, int inputRange, CouplingType coupling) {
		SetChannelInput packet = new SetChannelInput();
		packet.setChannelNum(channelNum);
		packet.setDeviceType(device);
		packet.setCoupling(coupling);
		packet.setInputGain(inputRange);

		saveInputConfigPacket(packet);

		return startTransmissionProgress(packet);
	}

	@Override
	public boolean setTriggerConfiguration(DeviceType device, int channelNum,TriggerType triggerType,
			TriggerMode triggerMode,int triggerLevel,long holdOff)
	{
		SetTriggerSettings packet = new SetTriggerSettings(triggerType, triggerMode, triggerLevel, holdOff);
		packet.setDeviceType(device);
		packet.setChannelNum(channelNum);

		return startTransmissionProgress(packet);
	}

	@Override
	public boolean setSamplingMode(DeviceType device,SamplingMode samplingMode,int samplesAcqRound,int acquisitionRound) {
		SetSamplingMode packet = new SetSamplingMode(samplingMode,samplesAcqRound,acquisitionRound);
		packet.setDeviceType(device);
		packet.setChannelNum(0);

		return startTransmissionProgress(packet);
	}

	@Override
	public boolean sendPacket(GenericPacket packet) {
		if (activePort != null) {
			List<Byte> packetData = packet.getRawPacketData();
			if ((txBuffer != null) && (packetData.size() > 0)) {
				for (Iterator<Byte> it = packetData.iterator(); it.hasNext();) {
					txBuffer.write(it.next().byteValue());
				}
				return true; // All data were sent
			}
		}
		return false; // Data transmission failed
	}

	@Override
	public GenericPacket receivePacket(int timeOutMs) {
		GenericPacket packet = null;

		long startTime = System.nanoTime();
		while ((System.nanoTime() - startTime) < (timeOutMs * 1e6))	{
			packet = backgroundReceiver.getNextPacket();
			if (packet != null) {
				break;
			}
		}
		return packet;
	}


	@Override
	public boolean openLabDeviceVerification(boolean autoConnect) {
		if(getHardwareVersionString() && getSoftwareVersionString()) {
			return true;
		} 

		disconnect(false);

		if(!autoConnect) {
			StandardDialog standardDialog = new StandardDialog(null);
			Platform.runLater(() -> {
				standardDialog.setTitle(SerialInterfaceMSG.dialogTitleNoInvalidDevice);
				standardDialog.setContentText(SerialInterfaceMSG.dialogTextInvalidDevice);
				standardDialog.createDialog(AlertType.ERROR);
			});
		}		
		return false;
	}


	/**
	 * @param packet
	 */
	private void saveInputConfigPacket(SetChannelInput packet) {		
		if(packet.getChannelNum() == OpenLabDevice.CH1) {
			setChannelInputPacketCH1 = new SetChannelInput();
			setChannelInputPacketCH1 = packet;
		} else if(packet.getChannelNum() == OpenLabDevice.CH2) {
			setChannelInputPacketCH2 = new SetChannelInput();
			setChannelInputPacketCH2 = packet;
		}
		return;
	}


	/**
	 * @param packet
	 */
	private void restoreInputConfig(SetChannelInput packet) {
		if(setInputConfiguration(packet.getDeviceType(), packet.getChannelNum(),
				packet.getInputGain(), packet.getCoupling()))
		{
			System.out.println("Serial Device: Restored Channel-Input Settings for CH" + packet.getChannelNum());
		} else {
			System.err.println("Serial Device: Failed to restore Channel-Input Settings for CH" + packet.getChannelNum());
		} 
		return;
	}
	

	private boolean startTransmissionProgress(GenericPacket packet) {
		int transmissionState = 0;

		if(packet == null) {
			return false;
		}

		transmissionState = transmitPacket(packet);
		if(transmissionState == ERR_SERIAL_PORT_RETRANSMISSION_FAILED) {
			if(openLabDeviceVerification(false)) {
				restoreInputConfig(setChannelInputPacketCH1);
				restoreInputConfig(setChannelInputPacketCH2);
				
				System.out.println("Serial Device: Synchronization was successfull");
				return true;
			} 
			System.err.println("Serial Device: Synchronization was not successfull - Disconecting...");
			return false;
		} else if(transmissionState == ERR_SERIAL_PORT_COULD_NOT_ACCESSED) {
			System.err.println("Serial Device: Error: Serial device is unreachable!");
			return false;
		}
		return true;
	}


	private synchronized int transmitPacket(GenericPacket packet) {
		int transmitProc = 0;

		while((packet != null) && (transmitProc < MAX_RETRANSMIT_PROCESS)) {
			if (sendPacket(packet)) {
				GenericPacket rxPacket = receivePacket(SERIAL_TIME_OUT);
				if (rxPacket != null) {
					if (rxPacket.getCmdCode().equals(CommandCode.NOT_ACKNOWLEDGED)) {
						transmitProc++;
						System.err.println("Serial Device: NOT ACKNOWLEDGED" + rxPacket.getTimeStamp());
						continue;//NACK was received - Retransmit is necessary
					} else if (rxPacket.getCmdCode().equals(CommandCode.ACKNOWLEDGE)){
						packet = null;
						return SERIAL_PORT_TRANSMISION_SUCCESSFUL; //ACK was received
					} else if(rxPacket.getCmdCode().equals(CommandCode.HARDWARE_VERSION)) {
						HardwareVersion hwPacket = (HardwareVersion) rxPacket;
						hwVersion = new String(Integer.toString(hwPacket.getMajorVersion()) + "."
								+ Integer.toString(hwPacket.getMinorVersion()) + "."
								+ Integer.toString(hwPacket.getSubVersion()));
						return SERIAL_PORT_TRANSMISION_SUCCESSFUL; //Hardware Version was received
					} else if(rxPacket.getCmdCode().equals(CommandCode.SOFTWARE_VERSION)) {
						SoftwareVersion swPacket = (SoftwareVersion) rxPacket;
						swVersion = new String(Integer.toString(swPacket.getMajorVersion()) + "."
								+ Integer.toString(swPacket.getMinorVersion()) + "."
								+ Integer.toString(swPacket.getSubVersion()));
						return SERIAL_PORT_TRANSMISION_SUCCESSFUL; //Software Version was received
					} else {
						return ERR_SERIAL_PORT_WRONG_COMMAND_RECEIVED; //Any command was received
					}
				} 
				//Received packet contained faulty CRC - Retransmit is necessary
				transmitProc++;
				System.err.println("Serial Device: No Packet received - Timed out - Retransmit Count: " + transmitProc);
				continue;
			}
			return ERR_SERIAL_PORT_COULD_NOT_ACCESSED; //Serial port could not be accessed
		}
		return ERR_SERIAL_PORT_RETRANSMISSION_FAILED; //Retransmission failed - device could not be accessed
	}


	public synchronized boolean connect(int baudRate, boolean autoConnect) {
		if((previousPort != null)) {
			previousPort = accessSelectedPort(SelectedPortAccess.GET_SELECTED_PORT, null);
		}

		//handle disconnect with previous port...
		disconnect(true);

		//handle connect to port
		try {
			if(accessSelectedPort(SelectedPortAccess.GET_SELECTED_PORT, null) == null) {				
				StandardDialog standardDialog = new StandardDialog(null);
				Platform.runLater(() -> {
					standardDialog.setTitle(SerialInterfaceMSG.dialogTitleNoSerialPortSelected);
					standardDialog.setContentText(SerialInterfaceMSG.dialogTextNoSerialPortSelected);
					standardDialog.createDialog(AlertType.ERROR);
				});
				return false;
			}

			CommPortIdentifier portID = CommPortIdentifier.
					getPortIdentifier(accessSelectedPort(SelectedPortAccess.GET_SELECTED_PORT, null));

			if(portID.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				activePort = (SerialPort) portID.open(this.toString(), PORT_OPEN_TIMEOUT);

				//Set serial port parameters according to the serial device
				activePort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

				rxBuffer = new BufferedInputStream(activePort.getInputStream());
				txBuffer = new PrintStream(activePort.getOutputStream());

				/*
				 * FIXME: Send some zeroes to reset the decoding state machine.
				 * Currently a work around because sometimes the decoder seems
				 * to receive some bytes and gets confused.
				 */
				byte[] dummy = new byte[10];
				activePort.getOutputStream().write(dummy);

				//Demand Hardware and Software version
				if(!openLabDeviceVerification(autoConnect)) {
					return false;
				}

				System.out.println("Serial Device: Connected");

				initOpenLabDevice();

				setConnected(true);

				OpenLabDevice openLabDevice = OpenLabDevice.getInstance();
				sendIntSerialDeviceState("Connected to " + openLabDevice.getDevice() + " Device", Components.ENABLE, true);			
			}

		} catch (NoSuchPortException | IOException e) {
			e.printStackTrace();
		} catch (PortInUseException e) {
			e.printStackTrace();
			StandardDialog standardDialog = new StandardDialog(null);
			Platform.runLater(() -> {
				standardDialog.setTitle(SerialInterfaceMSG.dialogTitlePortInUse);
				standardDialog.setContentText(SerialInterfaceMSG.dialogTextPortInUse);
				standardDialog.createDialog(AlertType.ERROR);
			});
		} catch (UnsupportedCommOperationException  e) {
			e.printStackTrace();
		} catch (purejavacomm.PureJavaIllegalStateException e) {
			e.printStackTrace();
			connect(9600, true);
			System.out.println("Serial Device: Processed PureJavaIllegalState Exception");
		}
		return true;
	}


	private boolean initOpenLabDevice() {
		//Set Hardware and Software Version
		OpenLabDevice openLabDevice = OpenLabDevice.getInstance();
		openLabDevice.setHardwareSoftwareVersion(this.hwVersion, this.swVersion);

		//Switch off both channels
		setInputConfiguration(DeviceType.OSCILLOSCOPE,OpenLabDevice.CH1,OpenLabDevice.DEFAULT_AMP_STAGE,CouplingType.COUPLING_TYPE_OFF);
		setInputConfiguration(DeviceType.OSCILLOSCOPE,OpenLabDevice.CH2,OpenLabDevice.DEFAULT_AMP_STAGE,CouplingType.COUPLING_TYPE_OFF);

		//Forcing Real-Time-Sampling Mode
		setSamplingMode(DeviceType.OSCILLOSCOPE,SamplingMode.REALTIME_SAMPLING,openLabDevice.getMaxPacketSizeRTS(),1);

		//Set default sample rate
		setSampleRate(DeviceType.OSCILLOSCOPE, (int)OpenLabDevice.DEFAULT_SAMPLE_RATE);		

		return true;
	}


	@Override
	public boolean disconnect(boolean switchOffChannels) {
		if(activePort != null) {
			OpenLabDevice openLabDevice = OpenLabDevice.getInstance();

			if((!openLabDevice.getDevice().equals(OpenLabDevice.DEVICE_INVALID)) && switchOffChannels){
				//Switch off both channels
				setInputConfiguration(DeviceType.OSCILLOSCOPE, OpenLabDevice.CH1,
						OpenLabDevice.DEFAULT_AMP_STAGE, CouplingType.COUPLING_TYPE_OFF);

				setInputConfiguration(DeviceType.OSCILLOSCOPE, OpenLabDevice.CH2,
						OpenLabDevice.DEFAULT_AMP_STAGE, CouplingType.COUPLING_TYPE_OFF);
			}

			setConnected(false);

			try {
				if(rxBuffer != null) {
					rxBuffer.close();
					rxBuffer = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if(txBuffer != null) {
				txBuffer.close();
				txBuffer = null;
			}

			activePort.close();

			sendIntSerialDeviceState("Disconnected " +  openLabDevice.getDevice() + " Device", Components.DISABLE, false);

			//Restore OpenLab device settings
			openLabDevice.resetDeviceProp();			

			return true;
		}
		return false;
	}


	private void sendIntSerialDeviceState(String status, Components components, boolean switchCH1on) {
		UIControlMSG uiControlMSG = new UIControlMSG();
		MSQ_Handler msq_Handler = MSQ_Handler.getInstance();

		uiControlMSG.setMsgCode(MessageCode.UIControl);
		uiControlMSG.setComponents(components);
		uiControlMSG.setStatus(status);		

		if(switchCH1on) {
			uiControlMSG.setActivityCH1(Components.ENABLE);
		} else {
			uiControlMSG.setActivityCH1(Components.DISABLE);
			uiControlMSG.setActivityCH2(Components.DISABLE);
		}

		msq_Handler.getMsqProcessReq().add(uiControlMSG);
		return;
	}


	@Override
	public synchronized boolean isConnected() {
		return isConnected;
	}

	/**
	 * @param isConnected the isConnected to set
	 */
	public /*synchronized*/ void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}


	/**
	 * @param eventListener
	 *            the eventListener to set
	 */
	@Override
	public void setEventListener(DeviceEventListener eventListener) {
		this.backgroundReceiver.setEventListener(eventListener);
	}


	/**
	 * @author 
	 *
	 */
	protected class BackgroundReceiver implements Runnable {
		private GenericPacket rxPacket = null;
		protected volatile DeviceEventListener eventListener = null;		
		protected volatile ConcurrentLinkedQueue<GenericPacket> receivedPackets = new ConcurrentLinkedQueue<GenericPacket>();


		/**
		 * This is the actual thread function that will be executed until this
		 * task has either finished or got terminated.
		 */
		@Override
		public void run() {
			while(true) {
				try	{
					if (rxBuffer != null) {
						rxPacket = PacketFactory.fromStream(rxBuffer);

						if (rxPacket != null) {
							if (rxPacket.getCmdCode().equals(CommandCode.SAMPLE_DATA)) {
								if (eventListener != null) {
									eventListener.handleSampleDataReceived((SampleDataPacket) rxPacket);
								}
							} else if(rxPacket.getCmdCode().equals(CommandCode.EXTENDED_SAMPLE_DATA)) {
								if (eventListener != null) {
									eventListener.handleExtendedSampleDataReceived((ExtendedSampleData) rxPacket);
								}
							} else {
								receivedPackets.add(rxPacket);
								if (eventListener != null) {
									eventListener.handlePacketReceived(rxPacket);
								}
							}
						}
					} else {
						Thread.sleep(25);
					}
				}catch (Exception e) {
					/*
					 * Connection lost or some error occurred. Don't mind too
					 * much, just inform someone
					 */
					if (eventListener != null) {
						eventListener.handleDisconnect();
					}
					e.printStackTrace();
				}
			}
		}


		/**
		 * @param eventListener
		 *            the eventListener to set
		 */
		public void setEventListener(DeviceEventListener eventListener) {
			this.eventListener = eventListener;
		}


		/**
		 * Returns the next packet in the receive queue. May return null if no
		 * packet is available yet.
		 *
		 * @return Returns next received packet or null
		 */
		public GenericPacket getNextPacket() {
			return receivedPackets.poll();
		}
	}
}

/* EOF */
