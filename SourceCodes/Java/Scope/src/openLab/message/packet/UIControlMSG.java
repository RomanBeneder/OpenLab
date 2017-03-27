/**
 * 
 */
package openLab.message.packet;

/**
 * @author Markus Lechner
 *
 */
public class UIControlMSG extends GenericMessage {
	public final int UNINITIALIZED_INT = -1;
	public final double UNINITIALIZED_DOUB = -1.0;	

	private boolean clearSignalSeries = false;
	private boolean introduceFeedback = false;
	
	private int triggerSlope = UNINITIALIZED_INT;
	
	private double verticalScaleCH1 = UNINITIALIZED_DOUB;
	private double verticalPosCH1 = UNINITIALIZED_DOUB;
	
	private double verticalScaleCH2 = UNINITIALIZED_DOUB;
	private double verticalPosCH2 = UNINITIALIZED_DOUB;
	
	private double horizontalScale = UNINITIALIZED_DOUB;
	private double triggerValue = UNINITIALIZED_DOUB;
	
	private String triggerSource = null;
	private String status = null;
	
	private Components activityCH1 = Components.NONE;
	private Components activityCH2 = Components.NONE;
	private Components components = Components.NONE;
	
	private ProcState singleShot = ProcState.NONE;

	public enum Components {
		ENABLE,
		DISABLE,
		NONE;
	}
	
	public enum ProcState {
		PROC,
		DONE,
		NONE;
	}

	/**
	 * @return the clearSignalSeries
	 */
	public boolean isClearSignalSeries() {
		return clearSignalSeries;
	}

	/**
	 * @param clearSignalSeries the clearSignalSeries to set
	 */
	public void setClearSignalSeries(boolean clearSignalSeries) {
		this.clearSignalSeries = clearSignalSeries;
	}

	/**
	 * @return the introduceFeedback
	 */
	public boolean isIntroduceFeedback() {
		return introduceFeedback;
	}

	/**
	 * @param introduceFeedback the introduceFeedback to set
	 */
	public void setIntroduceFeedback(boolean introduceFeedback) {
		this.introduceFeedback = introduceFeedback;
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
	 * @return the verticalScaleCH1
	 */
	public double getVerticalScaleCH1() {
		return verticalScaleCH1;
	}

	/**
	 * @param verticalScaleCH1 the verticalScaleCH1 to set
	 */
	public void setVerticalScaleCH1(double verticalScaleCH1) {
		this.verticalScaleCH1 = verticalScaleCH1;
	}

	/**
	 * @return the verticalPosCH1
	 */
	public double getVerticalPosCH1() {
		return verticalPosCH1;
	}

	/**
	 * @param verticalPosCH1 the verticalPosCH1 to set
	 */
	public void setVerticalPosCH1(double verticalPosCH1) {
		this.verticalPosCH1 = verticalPosCH1;
	}

	/**
	 * @return the verticalScaleCH2
	 */
	public double getVerticalScaleCH2() {
		return verticalScaleCH2;
	}

	/**
	 * @param verticalScaleCH2 the verticalScaleCH2 to set
	 */
	public void setVerticalScaleCH2(double verticalScaleCH2) {
		this.verticalScaleCH2 = verticalScaleCH2;
	}

	/**
	 * @return the verticalPosCH2
	 */
	public double getVerticalPosCH2() {
		return verticalPosCH2;
	}

	/**
	 * @param verticalPosCH2 the verticalPosCH2 to set
	 */
	public void setVerticalPosCH2(double verticalPosCH2) {
		this.verticalPosCH2 = verticalPosCH2;
	}

	/**
	 * @return the horizontalScale
	 */
	public double getHorizontalScale() {
		return horizontalScale;
	}

	/**
	 * @param horizontalScale the horizontalScale to set
	 */
	public void setHorizontalScale(double horizontalScale) {
		this.horizontalScale = horizontalScale;
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
	 * @return the triggerSource
	 */
	public String getTriggerSource() {
		return triggerSource;
	}

	/**
	 * @param triggerSource the triggerSource to set
	 */
	public void setTriggerSource(String triggerSource) {
		this.triggerSource = triggerSource;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the activityCH1
	 */
	public Components getActivityCH1() {
		return activityCH1;
	}

	/**
	 * @param activityCH1 the activityCH1 to set
	 */
	public void setActivityCH1(Components activityCH1) {
		this.activityCH1 = activityCH1;
	}

	/**
	 * @return the activityCH2
	 */
	public Components getActivityCH2() {
		return activityCH2;
	}

	/**
	 * @param activityCH2 the activityCH2 to set
	 */
	public void setActivityCH2(Components activityCH2) {
		this.activityCH2 = activityCH2;
	}

	/**
	 * @return the components
	 */
	public Components getComponents() {
		return components;
	}

	/**
	 * @param components the components to set
	 */
	public void setComponents(Components components) {
		this.components = components;
	}

	/**
	 * @return the uNINITIALIZED_INT
	 */
	public int getUNINITIALIZED_INT() {
		return UNINITIALIZED_INT;
	}

	/**
	 * @return the uNINITIALIZED_DOUB
	 */
	public double getUNINITIALIZED_DOUB() {
		return UNINITIALIZED_DOUB;
	}

	/**
	 * @return the singleShot
	 */
	public ProcState getSingleShot() {
		return singleShot;
	}

	/**
	 * @param singleShot the singleShot to set
	 */
	public void setSingleShot(ProcState singleShot) {
		this.singleShot = singleShot;
	}
}

/* EOF */