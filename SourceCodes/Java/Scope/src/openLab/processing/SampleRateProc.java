/**
 * 
 */
package openLab.processing;

import openLab.device.DeviceType;
import openLab.device.SamplingMode;
import openLab.device.serial.SerialDevice;
import openLab.message.packet.MessageCode;
import openLab.message.packet.SampleRateMSG;
import openLab.message.packet.UIControlMSG;
import openLab.message.packet.GenericEventMSG.GenericEvent;
import openLab.resource.MSQ_Handler;
import openLab.resource.OpenLabDevice;

/**
 * @author Markus Lechner
 *
 */
public class SampleRateProc {
	private final double DIV_FACTOR = 50.0;

	private SerialDevice serialDevice = null;	
	private GenericEvent genericEvent = GenericEvent.EXECUTION;

	/**
	 * @param serialDevice
	 */
	public SampleRateProc(SerialDevice serialDevice) {
		this.serialDevice = serialDevice;
	}


	/**
	 * @param sampleRateSettings
	 * @return
	 */
	public boolean transmitSampleRate(SampleRateMSG sampleRateSettings) {
		double sampleRate = DIV_FACTOR / sampleRateSettings.getHorizontalScaling();

		OpenLabDevice openLabDevice = OpenLabDevice.getInstance();

		if(sampleRate > openLabDevice.getMaxSampleRateRTS()) {
			if(sampleRate > openLabDevice.getMaxSampleRateETS()) {
				sampleRate = openLabDevice.getMaxSampleRateETS();
			}

			double acqRound = Math.ceil(sampleRate / openLabDevice.getMaxSampleRateRTS());
			int samplesAcqRound = (int)Math.ceil(openLabDevice.getMaxPacketSizeETS() / acqRound);

			if(serialDevice.setSamplingMode(DeviceType.OSCILLOSCOPE, SamplingMode.SEQUENTIAL_ETS,
					samplesAcqRound, (int)acqRound)) {

				sampleRateSettings.setSampleRate(sampleRate);
				sampleRateSettings.setSampleMode(SamplingMode.SEQUENTIAL_ETS);				

				sendUIStatusSampleRate(sampleRate);
			} else {
				return false;
			}
		} else {
			if(!sampleRateSettings.getSampleMode().equals(SamplingMode.REALTIME_SAMPLING)) {
				if(serialDevice.setSamplingMode(DeviceType.OSCILLOSCOPE,SamplingMode.REALTIME_SAMPLING,
						openLabDevice.getMaxPacketSizeRTS(),1)) {

					sampleRateSettings.setSampleMode(SamplingMode.REALTIME_SAMPLING);					
				} else {
					return false;
				}
			}

			if(serialDevice.setSampleRate(DeviceType.OSCILLOSCOPE, (int)sampleRate)) {
				sampleRateSettings.setSampleRate(sampleRate);				

				sendUIStatusSampleRate(sampleRate);
			} else {
				return false;
			}
		}		
		return true;
	}
	
	
	/**
	 * @param sampleRate
	 */
	private void sendUIStatusSampleRate(double sampleRate) {
		if(genericEvent != GenericEvent.RUN_STOP_PROC) {
			UIControlMSG uiControlMSG = new UIControlMSG();
			MSQ_Handler msq_Handler = MSQ_Handler.getInstance();
			
			uiControlMSG.setMsgCode(MessageCode.UIControl);
			uiControlMSG.setStatus(formatSampleRate(sampleRate) );
			
			msq_Handler.getMsqProcessReq().add(uiControlMSG);
		}
		return;
	}
	
	
	/**
	 * @param sampleRate
	 * @return
	 */
	private String formatSampleRate(double sampleRate) {
		if(sampleRate < 1.0e3) {
			return ("Sample Rate: " + (String.valueOf(sampleRate)) + " Sa/s");
		} else if((sampleRate >= 1.0e3) && (sampleRate < 1.0e6)) {
			return ("Sample Rate: " + (String.valueOf(sampleRate/1.0e3)) + " kSa/s");
		} else if(sampleRate >= 1.0e6) {
			return ("Sample Rate: " + (String.valueOf(sampleRate/1.0e6)) + " MSa/s");
		}		
		return null;
	}


	/**
	 * @param genericEvent the genericEvent to set
	 */
	public void setGenericEvent(GenericEvent genericEvent) {
		this.genericEvent = genericEvent;
	}

}


/* EOF */