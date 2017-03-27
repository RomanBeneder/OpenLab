/**
 * 
 */
package openLab.message.packet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Markus Lechner
 *
 */
public class MeasurementMSG extends GenericMessage {	
	
	private boolean indNoSignal = false; 
	private int channelNr;		
	
	private Map<Measurement, String> measurement = new HashMap<Measurement, String>(5);

	/**
	 * @return the channelNr
	 */
	public int getChannelNr() {
		return channelNr;
	}

	/**
	 * @return the indNoSignal
	 */
	public boolean isIndNoSignal() {
		return indNoSignal;
	}

	/**
	 * @param indNoSignal the indNoSignal to set
	 */
	public void setIndNoSignal(boolean indNoSignal) {
		this.indNoSignal = indNoSignal;
	}

	/**
	 * @param channelNr the channelNr to set
	 */
	public void setChannelNr(int channelNr) {
		this.channelNr = channelNr;
	}

	/**
	 * @return the measurement
	 */
	public Map<Measurement, String> getMeasurement() {
		return measurement;
	}

	/**
	 * @param measurement the measurement to set
	 */
	public void setMeasurement(Map<Measurement, String> measurement) {
		this.measurement = measurement;
	}	
}

/* EOF */