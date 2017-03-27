/**
 * 
 */
package openLab.message.packet;

import openLab.device.TriggerMode;

/**
 * @author Markus Lechner
 *
 */
public class TriggerMSG extends GenericMessage {	
	private boolean isSignalInverted;
	private int triggerSlope;
	private long holdOff;
	private double triggerValue;	
	private String triggerSorce = new String();
	private TriggerMode triggerMode = TriggerMode.AUTO;
	
	/**
	 * @return the isSignalInverted
	 */
	public boolean isSignalInverted() {
		return isSignalInverted;
	}
	/**
	 * @param isSignalInverted the isSignalInverted to set
	 */
	public void setSignalInverted(boolean isSignalInverted) {
		this.isSignalInverted = isSignalInverted;
	}
	/**
	 * @return the triggerSlope
	 */
	public int getTriggerSlope() {
		return triggerSlope;
	}
	/**
	 * @param triggerSlope the triggerSlope to set
	 */
	public void setTriggerSlope(int triggerSlope) {
		this.triggerSlope = triggerSlope;
	}
	/**
	 * @return the holdOff
	 */
	public long getHoldOff() {
		return holdOff;
	}
	/**
	 * @param holdOff the holdOff to set
	 */
	public void setHoldOff(long holdOff) {
		this.holdOff = holdOff;
	}
	/**
	 * @return the triggerValue
	 */
	public double getTriggerValue() {
		return triggerValue;
	}
	/**
	 * @param triggerValue the triggerValue to set
	 */
	public void setTriggerValue(double triggerValue) {
		this.triggerValue = triggerValue;
	}
	/**
	 * @return the triggerSorce
	 */
	public String getTriggerSorce() {
		return triggerSorce;
	}
	/**
	 * @param triggerSorce the triggerSorce to set
	 */
	public void setTriggerSorce(String triggerSorce) {
		this.triggerSorce = triggerSorce;
	}
	/**
	 * @return the triggerMode
	 */
	public TriggerMode getTriggerMode() {
		return triggerMode;
	}
	/**
	 * @param triggerMode the triggerMode to set
	 */
	public void setTriggerMode(TriggerMode triggerMode) {
		this.triggerMode = triggerMode;
	}	
}

/* EOF */
