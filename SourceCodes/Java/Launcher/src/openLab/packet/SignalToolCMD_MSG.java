/**
 * 
 */
package openLab.packet;

import openLab.packet.OpenLabSignalTools.SignalTool;

/**
 * @author Markus Lechner
 *
 */
public class SignalToolCMD_MSG extends GenericMessage {
	private SignalTool signalTool = null;
	private SignalToolCMD signalToolCommand = null;

	String status = null;


	/**
	 * @return the signalTool
	 */
	public SignalTool getSignalTool() {
		return signalTool;
	}
	/**
	 * @param signalTool the signalTool to set
	 */
	public void setSignalTool(SignalTool signalTool) {
		this.signalTool = signalTool;
	}
	/**
	 * @return the signalToolCommand
	 */
	public SignalToolCMD getSignalToolCommand() {
		return signalToolCommand;
	}
	/**
	 * @param signalToolCommand the signalToolCommand to set
	 */
	public void setSignalToolCommand(SignalToolCMD signalToolCommand) {
		this.signalToolCommand = signalToolCommand;
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
}

/* EOF */