/**
 * 
 */
package openLab.processing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import openLab.controller.ControlUtilities;
import openLab.controller.ImageComboBox;
import openLab.device.CouplingType;
import openLab.device.DeviceType;
import openLab.device.SamplingMode;
import openLab.device.serial.SerialDevice;
import openLab.message.packet.ChannelControlMSG;
import openLab.message.packet.GenericEventMSG.GenericEvent;
import openLab.message.packet.MessageCode;
import openLab.message.packet.SampleRateMSG;
import openLab.message.packet.TriggerMSG;
import openLab.message.packet.UIControlMSG;
import openLab.message.packet.UIControlMSG.Components;
import openLab.message.packet.UIControlMSG.ProcState;
import openLab.resource.MSQ_Handler;
import openLab.resource.OpenLabDevice;

/**
 * @author Markus Lechner
 *
 */
public class SampleDataProc {
	private final int MAX_AMPLYFICATION_ITERATIONS = 6;
	private final int SINGLE_SHOT_PROC_FAILED = 100;
	private final double MAX_HORZTL_SCALING = 250.0e-9;
	private final double CORR_ETS_MAX_SAMPLING = 0.175;	

	public final static int AMP_STAGE0 = 0;
	public final static int AMP_STAGE1 = 1;
	public final static int AMP_STAGE2 = 2;
	public final static int AMP_STAGE3 = 3;
	public final static int AMP_STAGE4 = 4;
	public final static int AMP_STAGE5 = 5;
	public final static int AMP_STAGE6 = 6;

	public final static double AMP_STAGE0_MAX_VALUE = 1.525;
	public final static double AMP_STAGE1_MAX_VALUE = 2.505;
	public final static double AMP_STAGE2_MAX_VALUE = 3.455;
	public final static double AMP_STAGE3_MAX_VALUE = 5.110;
	public final static double AMP_STAGE4_MAX_VALUE = 7.400;
	public final static double AMP_STAGE5_MAX_VALUE = 9.200;
	public final static double AMP_STAGE6_MAX_VALUE = 10.550;	

	private ArrayList<Integer> cpySampleDataCH1 = new ArrayList<Integer>(600);
	private ArrayList<Integer> cpySampleDataCH2 = new ArrayList<Integer>(600);
	private ArrayList<Integer> tempSampleDataPacket = new ArrayList<Integer>(600);

	ObservableList<Data<Number, Number>> sampleDataCH1 = FXCollections.observableArrayList();
	ObservableList<Data<Number, Number>> sampleDataCH2 = FXCollections.observableArrayList();

	private SerialDevice serialDevice = null;
	private MeasurementProc measurementProc = null;

	private int ampStageCH1 = OpenLabDevice.DEFAULT_AMP_STAGE;
	private int ampStageCH2 = OpenLabDevice.DEFAULT_AMP_STAGE;

	private int ampIterationCH1 = 0;
	private int ampIterationCH2 = 0;

	private int frequency = 0;

	private static ChannelControlMSG chCtrlCH1MSG = new ChannelControlMSG();
	private static ChannelControlMSG chCtrlCH2MSG = new ChannelControlMSG();
	private SampleRateMSG sampleRateSettings = new SampleRateMSG();
	private TriggerMSG triggerMSG = new TriggerMSG();

	private RunControlProc runControlProc = null;
	private GenericEvent genericEvent = GenericEvent.EXECUTION;

	private MSQ_Handler msq_Handler = MSQ_Handler.getInstance();

	double prevSamplerate;
	boolean initIsDone = true;

	/**
	 * 
	 */
	public SampleDataProc(SerialDevice serialDevice, MeasurementProc measurementProc) {
		this.serialDevice = serialDevice;		
		this.measurementProc = measurementProc;

		runControlProc = new RunControlProc();				
	}



	/**
	 * @param channelNr
	 * @param sampleData
	 * @return
	 */
	public int processSampleData(int channelNr, ArrayList<Integer> sampleData) {

		if(sampleData != null) {
			tempSampleDataPacket.addAll(sampleData);
			frequency = getFrequencyValue(tempSampleDataPacket);

			//The latest valid sample data are copied to
			//provide sample date in case of a Run/Stop Event
			//Due to new sample data are not required for a Run/Stop Event
			if(channelNr == OpenLabDevice.CH1) {
				cpySampleDataCH1.clear();
				cpySampleDataCH1.addAll(tempSampleDataPacket);
				runControlProc.setFrequencyValueCH1(frequency);
			} else if(channelNr == OpenLabDevice.CH2) {
				cpySampleDataCH2.clear();
				cpySampleDataCH2.addAll(tempSampleDataPacket);
				runControlProc.setFrequencyValueCH2(frequency);
			}
		}		

		if(genericEvent == GenericEvent.EXECUTION) {
			if(sampleData == null) {
				return -1;
			}
			sampleDataAnalysis(channelNr, tempSampleDataPacket, true, false, true);	
		} else if(genericEvent == GenericEvent.AUTO_SCALE) {
			runControlProc.executeAutoScaling(channelNr, tempSampleDataPacket);
		} else if(genericEvent == GenericEvent.RUN_STOP_PROC) {
			runControlProc.executeRunStopRequest();
		} else if(genericEvent == GenericEvent.RUN_STOP_ABRT) {			
			runControlProc.resetRunStopRequest();
			genericEvent = GenericEvent.EXECUTION;
		} else if(genericEvent == GenericEvent.SINGLE_SHOT_PROC) {			
			runControlProc.executeSingleShotRequest(channelNr, tempSampleDataPacket);
		} else if(genericEvent == GenericEvent.SINGLE_SHOT_ABRT) {
			runControlProc.resetSingleShotRequest();
			genericEvent = GenericEvent.EXECUTION;
		} else {
			tempSampleDataPacket.clear();
			return -1;
		}
		tempSampleDataPacket.clear();
		return 0;
	}


	/**
	 * @param measuredDataCH1
	 * @param measuredDataCH2
	 */
	public void getCalculatedSignalData(ArrayList<Double> measuredDataCH1, ArrayList<Double> measuredDataCH2) {		
		if (measuredDataCH1 != null) {
			calculateMeasuredVirtualData(OpenLabDevice.CH1,ampStageMaxValueLUT(OpenLabDevice.CH1,ampStageCH1),cpySampleDataCH1,null,measuredDataCH1);
		}

		if (measuredDataCH2 != null) {
			calculateMeasuredVirtualData(OpenLabDevice.CH2,ampStageMaxValueLUT(OpenLabDevice.CH2,ampStageCH2),cpySampleDataCH2,null,measuredDataCH2);
		}
		return;		
	}


	/**
	 * @param sampleData
	 * @return
	 */
	private int getFrequencyValue(ArrayList<Integer> sampleData) {
		OpenLabDevice openLabDevice = OpenLabDevice.getInstance();

		if(openLabDevice.isHWFreqSupported()) {
			return (sampleData.remove((sampleData.size()-1)));
		}		
		return 0;
	}


	/**
	 * @param channelNr
	 * @param sampleData
	 * @param signalRangeCheck
	 * @param isSingleShot
	 * @return
	 */
	private int sampleDataAnalysis(int channelNr, ArrayList<Integer> sampleData, boolean signalRangeCheck,
			boolean isSingleShot, boolean isFreqAvg) {

		int retVal;

		if(sampleData.isEmpty()) {
			return -1;
		}

		if(signalRangeCheck) {
			retVal = signalRangeDetection(channelNr, sampleData, false);				
			if(retVal != 1) {
				return retVal;
			}
		}

		ObservableList<Data<Number, Number>> tempSampleData = FXCollections.observableArrayList();

		if(channelNr == OpenLabDevice.CH1) {				
			tempSampleData = adjustSampleDataTodrawingArea(sampleData,ampStageMaxValueLUT(channelNr, ampStageCH1),OpenLabDevice.CH1,isSingleShot,isFreqAvg);

			if(tempSampleData != null) {				
				msq_Handler.getSampleDataCH1().add(tempSampleData);	
			} else {
				return SINGLE_SHOT_PROC_FAILED;
			}
		} else  if(channelNr == OpenLabDevice.CH2) {
			tempSampleData = adjustSampleDataTodrawingArea(sampleData,ampStageMaxValueLUT(channelNr, ampStageCH2),OpenLabDevice.CH2,isSingleShot,isFreqAvg);

			if(tempSampleData != null) {				
				msq_Handler.getSampleDataCH2().add(tempSampleData);	
			} else {
				return SINGLE_SHOT_PROC_FAILED;
			}
		} else {
			return OpenLabDevice.INVALID_CH;
		}

		return 0;
	}


	private int signalRangeDetection(int channelNr, ArrayList<Integer> sampleData, boolean ignIteration) {
		int tempAmpStage;
		int maxADCValue = Collections.max(sampleData);
		int minADCValue = Collections.min(sampleData);

		if((maxADCValue >= (OpenLabDevice.MAX_ADC_NO_CLIPPING + 4)) || (minADCValue <= (OpenLabDevice.MIN_ADC_NO_CLIPPING + 4))) {
			if((channelNr == OpenLabDevice.CH1) && (ampStageCH1 <= AMP_STAGE5)){
				tempAmpStage = ampStageCH1 + 1;

				if(serialDevice.setInputConfiguration(DeviceType.OSCILLOSCOPE,
						OpenLabDevice.CH1, tempAmpStage, CouplingType.COUPLING_TYPE_DC)) {

					ampStageCH1 = tempAmpStage;
					System.out.println("Attenuated CH1 to AmpStage" + tempAmpStage);
				} else {
					System.err.println("Attenuation CH1 failed");
				}

			} else if((channelNr == OpenLabDevice.CH2) && (ampStageCH2 <= AMP_STAGE5)) {
				tempAmpStage = ampStageCH2 + 1;

				if(serialDevice.setInputConfiguration(DeviceType.OSCILLOSCOPE,
						OpenLabDevice.CH2, tempAmpStage, CouplingType.COUPLING_TYPE_DC)) {

					ampStageCH2 = tempAmpStage;
					System.out.println("Attenuated CH2 to AmpStage" + tempAmpStage);
				} else {
					System.err.println("Attenuation CH2 failed");
				}
			} else if (!((channelNr == OpenLabDevice.CH1) || (channelNr == OpenLabDevice.CH2))) {
				return OpenLabDevice.INVALID_CH;
			} else {
				System.out.println("Attention: Input Voltage on CH" + channelNr + " is too high!");
			}
		} else {
			int minRange;
			int adcValueDiff = Math.abs(maxADCValue - minADCValue);			
			double maxADCratio = Math.abs(OpenLabDevice.MAX_ADC_NO_CLIPPING - maxADCValue);
			double minADCratio = Math.abs(OpenLabDevice.MIN_ADC_NO_CLIPPING - minADCValue);

			if(channelNr == OpenLabDevice.CH1) {
				tempAmpStage = ampStageCH1;

				if(shouldAmplify(OpenLabDevice.CH1, tempAmpStage, adcValueDiff, ignIteration)) {
					minRange = getMinRangeToNextStage(tempAmpStage);

					if(tempAmpStage >= AMP_STAGE1 && (maxADCratio >= minRange && minADCratio >= minRange)) {
						tempAmpStage--;

						if(serialDevice.setInputConfiguration(DeviceType.OSCILLOSCOPE,
								OpenLabDevice.CH1, tempAmpStage, CouplingType.COUPLING_TYPE_DC)) {

							ampStageCH1 = tempAmpStage;
							System.out.println("Amplified CH1 to AmpStage" + tempAmpStage);
						} else {
							System.err.println("Amplification CH1 failed");
						}			
					}
				}
			} else if(channelNr == OpenLabDevice.CH2) {
				tempAmpStage = ampStageCH2;

				if(shouldAmplify(OpenLabDevice.CH2, tempAmpStage, adcValueDiff, ignIteration)) {
					minRange = getMinRangeToNextStage(tempAmpStage);

					if(tempAmpStage >= AMP_STAGE1 && (maxADCratio >= minRange && minADCratio >= minRange)) {
						tempAmpStage--;

						if(serialDevice.setInputConfiguration(DeviceType.OSCILLOSCOPE,
								OpenLabDevice.CH2, tempAmpStage, CouplingType.COUPLING_TYPE_DC)) {

							ampStageCH2 = tempAmpStage;
							System.out.println("Amplified CH2 to AmpStage" + tempAmpStage);
						} else {
							System.err.println("Amplification CH2 failed");
						}			
					}
				}
			} else {
				return OpenLabDevice.INVALID_CH;
			}
		}		
		return 1;
	}


	private boolean shouldAmplify(int channelNr ,int ampStage, int adcValueDiff, boolean ignIteration) {
		int amplificationIterations;

		if(!ignIteration) {
			if(channelNr == OpenLabDevice.CH1) {
				amplificationIterations  = ampIterationCH1;
			} else if (channelNr == OpenLabDevice.CH2) {
				amplificationIterations = ampIterationCH2;
			} else {
				return false;
			}
		} else {
			amplificationIterations = MAX_AMPLYFICATION_ITERATIONS;
		}

		if(ampStage == AMP_STAGE0){
			amplificationIterations = 0;
		} else if((ampStage == AMP_STAGE1) && (adcValueDiff <= 70)){
			amplificationIterations++;
		} else if((ampStage == AMP_STAGE2) && (adcValueDiff <= 90)){
			amplificationIterations++;
		} else if((ampStage == AMP_STAGE3) && (adcValueDiff <= 80)){
			amplificationIterations++;
		} else if((ampStage == AMP_STAGE4) && (adcValueDiff <= 83)){
			amplificationIterations++;
		} else if((ampStage == AMP_STAGE5) && (adcValueDiff <= 98)){
			amplificationIterations++;
		} else if((ampStage == AMP_STAGE6) && (adcValueDiff <= 103)){
			amplificationIterations++;
		} else {
			amplificationIterations = 0;
		}

		if(channelNr == OpenLabDevice.CH1) {
			ampIterationCH1 = amplificationIterations;

			if(ampIterationCH1 >= MAX_AMPLYFICATION_ITERATIONS) {
				ampIterationCH1 = 0;
				return true;
			} 
		} else {
			ampIterationCH2 = amplificationIterations;

			if(ampIterationCH2 >= MAX_AMPLYFICATION_ITERATIONS) {
				ampIterationCH2 = 0;
				return true;
			} 
		}
		return false;
	}


	/**
	 * @param sampleData
	 * @param ampStage
	 * @param channelNum
	 * @param isSingleShot
	 * @param isFreqAvg
	 * @param probeAtt
	 * @return
	 */
	public ObservableList<Data<Number, Number>> adjustSampleDataTodrawingArea(ArrayList<Integer> sampleData,
			double ampStage, int channelNum, boolean isSingleShot, boolean isFreqAvg) {

		ArrayList<Double> tempMeasuredVoltageLvl = new ArrayList<Double>();
		List<Data<Number, Number>> sigGraphData = new ArrayList<Data<Number, Number>>();

		calculateMeasuredVirtualData(channelNum,ampStage,sampleData,sigGraphData,tempMeasuredVoltageLvl);

		if(isSingleShot) {
			double voltageLvl = 0.0;

			for(int i=0; i<tempMeasuredVoltageLvl.size(); i++) {
				voltageLvl = tempMeasuredVoltageLvl.get(i);

				if(triggerMSG.getTriggerValue() < 0) {
					if(voltageLvl >= triggerMSG.getTriggerValue()) {
						continue;//Signal graph update not allowed..
					}
					return updateDataQueues(channelNum,tempMeasuredVoltageLvl,sigGraphData, isSingleShot, isFreqAvg);
				}
				if(voltageLvl <= triggerMSG.getTriggerValue()) {
					continue; //Signal graph update not allowed..
				}
				return updateDataQueues(channelNum,tempMeasuredVoltageLvl,sigGraphData, isSingleShot, isFreqAvg);
			}
		} else {
			return updateDataQueues(channelNum,tempMeasuredVoltageLvl,sigGraphData, isSingleShot, isFreqAvg);
		}
		return null;
	}



	private ObservableList<Data<Number, Number>> updateDataQueues(int channelNum,ArrayList<Double> tempMeasuredVoltageLvl, 
			List<Data<Number, Number>> update, boolean isSingleShot, boolean isFreqAvg){

		OpenLabDevice openLabDevice = OpenLabDevice.getInstance();

		if(openLabDevice.isHWFreqSupported()) {
			tempMeasuredVoltageLvl.add((double)frequency);

			if(!isFreqAvg) {
				//The number -1.0 (NO_FREQ_AVERAGING) is added to the tail of the queue to
				//indicate that the frequency values shall not be averaged.
				tempMeasuredVoltageLvl.add(MeasurementProc.NO_FREQ_AVERAGING);
			}

			if(channelNum == OpenLabDevice.CH1){
				measurementProc.getClqMeasCH1().add(tempMeasuredVoltageLvl);			
			} else if(channelNum == OpenLabDevice.CH2){
				measurementProc.getClqMeasCH2().add(tempMeasuredVoltageLvl);	
			} else {
				return null;
			}	
		}		

		ObservableList<Data<Number, Number>> list = FXCollections.observableArrayList(update);
		return list;
	}


	private int calculateMeasuredVirtualData(int channelNr, double ampStageV,ArrayList<Integer> sampleData,
			List<Data<Number, Number>> sigGraphData, ArrayList<Double> measuredVoltLvlData) {

		boolean isSignalInv;
		double verticalScaling;
		double verticalPos;	

		if(channelNr == OpenLabDevice.CH1) {
			isSignalInv = chCtrlCH1MSG.isSignalInverted();
			verticalScaling = chCtrlCH1MSG.getVerticalScale();
			verticalPos = chCtrlCH1MSG.getVerticalPos();
		} else if(channelNr == OpenLabDevice.CH2) {
			isSignalInv = chCtrlCH2MSG.isSignalInverted();
			verticalScaling = chCtrlCH2MSG.getVerticalScale();
			verticalPos = chCtrlCH2MSG.getVerticalPos();
		} else {
			return OpenLabDevice.INVALID_CH;
		}

		double incLevelXAxis;
		double divisions = sampleRateSettings.getHorizontalScaling() * sampleRateSettings.getSampleRate();

		if(genericEvent == GenericEvent.RUN_STOP_PROC) {
			divisions = sampleRateSettings.getHorizontalScaling() * runControlProc.getPrevSampleRate();
		}

		OpenLabDevice openLabDevice = OpenLabDevice.getInstance();

		if(sampleRateSettings.getSampleMode().equals(SamplingMode.SEQUENTIAL_ETS)) {		
			if(sampleRateSettings.getHorizontalScaling() == MAX_HORZTL_SCALING) {
				incLevelXAxis = ((openLabDevice.getMaxPacketSizeETS() / ControlUtilities.X_AXIS_DIVISIONS) / divisions)
						- (CORR_ETS_MAX_SAMPLING*4.0);
			} else {
				incLevelXAxis = ((openLabDevice.getMaxPacketSizeETS() / ControlUtilities.X_AXIS_DIVISIONS) / divisions)
						- CORR_ETS_MAX_SAMPLING;
			}
		} else if((sampleRateSettings.getSampleMode().equals(SamplingMode.REALTIME_SAMPLING))
				&& (sampleRateSettings.getSampleRate() == openLabDevice.getMaxSampleRateRTS())) {

			incLevelXAxis = ((openLabDevice.getMaxPacketSizeRTS() / ControlUtilities.X_AXIS_DIVISIONS) / divisions)
					- (CORR_ETS_MAX_SAMPLING / ControlUtilities.X_AXIS_DIVISIONS);
		} else {
			incLevelXAxis = ((openLabDevice.getMaxPacketSizeRTS() / ControlUtilities.X_AXIS_DIVISIONS) / divisions);
		}		

		double xAxis = 0.0;
		double virtualValue;	
		double measuredVoltageLvl;

		for(int i=0; i < sampleData.size(); i++) {
			measuredVoltageLvl = ((sampleData.get(i) - OpenLabDevice.VIRTUAL_ADC_ZERO_POINT)
					/ (OpenLabDevice.MAX_ADC_NO_CLIPPING - OpenLabDevice.VIRTUAL_ADC_ZERO_POINT)) * ampStageV;

			//Necessary to reverse the signal inversion
			if(!isSignalInv) {
				measuredVoltageLvl *= -1.0;
			}

			if(sigGraphData != null) {
				virtualValue = (measuredVoltageLvl/verticalScaling) + verticalPos;
				sigGraphData.add(new Data<>(xAxis, virtualValue));
				xAxis += incLevelXAxis;
			}

			if (measuredVoltLvlData != null) {
				measuredVoltLvlData.add(measuredVoltageLvl);
			}			
		}
		return 0;
	}


	public boolean setupChannelInputConfig(int channelNr, boolean setChannelActive) {		
		int ampStage;
		CouplingType couplingType = CouplingType.COUPLING_TYPE_OFF;

		if(channelNr == OpenLabDevice.CH1) {
			ampStage = ampStageCH1;
		} else if (channelNr == OpenLabDevice.CH2) {
			ampStage = ampStageCH2;
		} else {
			return false;
		}

		if (this.genericEvent == GenericEvent.RUN_STOP_PROC) {
			if(serialDevice.setInputConfiguration(DeviceType.OSCILLOSCOPE, channelNr, ampStage, couplingType)) {			
				return true;
			}
			return false;
		}

		if(setChannelActive) {
			couplingType = CouplingType.COUPLING_TYPE_DC;
		} 

		if(serialDevice.setInputConfiguration(DeviceType.OSCILLOSCOPE, channelNr, ampStage, couplingType)) {			
			return true;
		}

		return false;
	}



	private int getMinRangeToNextStage(int ampStage) {
		if(ampStage == AMP_STAGE0){
			return 30; //Stage 0 -> Stage 1 space
		} else if(ampStage == AMP_STAGE1){
			return 28; //Stage 1 -> Stage 2 space
		} else if(ampStage == AMP_STAGE2){
			return 26; //Stage 2 -> Stage 3 space
		} else if(ampStage == AMP_STAGE3){
			return 26; //Stage 3 -> Stage 4 space
		} else if(ampStage == AMP_STAGE4){
			return 25; //Stage 4 -> Stage 5 
		} else if(ampStage == AMP_STAGE5){
			return 18; //Stage 5 -> Stage 6 space
		} else if(ampStage == AMP_STAGE6){
			return 16; //Stage 6 -> Stage 7 space
		} else {
			return 0;
		}
	}


	//TODO

	/**
	 * @param channelNr
	 * @param ampStage
	 * @return
	 */
	public static double ampStageMaxValueLUT(int channelNr, int ampStage) {
		double probeAtt = getProbeAttenuation(channelNr);
		
		switch (ampStage) {
		case AMP_STAGE0:
			return (AMP_STAGE0_MAX_VALUE * probeAtt);
		case AMP_STAGE1:
			return (AMP_STAGE1_MAX_VALUE * probeAtt);
		case AMP_STAGE2:
			return (AMP_STAGE2_MAX_VALUE * probeAtt);	
		case AMP_STAGE3:
			return (AMP_STAGE3_MAX_VALUE * probeAtt);
		case AMP_STAGE4:
			return (AMP_STAGE4_MAX_VALUE * probeAtt);
		case AMP_STAGE5:
			return (AMP_STAGE5_MAX_VALUE * probeAtt);
		case AMP_STAGE6:
			return (AMP_STAGE6_MAX_VALUE * probeAtt);			
		default:
			return 0.0;
		}
	}


	/**
	 * @param channelNr
	 * @return
	 */
	private static double getProbeAttenuation(int channelNr) {
		double probeAtt;

		if(channelNr == OpenLabDevice.CH1) {
			probeAtt = chCtrlCH1MSG.getProbeAtten();
		} else if(channelNr == OpenLabDevice.CH2) {
			probeAtt = chCtrlCH2MSG.getProbeAtten();
		} else {
			return -1.0;
		}
		return ((1.0 / 10.0) * probeAtt);
	}


	/**
	 * @return the measurementProc
	 */
	public MeasurementProc getMeasurementProc() {
		return measurementProc;
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
	/**
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	/**
	 * @return the chCtrlCH1MSG
	 */
	public ChannelControlMSG getChCtrlCH1MSG() {
		return chCtrlCH1MSG;
	}
	/**
	 * @param chCtrlCH1MSG the chCtrlCH1MSG to set
	 */
	public void setChCtrlCH1MSG(ChannelControlMSG chCtrlCH1MSG) {
		SampleDataProc.chCtrlCH1MSG = chCtrlCH1MSG;
	}
	/**
	 * @return the chCtrlCH2MSG
	 */
	public ChannelControlMSG getChCtrlCH2MSG() {
		return chCtrlCH2MSG;
	}
	/**
	 * @param chCtrlCH2MSG the chCtrlCH2MSG to set
	 */
	public void setChCtrlCH2MSG(ChannelControlMSG chCtrlCH2MSG) {
		SampleDataProc.chCtrlCH2MSG = chCtrlCH2MSG;
	}
	/**
	 * @return the sampleRateSettings
	 */
	public SampleRateMSG getSampleRateSettings() {
		return sampleRateSettings;
	}
	/**
	 * @param sampleRateSettings the sampleRateSettings to set
	 */
	public void setSampleRateSettings(SampleRateMSG sampleRateSettings) {
		this.sampleRateSettings = sampleRateSettings;
	}
	/**
	 * @return the triggerMSG
	 */
	public TriggerMSG getTriggerMSG() {
		return triggerMSG;
	}



	/**
	 * @param triggerMSG the triggerMSG to set
	 */
	public void setTriggerMSG(TriggerMSG triggerMSG) {
		this.triggerMSG = triggerMSG;
	}



	/**
	 * @return the sampleDataCH1
	 */
	public ObservableList<Data<Number, Number>> getSampleDataCH1() {
		return sampleDataCH1;
	}
	/**
	 * @return the sampleDataCH2
	 */
	public ObservableList<Data<Number, Number>> getSampleDataCH2() {
		return sampleDataCH2;
	}
	/**
	 * @return the genericEvent
	 */
	public GenericEvent getGenericEvent() {
		return genericEvent;
	}
	/**
	 * @param genericEvent the genericEvent to set
	 */
	public void setGenericEvent(GenericEvent genericEvent) {
		this.genericEvent = genericEvent;
	}


	/**
	 * @author Markus Lechner
	 *
	 */
	protected class RunControlProc {		
		private final int INPUT_RANGE_PROG = 1;
		private final int INPUT_RANGE_DONE = 2;
		private final int PARAMATERS_ADJUSTED = 3;
		private final int CHANNEL_DEACTIVATED = 4;		
		private final int FREQ_ANALYSIS_UNSUPPORTED = 5;
		private final int FREQ_ANALYSIS_PROC = 6;
		private final int FREQ_ANALYSIS_DONE = 7;		
		private final int FREQ_ANALYSIS_HORZ_SCALE_DONE = 8;
		private final int SAMPLE_RATE_LIMIT_REACHED = 9;
		private final int FREQ_ANALYSIS_FAILED = 10;
		private final int SINGLE_SHOT_PROC = 11;
		private final int SINGLE_SHOT_DONE = 12;

		private final int MAX_FREQ_ANALYSIS_SAMPLES = 5;
		private final double MAX_FREQUENCY_TOLERANZ = 0.1; // 10%

		private boolean signalCheckCH1 = false;
		private boolean signalCheckCH2 = false;
		private boolean isCH1active = true;
		private boolean isCH2active = true;

		private boolean isAutoScaleInit = false;
		private boolean isInputSignalCheckDone = false;
		private boolean triggerSettingsSet = false;
		private boolean isParamAdjusted = false;
		//Run/Stop Mode
		private boolean isRunStopModeInit = false;
		//Single-Shot Mode
		private boolean isSingleShotInit = false;
		private boolean isSingleShotDoneCH1 = false;
		private boolean isSingleShotDoneCH2 = false;

		private int prevAmpStageCH1 = -1;
		private int prevAmpStageCH2 = -1;

		private int horizScaleIndex;
		private int horizScaleIndexInit;
		private int scaleProcBothCH = -1;
		//Run/Stop Mode
		private int frequencyValueCH1 = 0;
		private int frequencyValueCH2 = 0;

		private double prevSampleRate;

		private ArrayList<Double> horizScaleSteps = new ArrayList<Double>(20);
		private ArrayList<Double> frequencyCH1 = new ArrayList<Double>(MAX_FREQ_ANALYSIS_SAMPLES);
		private ArrayList<Double> frequencyCH2 = new ArrayList<Double>(MAX_FREQ_ANALYSIS_SAMPLES);

		private ChannelControlMSG cpyChCtrlCH1MSG = new ChannelControlMSG();
		private ChannelControlMSG cpyChCtrlCH2MSG = new ChannelControlMSG();
		private SampleRateMSG cpySampleRateSettings = new SampleRateMSG();


		/**
		 * 
		 */
		public RunControlProc() {
			initHorScalList();
		}


		/**
		 * 
		 */
		private void initHorScalList() {
			for(int i=0; i<ControlUtilities.timeDivSteps.length;i++) {
				horizScaleSteps.add(ControlUtilities.timeDivSteps[i]);
			}

			horizScaleIndex = horizScaleSteps.indexOf(10.0e-3);		
			horizScaleIndexInit = horizScaleSteps.indexOf(1.0e-3);
			return;
		}


		/**
		 * @param channelNr
		 * @param sampleData
		 */
		private void executeAutoScaling(int channelNr, ArrayList<Integer> sampleData) {
			int stateSignalScan;

			if(!isAutoScaleInit) {
				System.out.println("Auto-Scale: Executing!");

				initAutoScalingParam();
				isAutoScaleInit = true;	

				System.out.println("Auto-Scale: Internal initialization is done!");
				return;
			}			

			if(!isInputSignalCheckDone) {
				if(chCtrlCH1MSG.isChannelActive() && (channelNr == OpenLabDevice.CH1) && !signalCheckCH1 ) {					
					stateSignalScan = checkInputSignalProp(channelNr, sampleData);

					if(stateSignalScan == INPUT_RANGE_DONE) {
						signalCheckCH1 = true;
						System.out.println("Auto-Scale: Input Check is done for CH1!");
					} else if(stateSignalScan == CHANNEL_DEACTIVATED) {
						isCH1active = false;
						signalCheckCH1 = true;
						System.out.println("Auto-Scale: Weak signal detected on CH1! CH1 has been switched off!");
					}
				} else if(chCtrlCH2MSG.isChannelActive() && (channelNr == OpenLabDevice.CH2) && !signalCheckCH2) {
					stateSignalScan = checkInputSignalProp(channelNr, sampleData);

					if(stateSignalScan == INPUT_RANGE_DONE) {
						signalCheckCH2 = true;
						System.out.println("Auto-Scale: Input Check is done for CH2!");
					} else if(stateSignalScan == CHANNEL_DEACTIVATED) {
						isCH2active = false;
						signalCheckCH2 = true;
						System.out.println("Auto-Scale: Weak signal detected on CH2! CH2 has been switched off!");
					}
				} 		

				if(signalCheckCH1 && signalCheckCH2) {
					isInputSignalCheckDone = true;	
				} else {
					return;
				}
			}

			if(!isParamAdjusted) {
				if(adjustUIparameters(channelNr, sampleData) == PARAMATERS_ADJUSTED) {
					isParamAdjusted = true;
					sendAutoScaleDoneNotif();
				} else {
					return;
				}
			}
			return;
		}


		/**
		 * 
		 */
		private void initAutoScalingParam() {
			UIControlMSG uiControlMSG = new UIControlMSG();

			uiControlMSG.setMsgCode(MessageCode.UIControl);
			uiControlMSG.setComponents(Components.DISABLE);
			uiControlMSG.setStatus("Executing Auto-Scale...");

			msq_Handler.getMsqProcessReq().add(uiControlMSG);

			return;
		}


		/**
		 * @param channelNr
		 * @param sampleData
		 * @return
		 */
		private int checkInputSignalProp(int channelNr, ArrayList<Integer> sampleData) {
			int ampStage;

			if(channelNr == OpenLabDevice.CH1) {
				if(prevAmpStageCH1 != ampStageCH1) {
					prevAmpStageCH1 = ampStageCH1;
					signalRangeDetection(OpenLabDevice.CH1, sampleData, true);				
					return INPUT_RANGE_PROG;
				}
				ampStage = ampStageCH1;
			} else if(channelNr == OpenLabDevice.CH2) {
				if(prevAmpStageCH2 != ampStageCH2) {
					prevAmpStageCH2 = ampStageCH2;
					signalRangeDetection(OpenLabDevice.CH2, sampleData, true);					
					return INPUT_RANGE_PROG;
				}
				ampStage = ampStageCH2;
			} else {
				return OpenLabDevice.INVALID_CH;
			}

			if(ampStage == AMP_STAGE0) {
				int minADCvalue = Collections.min(sampleData);
				int maxADCvalue = Collections.max(sampleData);
				int diffADCvalue = Math.abs(maxADCvalue - minADCvalue);

				if(diffADCvalue <= 15 && (maxADCvalue >= 123 && maxADCvalue <= 143)) {
					if(channelNr == OpenLabDevice.CH1) {
						UIControlMSG uiControlMSG = new UIControlMSG();
						uiControlMSG.setMsgCode(MessageCode.UIControl);

						uiControlMSG.setActivityCH1(Components.DISABLE);
						msq_Handler.getMsqProcessReq().add(uiControlMSG);
					} else {
						UIControlMSG uiControlMSG = new UIControlMSG();
						uiControlMSG.setMsgCode(MessageCode.UIControl);

						uiControlMSG.setActivityCH2(Components.DISABLE);
						msq_Handler.getMsqProcessReq().add(uiControlMSG);
					}
					return CHANNEL_DEACTIVATED;
				}				
			}
			return INPUT_RANGE_DONE;
		}


		/**
		 * 
		 */
		private void sendAutoScaleDoneNotif() {
			resetAutoScaleParam();

			UIControlMSG uiControlMSG = new UIControlMSG();

			uiControlMSG.setMsgCode(MessageCode.UIControl);
			uiControlMSG.setComponents(Components.ENABLE);
			uiControlMSG.setStatus("Executed Auto-Scale!");

			msq_Handler.getMsqProcessReq().add(uiControlMSG);

			setGenericEvent(GenericEvent.EXECUTION);
			System.out.println("Auto-Scale: Execution is done!");

			return;
		}


		/**
		 * 
		 */
		private void resetAutoScaleParam() {
			isCH1active = true;
			isCH2active = true;
			isAutoScaleInit = false;
			isInputSignalCheckDone = false;
			signalCheckCH1 = false;
			signalCheckCH2 = false;
			isInputSignalCheckDone = false;
			isParamAdjusted = false;
			triggerSettingsSet = false;

			prevAmpStageCH1 = -1;
			prevAmpStageCH2 = -1;	
			scaleProcBothCH = -1;

			horizScaleIndex =  horizScaleSteps.indexOf(10.0e-3);	
			return;
		}


		/**
		 * @param channelNr
		 * @param sampleData
		 * @return
		 */
		private int adjustUIparameters(int channelNr, ArrayList<Integer> sampleData) {
			if(!isCH1active && !isCH2active) {
				UIControlMSG uiControlMSG = new UIControlMSG();
				uiControlMSG.setMsgCode(MessageCode.UIControl);

				uiControlMSG.setTriggerSource("CH1");
				uiControlMSG.setStatus("No signal was found.\nCheck probe connections!");
				msq_Handler.getMsqProcessReq().add(uiControlMSG);

				return PARAMATERS_ADJUSTED;
			} else if(isCH1active && isCH2active) {
				if((channelNr == OpenLabDevice.CH1) && (scaleProcBothCH != PARAMATERS_ADJUSTED)) {			
					scaleProcBothCH = performScaleProcess(channelNr, "CH1", 2, true, sampleData, frequencyCH1);
				} else if((channelNr == OpenLabDevice.CH2) && (scaleProcBothCH == PARAMATERS_ADJUSTED)) {
					scaleProcBothCH = performScaleProcess(channelNr, null, 2, false, sampleData, null);

					return scaleProcBothCH;					
				}								
			} else if(isCH1active) {
				if(channelNr == OpenLabDevice.CH1) {					
					return (performScaleProcess(channelNr, "CH1", 0, true, sampleData, frequencyCH1));
				}				
			} else {
				if(channelNr == OpenLabDevice.CH2) {					
					return (performScaleProcess(channelNr, "CH2", 0, true, sampleData, frequencyCH2));
				}	
			}

			return -1;
		}


		/**
		 * @param channelNr
		 * @param triggerSrc
		 * @param vertOffset
		 * @param triggerLvlUpdate
		 * @param sampleData
		 * @param freqData
		 * @return
		 */
		private int performScaleProcess(int channelNr, String triggerSrc, int vertOffset, boolean triggerLvlUpdate,
				ArrayList<Integer> sampleData, ArrayList<Double> freqData) {

			if(!this.triggerSettingsSet && triggerLvlUpdate) {
				UIControlMSG uiControlMSG = new UIControlMSG();

				uiControlMSG.setMsgCode(MessageCode.UIControl);
				uiControlMSG.setTriggerSource(triggerSrc);
				uiControlMSG.setTriggerValue(0.0);
				uiControlMSG.setTriggerSlope(ImageComboBox.IMAGE_RISING_EDGE);
				msq_Handler.getMsqProcessReq().add(uiControlMSG);
				this.triggerSettingsSet = true;
				System.out.println("Auto-Scale: Default Trigger settings have been set");
			}

			if(freqData == null) {
				verticalAndTriggerScaling(channelNr, vertOffset, sampleData, triggerLvlUpdate);
				System.out.println("Auto-Scale: Vertical and Trigger scaling done");
				return PARAMATERS_ADJUSTED;
			}

			//The trigger level needs to be adjusted after every frequency analysis,
			//because it can not be ensured that the horizontal parameter is adjusted 
			//correctly after one frequency analysis.
			verticalAndTriggerScaling(channelNr, vertOffset, sampleData, triggerLvlUpdate);

			int freqProcState = frequencyAnalysis(channelNr, freqData, sampleData);					

			if((freqProcState == FREQ_ANALYSIS_DONE) || (freqProcState == FREQ_ANALYSIS_UNSUPPORTED)) {
				verticalAndTriggerScaling(channelNr, vertOffset, sampleData, triggerLvlUpdate);
				System.out.println("Auto-Scale: Vertical and Trigger scaling done");
				return PARAMATERS_ADJUSTED;
			}

			if(freqProcState == FREQ_ANALYSIS_FAILED) {
				verticalAndTriggerScaling(channelNr, vertOffset, sampleData, triggerLvlUpdate);
				System.out.println("Auto-Scale: Horizontal scaling failed! Out of frequency range!");
				return PARAMATERS_ADJUSTED;
			}

			return FREQ_ANALYSIS_PROC;
		}


		/**
		 * @param channelNr
		 * @param vertOffset
		 * @param sampleData
		 * @param triggerLvlUpdate
		 */
		private void verticalAndTriggerScaling(int channelNr,  int vertOffset, ArrayList<Integer> sampleData,
				boolean triggerLvlUpdate) {

			int ampStage;

			UIControlMSG uiControlMSG = new UIControlMSG();			
			uiControlMSG.setMsgCode(MessageCode.UIControl);

			if (channelNr == OpenLabDevice.CH1) {
				ampStage = ampStageCH1;
				uiControlMSG.setVerticalPosCH1((ControlUtilities.Y_AXIS_DIVISIONS / 2) + vertOffset);
			} else if(channelNr == OpenLabDevice.CH2) {
				ampStage = ampStageCH2;
				uiControlMSG.setVerticalPosCH2((ControlUtilities.Y_AXIS_DIVISIONS / 2) - vertOffset);
			} else {
				return;
			}

			ArrayList<Double> measuredVoltageLvl = new ArrayList<Double>(600);	

			calculateMeasuredVirtualData(channelNr,ampStageMaxValueLUT(channelNr,ampStage),sampleData,null,measuredVoltageLvl);

			//Remove pre-calculated frequency (int)
			measuredVoltageLvl.remove((measuredVoltageLvl.size()-1));

			double scalingFactor = 1.0;
			double vmin = Collections.min(measuredVoltageLvl);
			double vmax = Collections.max(measuredVoltageLvl);
			double vpp = Math.abs(vmax - vmin);

			double xMax = Math.max(Math.abs(vmin), Math.abs(vmax));
			double mean = Math.round(((vmin + vmax) / 2.0) * 10.0) / 10.0;

			if((((ampStage >=  AMP_STAGE5) && vpp <= 0.5)) || vpp <= 0.35) {
				if(vertOffset == 0) {
					scalingFactor = 2.0;
				}

				double scaleFactor = Math.floor(xMax/scalingFactor);				
				setVerticalScale(channelNr, scaleFactor , uiControlMSG);

			} else {
				if(vertOffset == 0) {
					scalingFactor = 2.0;
				}

				double scaleFactor = Math.ceil(xMax/(2.0 * scalingFactor));				
				setVerticalScale(channelNr, scaleFactor , uiControlMSG);

				if(triggerLvlUpdate) {
					uiControlMSG.setTriggerValue(mean);
				}
			}			

			msq_Handler.getMsqProcessReq().add(uiControlMSG);
			return;
		}


		/**
		 * @param channelNr
		 * @param scalFactor
		 * @param uiControlMSG
		 */
		private void setVerticalScale(int channelNr, double scalFactor, UIControlMSG uiControlMSG) {
			if (channelNr == OpenLabDevice.CH1) {
				uiControlMSG.setVerticalScaleCH1(scalFactor);
				System.out.println("Auto-Scale: Vertical scaling factor for CH1: " + scalFactor);
			} else if(channelNr == OpenLabDevice.CH2) {
				uiControlMSG.setVerticalScaleCH2(scalFactor);
				System.out.println("Auto-Scale: Vertical scaling factor for CH2: " + scalFactor);
			} else {
				return;
			}
		}


		/**
		 * @param freqData
		 * @param sampleData
		 * @return
		 */
		private int frequencyAnalysis(int channelNr, ArrayList<Double> freqData, ArrayList<Integer> sampleData) {
			OpenLabDevice openLabDevice = OpenLabDevice.getInstance();

			if(openLabDevice.isHWFreqSupported()) {				
				if(freqData.size() >= MAX_FREQ_ANALYSIS_SAMPLES) {

					ArrayList<Double> measuredVoltageLvl = new ArrayList<Double>(600);					

					if(channelNr == OpenLabDevice.CH1) {
						calculateMeasuredVirtualData(OpenLabDevice.CH1,ampStageMaxValueLUT(OpenLabDevice.CH1, ampStageCH1),
								sampleData,null,measuredVoltageLvl);

					} else if(channelNr == OpenLabDevice.CH2) {
						calculateMeasuredVirtualData(OpenLabDevice.CH2,ampStageMaxValueLUT(OpenLabDevice.CH2, ampStageCH2),
								sampleData,null,measuredVoltageLvl);

					} else {
						return -1;
					}

					//Remove pre-calculated frequency (int)
					measuredVoltageLvl.remove((measuredVoltageLvl.size()-1));

					//Verify if the signal has only DC components
					if(MeasurementProc.calculateVpp(measuredVoltageLvl) <= MeasurementProc.SIGNAL_DC_LIMIT) {
						sendSampleRateIncReq(true, false);
						freqData.clear();
						return FREQ_ANALYSIS_DONE;
					}					

					//Verify if the frequency values are stuck at a certain value
					if(isFreqValueInvalid(freqData)) {						
						if(sendSampleRateIncReq(false, false) == SAMPLE_RATE_LIMIT_REACHED) {
							return FREQ_ANALYSIS_DONE;
						}
						freqData.clear();
						return FREQ_ANALYSIS_PROC;
					}

					//Verify if the frequency values are all within a tolerable tolerance (10%)
					if(!isFreqValueTolerable(freqData)) {						
						if(sendSampleRateIncReq(false, false) != FREQ_ANALYSIS_HORZ_SCALE_DONE) {
							freqData.clear();
							return FREQ_ANALYSIS_PROC;
						}
					} 

					//Calculate the optimum scaling factor to illustrate the signal properly
					calcHorzScaling(freqData);

					if(sendSampleRateIncReq(false, true) != FREQ_ANALYSIS_HORZ_SCALE_DONE) {
						freqData.clear();						
						return FREQ_ANALYSIS_PROC;
					}

					freqData.clear();			
					return FREQ_ANALYSIS_DONE;
				}
				freqData.add(MeasurementProc.calculateFrequency(frequency));
				return FREQ_ANALYSIS_PROC;
			}
			return FREQ_ANALYSIS_UNSUPPORTED;
		}


		/**
		 * @param freqData
		 * @return
		 */
		private boolean isFreqValueTolerable(ArrayList<Double> freqData) {			
			double diff;
			double resDiff;

			//Compare all frequency values among each other to ensure
			//that all frequency values are within the given tolerance
			for(int i=0,j=0,index=0; index<freqData.size(); index++) {
				i=index;
				j=index+1;				

				for(; j<freqData.size(); i++,j++) {
					diff = Math.abs(freqData.get(i) - freqData.get(j));
					resDiff = diff / Math.max(freqData.get(i), freqData.get(j));

					if(resDiff >= MAX_FREQUENCY_TOLERANZ) {
						return false;
					}
				}				
			}

			return true;
		}


		/**
		 * @param freqData
		 */
		private void calcHorzScaling(ArrayList<Double> freqData) {
			double scaling;
			double meanFreq = MeasurementProc.calculateMean(freqData);

			//Determine the best scaling factor to illustrate at least two
			//periods of the signal within the signal graph
			while(this.horizScaleIndex < (horizScaleSteps.size()-1)) {
				scaling = meanFreq * this.horizScaleSteps.get(horizScaleIndex);

				if(scaling <= 0.5) {
					break;
				}
				this.horizScaleIndex++;
			}			
			return;
		}


		/**
		 * This method verifies if all elements in the ArrayList
		 * are equal. If so the horizontal scaling can not be performed 
		 * and the sampling rate has to be adjusted properly.
		 * @param The collected frequency data
		 * @return Returns false if all values in the ArrayList are equal.
		 * If the elements differ, true is returned to the caller.
		 */
		private boolean isFreqValueInvalid(ArrayList<Double> freqData) {
			for(int i=0,j=1; j<freqData.size(); i++,j++) {
				if(Double.compare(freqData.get(i), freqData.get(j)) != 0) {
					return false;
				}
			}
			return true;
		}


		/**
		 * @param setDefault
		 * @param isPropScaled
		 * @return
		 */
		private int sendSampleRateIncReq(boolean setDefault, boolean isPropScaled) {
			UIControlMSG uiControlMSG = new UIControlMSG();

			uiControlMSG.setMsgCode(MessageCode.UIControl);

			//The device has not been informed yet to update the sample rate accordingly.
			if(Double.compare(sampleRateSettings.getHorizontalScaling(), horizScaleSteps.get(this.horizScaleIndex)) != 0
					&& !isPropScaled) {				

				return FREQ_ANALYSIS_PROC;
			}

			if(setDefault) {
				uiControlMSG.setHorizontalScale(horizScaleSteps.get(horizScaleIndexInit));
				msq_Handler.getMsqProcessReq().add(uiControlMSG);
				System.out.println("Auto-Scale: Default Sample-Rate has been set!");

				return FREQ_ANALYSIS_HORZ_SCALE_DONE;
			}

			if(isPropScaled) {
				uiControlMSG.setHorizontalScale(horizScaleSteps.get(this.horizScaleIndex));
				msq_Handler.getMsqProcessReq().add(uiControlMSG);	
			} else {
				if(this.horizScaleIndex < (horizScaleSteps.size()-1)) {
					this.horizScaleIndex++;
					uiControlMSG.setHorizontalScale(horizScaleSteps.get(this.horizScaleIndex));
					msq_Handler.getMsqProcessReq().add(uiControlMSG);	

					System.out.println("Auto-Scale: Sample-Rate has been increased to: "
							+ horizScaleSteps.get(horizScaleIndex));
				} else {
					return SAMPLE_RATE_LIMIT_REACHED;
				}	
			}

			return FREQ_ANALYSIS_HORZ_SCALE_DONE;
		}


		/**
		 * 
		 */
		private void executeRunStopRequest() {
			if(!isRunStopModeInit) {
				initRunStopParam();
			}

			if(!cpyChCtrlCH1MSG.equals(getChCtrlCH1MSG()) || !cpySampleRateSettings.equals(getSampleRateSettings())) {
				cpyChCtrlCH1MSG = getChCtrlCH1MSG();

				if(getChCtrlCH1MSG().isChannelActive()) {
					setFrequency(frequencyValueCH1);
					sampleDataAnalysis(OpenLabDevice.CH1, cpySampleDataCH1, false, false, false);
				}
			}

			if(!cpyChCtrlCH2MSG.equals(getChCtrlCH2MSG()) || !cpySampleRateSettings.equals(getSampleRateSettings())) {
				cpyChCtrlCH2MSG = getChCtrlCH2MSG();				

				if(getChCtrlCH2MSG().isChannelActive()) {
					setFrequency(frequencyValueCH2);
					sampleDataAnalysis(OpenLabDevice.CH2, cpySampleDataCH2, false, false, false);
				}
			}

			if(!cpySampleRateSettings.equals(getSampleRateSettings())) {
				cpySampleRateSettings = getSampleRateSettings();
			}

			return;
		}


		/**
		 * 
		 */
		private void initRunStopParam() {
			cpyChCtrlCH1MSG = getChCtrlCH1MSG();
			cpyChCtrlCH2MSG = getChCtrlCH2MSG();				
			cpySampleRateSettings = getSampleRateSettings();

			prevSampleRate = getSampleRateSettings().getSampleRate();

			//Switch off channels if active - Due to, new sample
			//data are not required during the Run/Stop Mode
			if(getChCtrlCH1MSG().isChannelActive()) {
				setupChannelInputConfig(OpenLabDevice.CH1, false);
			}

			if(getChCtrlCH2MSG().isChannelActive()) {
				setupChannelInputConfig(OpenLabDevice.CH2, false);
			}

			isRunStopModeInit = true;
			System.out.println("Run/Stop Mode: Internal initialization is done!" +
					"\nRun/Stop Mode: Executing!");

			return;
		}


		/**
		 * This method is used to reset the analog channel states
		 * before the Run/Stop Mode state, due to all active channels were
		 * switched off without a User Interface update
		 */
		private void resetRunStopRequest() {
			if(getChCtrlCH1MSG().isChannelActive()) {
				setupChannelInputConfig(OpenLabDevice.CH1, true);				
			}

			if(getChCtrlCH2MSG().isChannelActive()) {
				setupChannelInputConfig(OpenLabDevice.CH2, true);				
			}

			isRunStopModeInit = false;
			System.out.println("Run/Stop Mode: Reseted initialization!");
			return;
		}


		/**
		 * @param channelNr
		 * @param sampleData
		 * @return
		 */
		private int executeSingleShotRequest(int channelNr, ArrayList<Integer> sampleData) {
			if (!isSingleShotInit) {
				if(!initSingleShotParam()) {
					System.out.println("Single-Shot: Failed to force amp-stages");
					return -1;
				}

				isSingleShotInit = true;
				System.out.println("Single-Shot: Forcing amp-stages done!");
				return SINGLE_SHOT_PROC;
			}

			int singleShotState = processSingleShotData(channelNr, sampleData);

			if (singleShotState == SINGLE_SHOT_DONE) {
				sendSingleShotDoneNotify();
				genericEvent = GenericEvent.RUN_STOP_PROC;	
			}
			return singleShotState;
		}


		/**
		 * @param channelNr
		 * @param sampleData
		 * @return
		 */
		private int processSingleShotData(int channelNr, ArrayList<Integer> sampleData) {
			if(getTriggerMSG().getTriggerSorce().equals("CH1")) {
				if(chCtrlCH1MSG.isChannelActive()) {
					if(!isSingleShotDoneCH1 && (channelNr == OpenLabDevice.CH1)) {
						if(sampleDataAnalysis(OpenLabDevice.CH1, sampleData, false, true, false) != SINGLE_SHOT_PROC_FAILED) {
							isSingleShotDoneCH1 = true;
						} else {
							sendSingleShotInitMSG(false);
							return SINGLE_SHOT_PROC;
						}
					}

					if(isSingleShotDoneCH1) {
						if(chCtrlCH2MSG.isChannelActive() && (channelNr == OpenLabDevice.CH2)) {
							sampleDataAnalysis(OpenLabDevice.CH2, sampleData, false, false, false);							

							isSingleShotDoneCH1 = false;
							return SINGLE_SHOT_DONE;
						} else if(!chCtrlCH2MSG.isChannelActive()){							
							isSingleShotDoneCH1 = false;
							return SINGLE_SHOT_DONE;
						} else {
							return SINGLE_SHOT_PROC;
						}	
					}
				}
			} else if(getTriggerMSG().getTriggerSorce().equals("CH2")) {
				if(chCtrlCH2MSG.isChannelActive()) {
					if(!isSingleShotDoneCH2 && (channelNr == OpenLabDevice.CH2)) {
						if(sampleDataAnalysis(OpenLabDevice.CH2, sampleData, false, true, false) != SINGLE_SHOT_PROC_FAILED) {
							isSingleShotDoneCH2 = true;
						} else {
							sendSingleShotInitMSG(false);
							return SINGLE_SHOT_PROC;
						}
					}

					if(isSingleShotDoneCH2) {
						if(chCtrlCH1MSG.isChannelActive() && (channelNr == OpenLabDevice.CH1)) {
							sampleDataAnalysis(OpenLabDevice.CH1, sampleData, false, false, false);							

							isSingleShotDoneCH2 = false;
							return SINGLE_SHOT_DONE;
						} else if(!chCtrlCH1MSG.isChannelActive()){							
							isSingleShotDoneCH2 = false;
							return SINGLE_SHOT_DONE;
						} else {
							return SINGLE_SHOT_PROC;
						}	
					}
				}
			} 
			return -1;
		}


		/**
		 * 
		 */
		private void resetSingleShotRequest() {
			isSingleShotInit = false;

			return;
		}


		/**
		 * 
		 */
		private void sendSingleShotDoneNotify() {
			UIControlMSG uiControlMSG = new UIControlMSG();

			uiControlMSG.setMsgCode(MessageCode.UIControl);
			uiControlMSG.setSingleShot(ProcState.DONE);
			uiControlMSG.setStatus("Single-Shot caught signal!");

			msq_Handler.getMsqProcessReq().add(uiControlMSG);

			System.out.println("Single-Shot: Signal was caught within the set tigger level!");
			return;
		}


		/**
		 * @return
		 */
		private boolean initSingleShotParam() {	
			int ampStage = 0;
			Map<Integer, Integer> ampStageSet = new HashMap<Integer, Integer>();

			sendSingleShotInitMSG(true);			

			if(getChCtrlCH1MSG().isChannelActive() && getChCtrlCH2MSG().isChannelActive()) {				
				ampStage = voltageLvlToAmpStage(OpenLabDevice.CH1, getChCtrlCH1MSG().getVerticalScale() * (ControlUtilities.Y_AXIS_DIVISIONS / 2));
				ampStageSet.put(OpenLabDevice.CH1, ampStage);

				ampStage = voltageLvlToAmpStage(OpenLabDevice.CH2, getChCtrlCH2MSG().getVerticalScale() * (ControlUtilities.Y_AXIS_DIVISIONS / 2));
				ampStageSet.put(OpenLabDevice.CH2, ampStage);
			} else if(getChCtrlCH1MSG().isChannelActive()) {
				ampStage = voltageLvlToAmpStage(OpenLabDevice.CH1, getChCtrlCH1MSG().getVerticalScale() * ControlUtilities.Y_AXIS_DIVISIONS);
				ampStageSet.put(OpenLabDevice.CH1, ampStage);
			} else if(getChCtrlCH2MSG().isChannelActive()) {
				ampStage = voltageLvlToAmpStage(OpenLabDevice.CH2, getChCtrlCH2MSG().getVerticalScale() * ControlUtilities.Y_AXIS_DIVISIONS);
				ampStageSet.put(OpenLabDevice.CH2, ampStage);
			} else {
				return false;
			}

			for(HashMap.Entry<Integer, Integer> entry : ampStageSet.entrySet()) {
				if(serialDevice.setInputConfiguration(DeviceType.OSCILLOSCOPE,
						entry.getKey(), entry.getValue(), CouplingType.COUPLING_TYPE_DC)) {

					if(entry.getKey() == OpenLabDevice.CH1) {
						setAmpStageCH1(entry.getValue());						
					} else if(entry.getKey() == OpenLabDevice.CH2) {
						setAmpStageCH2(entry.getValue());
					} else {
						continue;
					}

					System.out.println("Single-Shot: Forced amp-stage of CH" +
							entry.getKey() + " to stage: " + entry.getValue());
				} else {
					System.out.println("Single-Shot: Failed to force amp-stage of CH" +
							entry.getKey() + " to stage: " + entry.getValue());

					return false;
				}
			}
			return true;
		}


		/**
		 * 
		 */
		private void sendSingleShotInitMSG(boolean setStatus) {
			UIControlMSG uiControlMSG = new UIControlMSG();

			uiControlMSG.setMsgCode(MessageCode.UIControl);
			uiControlMSG.setSingleShot(ProcState.PROC);
			uiControlMSG.setClearSignalSeries(true);

			if(setStatus) {
				uiControlMSG.setStatus("Single-Shot: Executing!");
			}			

			msq_Handler.getMsqProcessReq().add(uiControlMSG);

			return;
		}


		/**
		 * @param channelNr
		 * @param voltageLvl
		 * @return
		 */
		private int voltageLvlToAmpStage(int channelNr, double voltageLvl)	{
			double probeAtt = getProbeAttenuation(channelNr);
			
			if(voltageLvl <= (AMP_STAGE0_MAX_VALUE * probeAtt)) {
				return AMP_STAGE0;
			} else if((voltageLvl > (AMP_STAGE0_MAX_VALUE * probeAtt)) && (voltageLvl <= (AMP_STAGE1_MAX_VALUE * probeAtt))) {
				return AMP_STAGE1;
			} else if((voltageLvl > (AMP_STAGE1_MAX_VALUE * probeAtt)) && (voltageLvl <= (AMP_STAGE2_MAX_VALUE * probeAtt))) {
				return AMP_STAGE2;
			} else if((voltageLvl > (AMP_STAGE2_MAX_VALUE * probeAtt)) && (voltageLvl <= (AMP_STAGE3_MAX_VALUE * probeAtt))) {
				return AMP_STAGE3;
			} else if((voltageLvl > (AMP_STAGE3_MAX_VALUE * probeAtt)) && (voltageLvl <= (AMP_STAGE4_MAX_VALUE * probeAtt))) {
				return AMP_STAGE4;
			} else if((voltageLvl > (AMP_STAGE4_MAX_VALUE * probeAtt)) && (voltageLvl <= (AMP_STAGE5_MAX_VALUE * probeAtt))) {
				return AMP_STAGE5;
			} else if(voltageLvl > (AMP_STAGE5_MAX_VALUE * probeAtt)) { 
				return AMP_STAGE6;
			} else {
				return -1;
			}
		}


		/**
		 * @return the prevSampleRate
		 */
		public double getPrevSampleRate() {
			return prevSampleRate;
		}


		/**
		 * @return the frequencyValueCH1
		 */
		public int getFrequencyValueCH1() {
			return frequencyValueCH1;
		}


		/**
		 * @param frequencyValueCH1 the frequencyValueCH1 to set
		 */
		public void setFrequencyValueCH1(int frequencyValueCH1) {
			this.frequencyValueCH1 = frequencyValueCH1;
		}


		/**
		 * @return the frequencyValueCH2
		 */
		public int getFrequencyValueCH2() {
			return frequencyValueCH2;
		}


		/**
		 * @param frequencyValueCH2 the frequencyValueCH2 to set
		 */
		public void setFrequencyValueCH2(int frequencyValueCH2) {
			this.frequencyValueCH2 = frequencyValueCH2;
		}
	}
}


/* EOF */