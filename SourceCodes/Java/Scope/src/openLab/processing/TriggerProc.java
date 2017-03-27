/**
 * 
 */
package openLab.processing;

import openLab.controller.ImageComboBox;
import openLab.device.DeviceType;
import openLab.device.TriggerMode;
import openLab.device.TriggerType;
import openLab.device.serial.SerialDevice;
import openLab.message.packet.TriggerMSG;
import openLab.resource.OpenLabDevice;

/**
 * @author Markus Lechner
 *
 */
public class TriggerProc {
	public final int TRIGGER_OUT_OF_RANGE = OpenLabDevice.ADC_RESOLUTION_8BIT;

	private int triggerSource = OpenLabDevice.CH1;
	private int ampStageCH1 = OpenLabDevice.DEFAULT_AMP_STAGE;
	private int ampStageCH2 = OpenLabDevice.DEFAULT_AMP_STAGE;	

	private TriggerMSG triggerMSG_CH1 =  new TriggerMSG();
	private TriggerMSG triggerMSG_CH2 =  new TriggerMSG();

	private SerialDevice serialDevice = null;

	/**
	 * @param serialDevice
	 */
	public TriggerProc(SerialDevice serialDevice) {
		this.serialDevice = serialDevice;
	}


	/**
	 * @param triggerMSG
	 */
	public void processTriggerReq(TriggerMSG triggerMSG) {
		boolean isSignalInv;
		int ampStage;
		int triggerLvl;
		int triggerSlope;

		if((triggerMSG != null) && triggerMSG.getTriggerSorce().equals("CH1")) {
			ampStage = ampStageCH1;
			triggerSource = OpenLabDevice.CH1;
			triggerMSG_CH1 = triggerMSG;			
			isSignalInv = triggerMSG.isSignalInverted();					
		} else if((triggerMSG != null) && triggerMSG.getTriggerSorce().equals("CH2")) {
			ampStage = ampStageCH2;
			triggerSource = OpenLabDevice.CH2;
			triggerMSG_CH2 = triggerMSG;			
			isSignalInv = triggerMSG.isSignalInverted();			
		} else if((triggerMSG != null) && triggerMSG.getTriggerSorce().equals("OFF")) {
			ampStage = 0;
			triggerSource = OpenLabDevice.CH1;
			isSignalInv = triggerMSG.isSignalInverted();
		} else {
			return;
		}

		triggerSlope = triggerMSG.getTriggerSlope();		

		if(isSignalInv) {			
			if(triggerSlope == ImageComboBox.IMAGE_RISING_EDGE) {
				triggerSlope = ImageComboBox.IMAGE_FALLING_EDGE;
			} else if(triggerSlope == ImageComboBox.IMAGE_FALLING_EDGE) {
				triggerSlope = ImageComboBox.IMAGE_RISING_EDGE;
			}
		}

		TriggerType triggerType = TriggerType.fromInt(triggerSlope);
		
		if(triggerMSG.getTriggerSorce().equals("OFF")) {
			triggerType = TriggerType.DISABLE;
		}
		
		triggerLvl = calcTriggerValue(ampStage, triggerMSG.getTriggerValue(), isSignalInv, triggerSource);					
		
		transmitTriggerPacket(triggerSource, triggerType,
				triggerMSG.getTriggerMode(), triggerLvl, triggerMSG.getHoldOff());

		return;
	}


	/**
	 * @param channelNr
	 */
	public void updateAmpStage(int channelNr) {
		//If the amp stage to be adjusted is not the same
		//channel as the trigger source, no further steps are required.
		if(channelNr != triggerSource) {
			return;
		}

		if(channelNr == OpenLabDevice.CH1) {
			processTriggerReq(triggerMSG_CH1);
		} else if(channelNr == OpenLabDevice.CH2) {
			processTriggerReq(triggerMSG_CH2);
		} else {
			return;
		}
	}


	/**
	 * @param ampStage
	 * @param triggerValue
	 * @param isSignalInv
	 * @return
	 */
	private int calcTriggerValue(int ampStage, double triggerValue, boolean isSignalInv, int triggerSource) {
		int tempTriggerValue = 0;

		if(Math.abs(triggerValue) > SampleDataProc.ampStageMaxValueLUT(triggerSource, ampStage)){
			return TRIGGER_OUT_OF_RANGE;
		} 

		if(isSignalInv) {
			tempTriggerValue = (int) (((triggerValue / SampleDataProc.ampStageMaxValueLUT(triggerSource, ampStage))
					* (OpenLabDevice.MAX_ADC_NO_CLIPPING - OpenLabDevice.VIRTUAL_ADC_ZERO_POINT))
					+ OpenLabDevice.VIRTUAL_ADC_ZERO_POINT);
		} else {
			tempTriggerValue = (int) (((triggerValue / SampleDataProc.ampStageMaxValueLUT(triggerSource, ampStage))
					* (OpenLabDevice.MIN_ADC_NO_CLIPPING - OpenLabDevice.VIRTUAL_ADC_ZERO_POINT))
					+ OpenLabDevice.VIRTUAL_ADC_ZERO_POINT);				
		}
		return tempTriggerValue;
	}


	/**
	 * @param triggerSrc
	 * @param triggerType
	 * @param triggerMode
	 * @param triggerLvl
	 * @param holdOff
	 * @return
	 */
	private boolean transmitTriggerPacket(int triggerSrc, TriggerType triggerType, TriggerMode triggerMode,
			int triggerLvl, long holdOff) {

		if(serialDevice.setTriggerConfiguration(DeviceType.OSCILLOSCOPE, triggerSrc,
				triggerType, triggerMode, triggerLvl, holdOff))	{

			return true;
		} 

		return false;
	}


	/**
	 * @return the ampStageCH1
	 */
	public int getAmpStageCH1() {
		return ampStageCH1;
	}


	/**
	 * @param ampStageCH1 the ampStageCH1 to set
	 */
	public void setAmpStageCH1(int ampStageCH1) {
		this.ampStageCH1 = ampStageCH1;
	}


	/**
	 * @return the ampStageCH2
	 */
	public int getAmpStageCH2() {
		return ampStageCH2;
	}


	/**
	 * @param ampStageCH2 the ampStageCH2 to set
	 */
	public void setAmpStageCH2(int ampStageCH2) {
		this.ampStageCH2 = ampStageCH2;
	}
}

/* EOF */
