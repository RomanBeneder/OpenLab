/**
 * 
 */
package openLab.message.packet;

/**
 * @author Markus Lechner
 *
 */
public class ChannelControlMSG extends GenericMessage {
	private boolean isChannelActive = false;
	private boolean isSignalInverted;
	private int channelNr;
	private double verticalScale;
	private double verticalPos;
	private double probeAtten = 10.0; //Default: 10:1
	
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
	 * @return the verticalScale
	 */
	public double getVerticalScale() {
		return verticalScale;
	}
	/**
	 * @param verticalScale the voltDiv to set
	 */
	public void setVerticalScale(double verticalScale) {
		this.verticalScale = verticalScale;
	}
	/**
	 * @return the verticalPos
	 */
	public double getVerticalPos() {
		return verticalPos;
	}
	/**
	 * @param verticalPos the verticalPos to set
	 */
	public void setVerticalPos(double verticalPos) {
		this.verticalPos = verticalPos;
	}
	/**
	 * @return the channelNr
	 */
	public int getChannelNr() {
		return channelNr;
	}
	/**
	 * @param channelNr the channelNr to set
	 */
	public void setChannelNr(int channelNr) {
		this.channelNr = channelNr;
	}
	/**
	 * @return the isChannelActive
	 */
	public boolean isChannelActive() {
		return isChannelActive;
	}
	/**
	 * @param isChannelActive the isChannelActive to set
	 */
	public void setChannelActive(boolean isChannelActive) {
		this.isChannelActive = isChannelActive;
	}
	/**
	 * @return the probeAtten
	 */
	public double getProbeAtten() {
		return probeAtten;
	}
	/**
	 * @param probeAtten the probeAtten to set
	 */
	public void setProbeAtten(double probeAtten) {
		this.probeAtten = probeAtten;
	}
}

/* EOF */