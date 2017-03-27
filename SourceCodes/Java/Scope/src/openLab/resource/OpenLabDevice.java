/**
 * 
 */
package openLab.resource;

import dialog.StandardDialog;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import openLab.messageX.SerialInterfaceMSG;

/**
 * @author Markus Lechner
 *
 */
public class OpenLabDevice {
	private static OpenLabDevice deviceType = null;

	//General Valid finals
	public final static int CH1 = 0;
	public final static int CH2 = 1;
	public final static int BOTH_CHANNELS = 2;
	public final static int INVALID_CH = -1;
	public final static int DEFAULT_AMP_STAGE = 5;
	public final static String REQUIRED_HW_VERSION = "2";
	public final static String DEVICE_INVALID = "Invalid";
	public final static double DEFAULT_SAMPLE_RATE = 50e3; //Default 50kSA/s
	
	//Finals according to the 8 Bit ADC
	public final static int ADC_RESOLUTION_8BIT = 255;
	public static final double VIRTUAL_ADC_ZERO_POINT = 129.00;
	public static final double MAX_ADC_NO_CLIPPING = 193.80;
	public static final double MIN_ADC_NO_CLIPPING = 64.00;	

	//Final values for the FPGA DE0
	public final static String DEVICE_FPGA_DE0 = "FPGA-DE0";
	private final int MAX_PACKET_SIZE_RTS_FPGA = 500;
	private final int MAX_PACKET_SIZE_ETS_FPGA = 600;
	private final double SYSTEM_CLOCK_FPGA = 120.0e6;
	private final double MAX_SAMPLE_RATE_RTS_FPGA = 2.5e6;
	private final double MAX_SAMPLE_RATE_ETS_FPGA = 50.0e6;

	//Final values for the Microcontroller XMC4500 & TIVA C
	public final static String DEVICE_uC_XMC4500 = "µC-XMC4500";
	public final static String DEVICE_uC_TIVAC = "µC-TIVAC";
	private final int MAX_PACKET_SIZE_RTS_uC = 500;
	private final int MAX_PACKET_SIZE_ETS_uC = 600;
	private final double SYSTEM_CLOCK_uC = 120.0e6;
	private final double MAX_SAMPLE_RATE_RTS_uC = 250.0e3;
	private final double MAX_SAMPLE_RATE_ETS_uC = 0.0;

	private String hwVersion = "n/a";
	private String swVersion = "n/a";
	private String device = DEVICE_INVALID;

	private boolean perfSWCalc = false;
	private boolean isHWFreqSupported = false;	

	private int maxPacketSizeRTS = 500;	
	private int maxPacketSizeETS = 600;	
	private double systemClock = 0.0;
	private double maxSampleRateRTS = 0.0;
	private double maxSampleRateETS = 0.0;	

	private volatile boolean isInitialized = false;

	/**
	 * @return
	 */
	public static OpenLabDevice getInstance() {
		if(deviceType == null) {
			synchronized(OpenLabDevice.class)	{
				deviceType = new OpenLabDevice();
			}
		}
		return deviceType;
	}


	public void setHardwareSoftwareVersion(String hwVersion, String swVersion){
		if((hwVersion != null) && (swVersion != null)) {
			if((hwVersion.isEmpty() || hwVersion.equals("n/a")) || swVersion.isEmpty()) {
				this.device = DEVICE_INVALID;
				this.hwVersion = "n/a";
				this.swVersion = "n/a";
			} else {
				String tempMajorNr = hwVersion.substring(0, 1);

				if(tempMajorNr.equals("1"))	{
					initFPGAProp(DEVICE_FPGA_DE0, hwVersion, swVersion);
				} else if(tempMajorNr.equals("2")) {
					initMicrocontrollerProp(DEVICE_uC_XMC4500, hwVersion, swVersion);
				} else if(tempMajorNr.equals("3")) {
					initMicrocontrollerProp(DEVICE_uC_TIVAC, hwVersion, swVersion);
				} else {
					this.device = DEVICE_INVALID;
					this.hwVersion = DEVICE_INVALID;
					this.swVersion = DEVICE_INVALID;
				}
			}
			performHardwareVerification(this.hwVersion);
		}		
		return;
	}


	private boolean performHardwareVerification(String hwVersion) {
		StringBuilder subHWVersion = new StringBuilder(hwVersion);
		subHWVersion.delete(0, hwVersion.indexOf(".")+1);
		subHWVersion.delete(0, hwVersion.indexOf(".")+1);

		if(REQUIRED_HW_VERSION.equals(subHWVersion.toString())){
			return true;
		} 

		String dialogText = new String(SerialInterfaceMSG.dialogTextInvHW1)
				.concat(SerialInterfaceMSG.dialogTextInvHW2)
				.concat(REQUIRED_HW_VERSION)
				.concat(SerialInterfaceMSG.dialogTextInvHW3)
				.concat(subHWVersion.toString());

		StandardDialog standardDialog  = new StandardDialog(null);
		
		Platform.runLater(() -> {
			standardDialog.setTitle(SerialInterfaceMSG.dialogTitleInvHW);
			standardDialog.setContentText(dialogText);
			standardDialog.createDialog(AlertType.WARNING);
		});		
		return false;
	}


	/**
	 * @param hwVersion
	 * @param swVersion
	 */
	private void initFPGAProp(String device, String hwVersion, String swVersion) {
		this.device = device;	
		this.hwVersion = hwVersion;
		this.swVersion = swVersion;
		perfSWCalc = true;
		isHWFreqSupported = true;
		systemClock = SYSTEM_CLOCK_FPGA;
		maxPacketSizeRTS = MAX_PACKET_SIZE_RTS_FPGA;
		maxPacketSizeETS = MAX_PACKET_SIZE_ETS_FPGA;
		maxSampleRateRTS = MAX_SAMPLE_RATE_RTS_FPGA;
		maxSampleRateETS = MAX_SAMPLE_RATE_ETS_FPGA;	
		isInitialized = true;

		return;
	}


	/**
	 * @param hwVersion
	 * @param swVersion
	 */
	private void initMicrocontrollerProp(String device, String hwVersion, String swVersion) {		
		this.device = device;	
		this.hwVersion = hwVersion;
		this.swVersion = swVersion;
		perfSWCalc = false;
		isHWFreqSupported = false;
		systemClock = SYSTEM_CLOCK_uC;
		maxPacketSizeRTS = MAX_PACKET_SIZE_RTS_uC;
		maxPacketSizeETS = MAX_PACKET_SIZE_ETS_uC;
		maxSampleRateRTS = MAX_SAMPLE_RATE_RTS_uC;
		maxSampleRateETS = MAX_SAMPLE_RATE_ETS_uC;		
		isInitialized = true;

		return;
	}

	
	/**
	 * 
	 */
	public void resetDeviceProp() {
		this.device = DEVICE_INVALID;	
		this.hwVersion = "n/a";
		this.swVersion = "n/a";
		perfSWCalc = false;
		isHWFreqSupported = false;
		systemClock = 0.0;
		maxPacketSizeRTS = 500;
		maxPacketSizeETS = 600;
		maxSampleRateRTS = 0.0;
		maxSampleRateETS = 0.0;	
		
		return;
	}

	/**
	 * @return the isInitialized
	 */
	public boolean isInitialized() {
		return isInitialized;
	}


	/**
	 * @param isInitialized the isInitialized to set
	 */
	public void setInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}


	/**
	 * @return the hwVersion
	 */
	public synchronized String getHwVersion() {
		return hwVersion;
	}


	/**
	 * @return the swVersion
	 */
	public String getSwVersion() {
		return swVersion;
	}


	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}


	/**
	 * @return the perfSWCalc
	 */
	public boolean isPerfSWCalc() {
		return perfSWCalc;
	}


	/**
	 * @return the isHWFreqSupported
	 */
	public boolean isHWFreqSupported() {
		return isHWFreqSupported;
	}


	/**
	 * @return the maxPacketSizeRTS
	 */
	public int getMaxPacketSizeRTS() {
		return maxPacketSizeRTS;
	}


	/**
	 * @return the systemClock
	 */
	public double getSystemClock() {
		return systemClock;
	}


	/**
	 * @return the maxSampleRateRTS
	 */
	public double getMaxSampleRateRTS() {
		return maxSampleRateRTS;
	}


	/**
	 * @return the maxSampleRateETS
	 */
	public double getMaxSampleRateETS() {
		return maxSampleRateETS;
	}


	/**
	 * @return the maxPacketSizeETS
	 */
	public int getMaxPacketSizeETS() {
		return maxPacketSizeETS;
	}	
}

/* EOF */
