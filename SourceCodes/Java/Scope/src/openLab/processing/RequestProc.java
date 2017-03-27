package openLab.processing;

import java.util.ArrayList;
import java.util.Iterator;

import openLab.device.serial.HandleSampleData;
import openLab.device.serial.SerialDevice;
import openLab.device.serial.SerialDevice.SelectedPortAccess;
import openLab.message.packet.ChannelControlMSG;
import openLab.message.packet.DeviceMSG;
import openLab.message.packet.ErrorCode;
import openLab.message.packet.ErrorMSG;
import openLab.message.packet.ErrorType.Severity;
import openLab.message.packet.ExportCSVMSG;
import openLab.message.packet.GenericEventMSG;
import openLab.message.packet.GenericEventMSG.GenericEvent;
import openLab.message.packet.GenericMessage;
import openLab.message.packet.MeasurementMSG;
import openLab.message.packet.MessageCode;
import openLab.message.packet.SampleRateMSG;
import openLab.message.packet.SnapshotMSG;
import openLab.message.packet.TriggerMSG;
import openLab.message.packet.UIControlMSG;
import openLab.resource.MSQ_Handler;
import openLab.resource.OpenLabDevice;

/**
 * @author Markus Lechner
 *
 */
public class RequestProc implements Runnable{
	private Thread requestProcThread = null;

	private SerialDevice serialDevice = null;
	private HandleSampleData handleSampleData = null;
	private MeasurementProc measurementProc = null;
	private SampleDataProc sampleDataProc = null;
	private SampleRateProc sampleRateProc = null;
	private TriggerProc triggerProc = null;

	private ChannelControlMSG channelControlCH1 = new ChannelControlMSG();
	private ChannelControlMSG channelControlCH2 = new ChannelControlMSG();
	private SampleRateMSG sampleRateMSG = new SampleRateMSG();


	MSQ_Handler msq_Handler = null;
	/**
	 * 
	 */
	public RequestProc() {
		initSerialInterface();
	}	


	private void initSerialInterface() {
		serialDevice = new SerialDevice();
		handleSampleData = new HandleSampleData();
		handleSampleData.initSamplePacketManagment(serialDevice);		
		return;
	}	


	public void initRequestProcessing() {
		requestProcThread = new Thread(this);
		requestProcThread.setName("Request.Processing");
		requestProcThread.setDaemon(true);
		requestProcThread.start();
		return;
	}	


	@Override
	public void run() {
		GenericMessage genericMessage;
		msq_Handler = MSQ_Handler.getInstance();

		measurementProc = new  MeasurementProc();
		sampleDataProc = new SampleDataProc(serialDevice, measurementProc);		
		triggerProc = new TriggerProc(serialDevice);
		sampleRateProc = new SampleRateProc(serialDevice);		
		
		while(true) {
			if(!serialDevice.isConnected()) {
				if(!msq_Handler.getMsqProcessReq().isEmpty()) {					
					Iterator<GenericMessage> iterProcReq = msq_Handler.getMsqProcessReq().iterator();
					
					while(iterProcReq.hasNext()) {
						genericMessage = iterProcReq.next();
						
						if(genericMessage.getMsgCode().equals(MessageCode.UIControl)) {					
							processUIControlRedir(genericMessage);	
							msq_Handler.getMsqProcessReq().remove(genericMessage);
						} else if(genericMessage.getMsgCode().equals(MessageCode.DEVICE_INTER_UPD)) {
							processDeviceInterfaceRedir(genericMessage);
							msq_Handler.getMsqProcessReq().remove(genericMessage);
						} else if(genericMessage.getMsgCode().equals(MessageCode.DEVICE_INTER_REQ)) {
							processDeviceInterfaceReq(genericMessage);
							msq_Handler.getMsqProcessReq().remove(genericMessage);
						}
					}	
				}				
				setThreadInSleepMode(1000);
				continue;
			}

			if(!msq_Handler.getMsqProcessReq().isEmpty()) {
				genericMessage = msq_Handler.getMsqProcessReq().remove();
				processRequest(genericMessage);				
			}

			checkRawSampleDataQueues();

			setThreadInSleepMode(10);
		}		
	}


	/**
	 * @param genericMessage
	 */
	private void processRequest(GenericMessage genericMessage) {
		switch (genericMessage.getMsgCode()) {

		case TRIGGER:
			TriggerMSG triggerMSG = (TriggerMSG)genericMessage;

			triggerProc.setAmpStageCH1(sampleDataProc.getAmpStageCH1());
			triggerProc.setAmpStageCH2(sampleDataProc.getAmpStageCH2());
			triggerProc.processTriggerReq(triggerMSG);

			//Update the trigger data within the class SampleDataProcessing
			//In case of an occuring Single-Shot event
			sampleDataProc.setTriggerMSG(triggerMSG);

			break;

		case CHANNEL_CTRL:
			ChannelControlMSG channelControl = (ChannelControlMSG)genericMessage;

			if(channelControl.getChannelNr() == OpenLabDevice.CH1) {
				//If the channel state has changed, the device needs to be informed
				if(this.channelControlCH1.isChannelActive() != channelControl.isChannelActive()) {
					sampleDataProc.setupChannelInputConfig(OpenLabDevice.CH1, channelControl.isChannelActive());
				}

				this.channelControlCH1 = channelControl;						
				sampleDataProc.setChCtrlCH1MSG(this.channelControlCH1);
			} else if(channelControl.getChannelNr() == OpenLabDevice.CH2) {
				//If the channel state has changed, the device needs to be informed
				if(this.channelControlCH2.isChannelActive() != channelControl.isChannelActive()) {
					sampleDataProc.setupChannelInputConfig(OpenLabDevice.CH2, channelControl.isChannelActive());
				}						

				this.channelControlCH2 = channelControl;						
				sampleDataProc.setChCtrlCH2MSG(this.channelControlCH2);	
			}				

			break;

		case SAMPLE_RATE: 
			sampleRateMSG = (SampleRateMSG)genericMessage;

			sampleRateProc.transmitSampleRate(sampleRateMSG);	
			sampleDataProc.setSampleRateSettings(sampleRateMSG);
			break;

		case MEASUREMENT:
			MeasurementMSG measurementMSG = (MeasurementMSG)genericMessage;
			measurementProc.getClqMeasReq().add(measurementMSG);
			break;

		case MEASUREMENT_RESP:			
			MeasurementMSG measurementMSG_RESP = new MeasurementMSG();

			measurementMSG_RESP = (MeasurementMSG)genericMessage;			
			msq_Handler.getMsqUIReq().add(measurementMSG_RESP);
			break;

		case SNAPSHOT:
			SnapshotMSG snapshotMSG = (SnapshotMSG)genericMessage;
			SnapshotProc snapshotProc = new SnapshotProc(snapshotMSG.getFileName(),
					snapshotMSG.getFilePath(), snapshotMSG.getFileFormat(),
					snapshotMSG.getColor(), snapshotMSG.getImage());

			//In case of an error, inform the requester (UI)
			if(snapshotProc.takeSnapshot() != 0) {					
				ErrorMSG errorMSG = new ErrorMSG();

				errorMSG.setMsgCode(MessageCode.ERROR_MSG);
				errorMSG.setErrorCode(ErrorCode.SNAPSHOT);
				errorMSG.setErrorType(snapshotProc.getErrorType());
				errorMSG.setSeverity(Severity.Major);

				msq_Handler.getMsqUIReq().add(errorMSG);
			}

			break;

		case UIControl:
			processUIControlRedir(genericMessage);
			break;

		case GENERIC_EVENT:
			GenericEventMSG genericEventMSG = new GenericEventMSG();

			genericEventMSG = (GenericEventMSG)genericMessage;			
			sampleDataProc.setGenericEvent(genericEventMSG.getGenericEvent());
			sampleDataProc.processSampleData(-1, null);
			ampStageVerification();
			break;

		case EXPORT_CSV: 
			ExportCSVMSG exportCSVMSG = new ExportCSVMSG();

			exportCSVMSG = (ExportCSVMSG)genericMessage;			
			processCSVExport(exportCSVMSG);
			break;

		case DEVICE_INTER_REQ:
			processDeviceInterfaceReq(genericMessage);
			break;
			
		case DEVICE_INTER_UPD:
			processDeviceInterfaceRedir(genericMessage);
			break;
			
		case ERROR_MSG:			
			break;			

		default:
			System.err.println("Request-Processing: Invalid MSG received - Removed per default!");
			break;
		}		
		return;
	}


	private void processDeviceInterfaceReq(GenericMessage genericMessage) {
		DeviceMSG deviceMSG = new DeviceMSG();
		deviceMSG = (DeviceMSG)genericMessage;
		
		serialDevice.accessSelectedPort(SelectedPortAccess.SET_SELECTED_PORT, deviceMSG.getSelectedPort());
		serialDevice.connect(serialDevice.DEFAULT_BAUDRATE, false);		
		
		return;
	}
	
	
	private void processDeviceInterfaceRedir(GenericMessage genericMessage) {
		DeviceMSG deviceMSG = new DeviceMSG();
		deviceMSG = (DeviceMSG)genericMessage;
		msq_Handler.getMsqUIReq().add(deviceMSG);
		
		return;
	}
	
	
	/**
	 * @param genericMessage
	 */
	private void processUIControlRedir(GenericMessage genericMessage) {
		UIControlMSG uiControlMSG = new UIControlMSG();
		uiControlMSG = (UIControlMSG)genericMessage;
		msq_Handler.getMsqUIReq().add(uiControlMSG);

		return;
	}


	/**
	 * @param exportCSVMSG
	 */
	private void processCSVExport(ExportCSVMSG exportCSVMSG) {
		ArrayList<Double> measuredDataCH1 = null;
		ArrayList<Double> measuredDataCH2 = null;

		if(channelControlCH1.isChannelActive()) {
			measuredDataCH1 = new ArrayList<Double>();
		}

		if(channelControlCH2.isChannelActive()) {
			measuredDataCH2= new ArrayList<Double>();
		}

		sampleDataProc.getCalculatedSignalData(measuredDataCH1, measuredDataCH2);

		ExportCSVProc exportCSVProc = new ExportCSVProc(channelControlCH1.isChannelActive(),
				channelControlCH2.isChannelActive(), sampleRateMSG.getHorizontalScaling(),
				exportCSVMSG.getFileName(),
				exportCSVMSG.getFilePath(),
				exportCSVMSG.getLangFormat());		

		exportCSVProc.setMeasuredDataCH1(measuredDataCH1);
		exportCSVProc.setMeasuredDataCH2(measuredDataCH2);
		exportCSVProc.executeCSVExport();
	}

	
	/**
	 * 
	 */
	private void checkRawSampleDataQueues() {
		if(!msq_Handler.getRawSampleDataCH1().isEmpty()) {				
			sampleDataProc.processSampleData(OpenLabDevice.CH1, msq_Handler.getRawSampleDataCH1().remove());	

			ampStageVerification();

			if(msq_Handler.getRawSampleDataCH1().size() >= 5) {
				msq_Handler.getRawSampleDataCH1().clear();
			}
		}

		if(!msq_Handler.getRawSampleDataCH2().isEmpty()) {				
			sampleDataProc.processSampleData(OpenLabDevice.CH2, msq_Handler.getRawSampleDataCH2().remove());	

			ampStageVerification();

			if(msq_Handler.getRawSampleDataCH2().size() >= 5) {
				msq_Handler.getRawSampleDataCH2().clear();
			}
		}

		if(sampleDataProc.getGenericEvent() == GenericEvent.RUN_STOP_PROC) {
			//All received sample data after a Run/Stop event are not
			//valid anymore and need to be deleted
			msq_Handler.getRawSampleDataCH1().clear();
			msq_Handler.getRawSampleDataCH2().clear();

			sampleDataProc.processSampleData(-1, null);
		}

		return;
	}


	private void ampStageVerification() {
		//If the ampStage has changed, the device needs to adjust the trigger parameter
		if(sampleDataProc.getAmpStageCH1() != triggerProc.getAmpStageCH1()) {
			triggerProc.setAmpStageCH1(sampleDataProc.getAmpStageCH1());
			triggerProc.setAmpStageCH2(sampleDataProc.getAmpStageCH2());
			triggerProc.updateAmpStage(OpenLabDevice.CH1);
			msq_Handler.getRawSampleDataCH1().clear();
		}		

		if(sampleDataProc.getAmpStageCH2() != triggerProc.getAmpStageCH2()) {
			triggerProc.setAmpStageCH1(sampleDataProc.getAmpStageCH1());
			triggerProc.setAmpStageCH2(sampleDataProc.getAmpStageCH2());
			triggerProc.updateAmpStage(OpenLabDevice.CH2);
			msq_Handler.getRawSampleDataCH2().clear();
		}		
		return;
	}

	/**
	 * @param sleepTime
	 */
	private void setThreadInSleepMode(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}


	public void terminateConnection() {
		serialDevice.disconnect(true);
		return;
	}
}

/* EOF */