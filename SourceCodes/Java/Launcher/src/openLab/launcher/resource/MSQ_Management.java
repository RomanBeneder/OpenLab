/**
 *
 */
package openLab.launcher.resource;

import java.util.concurrent.ConcurrentLinkedQueue;

import openLab.packet.GenericMessage;

/**
 * @author Markus Lechner
 *
 */
public class MSQ_Management {
	private static MSQ_Management msqManagement = null;

	private ConcurrentLinkedQueue<GenericMessage> msqProcessSupervisor = new ConcurrentLinkedQueue<GenericMessage>();
	private ConcurrentLinkedQueue<GenericMessage> msqControllerUI = new ConcurrentLinkedQueue<GenericMessage>();

	/**
	 * @return
	 */
	public static MSQ_Management getInstance() {
		if(msqManagement == null) {
			synchronized(MSQ_Management.class)	{
				msqManagement = new MSQ_Management();
			}
		}
		return msqManagement;
	}



	/**
	 * @return the msqProcessSupervisor
	 */
	public ConcurrentLinkedQueue<GenericMessage> getMsqProcessSupervisor() {
		return msqProcessSupervisor;
	}
	/**
	 * @param msqProcessSupervisor the msqProcessSupervisor to set
	 */
	public void setMsqProcessSupervisor(ConcurrentLinkedQueue<GenericMessage> msqProcessSupervisor) {
		this.msqProcessSupervisor = msqProcessSupervisor;
	}
	/**
	 * @return the msqControllerUI
	 */
	public ConcurrentLinkedQueue<GenericMessage> getMsqControllerUI() {
		return msqControllerUI;
	}
	/**
	 * @param msqControllerUI the msqControllerUI to set
	 */
	public void setMsqControllerUI(ConcurrentLinkedQueue<GenericMessage> msqControllerUI) {
		this.msqControllerUI = msqControllerUI;
	}
}

/* EOF */