/**
 * 
 */
package openLab.processing;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import openLab.message.packet.Measurement;
import openLab.message.packet.MeasurementMSG;
import openLab.message.packet.MessageCode;
import openLab.resource.MSQ_Handler;
import openLab.resource.OpenLabDevice;

/**
 * @author Markus Lechner
 *
 */
public class MeasurementProc implements Runnable {	
	private final static int INITIAL_FREQ_CTS = 5;

	public final static double SIGNAL_DC_LIMIT = 200.0e-3;
	public final static double NO_FREQ_AVERAGING = -1.0;

	private final String INVALID_FREQ = "***.**Hz";

	private static int MAX_FREQ_MEASURING = INITIAL_FREQ_CTS;

	private double prevFreqCH1 = 0.0;
	private double prevFreqCH2 = 0.0;

	private Thread measurementProcessingThread = null;		

	private ConcurrentLinkedQueue<MeasurementMSG> clqMeasReq = new ConcurrentLinkedQueue<MeasurementMSG>();
	private ConcurrentLinkedQueue<ArrayList<Double>> clqMeasCH1 = new ConcurrentLinkedQueue<ArrayList<Double>>();
	private ConcurrentLinkedQueue<ArrayList<Double>> clqMeasCH2 = new ConcurrentLinkedQueue<ArrayList<Double>>();

	private Map<Measurement, String> measurementCH1 = new HashMap<Measurement, String>(INITIAL_FREQ_CTS);
	private Map<Measurement, String> measurementCH2 = new HashMap<Measurement, String>(INITIAL_FREQ_CTS);

	private MSQ_Handler msq_Handler = null;

	private DecimalFormat  dFormat2dp = new DecimalFormat("#.00");
	private DecimalFormat  dFormatmV = new DecimalFormat("#");

	private ArrayList<Double> frequencyCH1 = new ArrayList<Double>(MAX_FREQ_MEASURING);
	private ArrayList<Double> frequencyCH2 = new ArrayList<Double>(MAX_FREQ_MEASURING);


	/**
	 * 
	 */
	public MeasurementProc() {
		initMeasurementProcessing();
	}


	private void initMeasurementProcessing() {
		measurementProcessingThread = new Thread(this);
		measurementProcessingThread.setName("Measurement.Processing");
		measurementProcessingThread.setDaemon(true);
		measurementProcessingThread.start();
		return;
	}


	@Override
	public void run() {
		MeasurementMSG measurementMSG = new MeasurementMSG();
		msq_Handler = MSQ_Handler.getInstance();

		while(true) {
			if(!clqMeasReq.isEmpty()) {
				measurementMSG = clqMeasReq.remove();

				if(measurementMSG.getChannelNr() == OpenLabDevice.CH1) {
					measurementCH1.clear();
					measurementCH1.putAll(measurementMSG.getMeasurement());					
				} else if(measurementMSG.getChannelNr() == OpenLabDevice.CH2) {
					measurementCH2.clear();
					measurementCH2.putAll(measurementMSG.getMeasurement());	
				} else {
					System.err.println("Error: Invalid Channel Number entered!");
					continue;
				}				
			}

			if(!clqMeasCH1.isEmpty() || !clqMeasCH2.isEmpty()) {
				if(!clqMeasCH1.isEmpty()) {
					MeasurementMSG measurementCH1MSG = new MeasurementMSG();

					measurementCH1MSG.setMsgCode(MessageCode.MEASUREMENT_RESP);
					measurementCH1MSG.setChannelNr(OpenLabDevice.CH1);		

					processReq(OpenLabDevice.CH1, measurementCH1MSG, clqMeasCH1.remove());							
					msq_Handler.getMsqProcessReq().add(measurementCH1MSG);

					if(clqMeasCH1.size() >= 2) {
						clqMeasCH1.clear();
					}					
				}

				if(!clqMeasCH2.isEmpty()) {		
					MeasurementMSG measurementCH2MSG = new MeasurementMSG();

					measurementCH2MSG.setMsgCode(MessageCode.MEASUREMENT_RESP);					
					measurementCH2MSG.setChannelNr(OpenLabDevice.CH2);	

					processReq(OpenLabDevice.CH2, measurementCH2MSG, clqMeasCH2.remove());	
					msq_Handler.getMsqProcessReq().add(measurementCH2MSG);

					if(clqMeasCH2.size() >= 2) {
						clqMeasCH2.clear();
					}
				}
				setThreadInSleepMode(50);
			} else {
				setThreadInSleepMode(20);
			}			
		}
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


	private void processReq(int channelNr, MeasurementMSG measurementMSG, ArrayList<Double> calcData) {
		double frequency = 0.0;

		OpenLabDevice openLabDevice = OpenLabDevice.getInstance();

		if(openLabDevice.isHWFreqSupported()) {
			frequency = calcData.remove((calcData.size() - 1));			
			
			if(Double.compare(frequency, NO_FREQ_AVERAGING) == 0) {
				MAX_FREQ_MEASURING = 1;
				frequencyCH1.clear();
				frequencyCH2.clear();
				frequency = calcData.remove((calcData.size() - 1));				
			} else {
				MAX_FREQ_MEASURING = INITIAL_FREQ_CTS;
			}
		}

		for(Map.Entry<Measurement, String> entry : measurementCH1.entrySet()) {			
			switch(entry.getKey()) {
			case Vpp:
				measurementMSG.getMeasurement().put(Measurement.Vpp, formatVoltageValue(calculateVpp(calcData)));				
				break;

			case Vmin:
				double vmin = Collections.min(calcData);				
				measurementMSG.getMeasurement().put(Measurement.Vmin, formatVoltageValue(vmin));	
				break;

			case Vmax:
				double vmax = Collections.max(calcData);				
				measurementMSG.getMeasurement().put(Measurement.Vmax, formatVoltageValue(vmax));	
				break;

			case Freq:				
				//Verify if the signal is DC
				if(calculateVpp(calcData) <= SIGNAL_DC_LIMIT) {
					measurementMSG.getMeasurement().put(Measurement.Freq, INVALID_FREQ);
					break;
				}

				double freqRes = calculateFrequency((int)frequency);

				if(channelNr == OpenLabDevice.CH1) {
					frequencyCH1.add(freqRes);
					
					if(frequencyCH1.size() >= MAX_FREQ_MEASURING) {
						if(freqPlausibilityCheck(frequencyCH1)) {
							prevFreqCH1 =  calculateMean(frequencyCH1);
							measurementMSG.getMeasurement().put(Measurement.Freq, formatFrequencyValue(prevFreqCH1));
						} else {
							measurementMSG.getMeasurement().put(Measurement.Freq, INVALID_FREQ);
							frequencyCH1.clear();
							break;
						}
						frequencyCH1.clear();
					} else {
						measurementMSG.getMeasurement().put(Measurement.Freq, formatFrequencyValue(prevFreqCH1));
					}
				} else if(channelNr == OpenLabDevice.CH2) {
					frequencyCH2.add(freqRes);

					if(frequencyCH2.size() >= MAX_FREQ_MEASURING) {
						if(freqPlausibilityCheck(frequencyCH2)) {
							prevFreqCH2 =  calculateMean(frequencyCH2);
							measurementMSG.getMeasurement().put(Measurement.Freq, formatFrequencyValue(prevFreqCH2));
						} else {
							measurementMSG.getMeasurement().put(Measurement.Freq, INVALID_FREQ);
							frequencyCH2.clear();
							break;
						}
						frequencyCH2.clear();
					} else {
						measurementMSG.getMeasurement().put(Measurement.Freq, formatFrequencyValue(prevFreqCH2));
					}
				} else {
					break;
				}			

				break;

			case Period:

				break;

			default:
				break;
			}
		}

		return;
	}

	/**
	 * @param frequency
	 * @return
	 */
	public static double calculateFrequency(int frequency) {
		return ((1.0 / (frequency * (1.0 / 120.0e6))) * 2.0);
	}


	private boolean freqPlausibilityCheck(ArrayList<Double> freqData) {		
		for(int i=0; i<freqData.size(); i++) {
			if(freqData.get(i) <= 0.0 || freqData.get(i).isInfinite() || freqData.get(i).isNaN()) {
				return false;
			}
		}
		return true;
	}


	/**
	 * @param meanFreq
	 * @return
	 */
	public static double calculateMean(ArrayList<Double> meanFreq) {
		double mean = 0.0;

		for (Double d: meanFreq) {
			mean += d;
		}

		return (mean / MAX_FREQ_MEASURING);	
	}


	/**
	 * @param calcData
	 * @return
	 */
	public static double calculateVpp(ArrayList<Double> calcData) {
		double vmin;
		double vmax;

		vmin = Collections.min(calcData);
		vmax = Collections.max(calcData);

		return (Math.abs(vmax - vmin));
	}


	/**
	 * @param vMeasure
	 * @return
	 */
	private String formatVoltageValue(double vMeasure) {
		if(Math.abs(vMeasure) < 1.00) {
			vMeasure *= 1000.00;
			return String.valueOf(dFormatmV.format(vMeasure) + "mV");
		}
		return (String.valueOf(dFormat2dp.format(vMeasure)) + "V");
	}


	/**
	 * @param fMeasure
	 * @return
	 */
	private String formatFrequencyValue(double fMeasure) {	
		
		fMeasure = Math.round(fMeasure * 100.0) / 100.0;
		
		if((fMeasure >= 1.0e3) && (fMeasure< 1.0e6)) {
			return String.valueOf(dFormat2dp.format((fMeasure / 1.0e3)) + "kHz");
		} else if(fMeasure >= 1.0e6) {
			return String.valueOf(dFormat2dp.format((fMeasure / 1.0e6)) + "MHz");
		} else {
			return String.valueOf(dFormat2dp.format(fMeasure) + "Hz");
		}
	}


	/**
	 * @return the clqMeasReq
	 */
	public ConcurrentLinkedQueue<MeasurementMSG> getClqMeasReq() {
		return clqMeasReq;
	}

	/**
	 * @return the clqMeasCH1
	 */
	public ConcurrentLinkedQueue<ArrayList<Double>> getClqMeasCH1() {
		return clqMeasCH1;
	}


	/**
	 * @return the clqMeasCH2
	 */
	public ConcurrentLinkedQueue<ArrayList<Double>> getClqMeasCH2() {
		return clqMeasCH2;
	}
}

/* EOF */