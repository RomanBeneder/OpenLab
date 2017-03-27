/**
 * 
 */
package openLab.message.packet;

import openLab.device.SamplingMode;

/**
 * @author Markus Lechner
 *
 */
public class SampleRateMSG extends GenericMessage{
	private double sampleRate;
	private double horizontalScaling;
	private SamplingMode sampleMode = SamplingMode.INITIAL;
	/**
	 * @return the sampleRate
	 */
	public double getSampleRate() {
		return sampleRate;
	}
	/**
	 * @param sampleRate the sampleRate to set
	 */
	public void setSampleRate(double sampleRate) {
		this.sampleRate = sampleRate;
	}
	/**
	 * @return the horizontalScaling
	 */
	public double getHorizontalScaling() {
		return horizontalScaling;
	}
	/**
	 * @param horizontalScaling the horizontalScaling to set
	 */
	public void setHorizontalScaling(double horizontalScaling) {
		this.horizontalScaling = horizontalScaling;
	}
	/**
	 * @return the sampleMode
	 */
	public SamplingMode getSampleMode() {
		return sampleMode;
	}
	/**
	 * @param sampleMode the sampleMode to set
	 */
	public void setSampleMode(SamplingMode sampleMode) {
		this.sampleMode = sampleMode;
	}

}

/* EOF */