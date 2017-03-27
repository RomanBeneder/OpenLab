package openLab.message.packet;

import java.util.ArrayList;

/**
 * @author Markus Lechner
 *
 */
public class DeviceMSG extends GenericMessage  {
	private String selectedPort = null;
	
	private ArrayList<String> portList = null;

	/**
	 * @return the selectedPort
	 */
	public String getSelectedPort() {
		return selectedPort;
	}

	/**
	 * @param selectedPort the selectedPort to set
	 */
	public void setSelectedPort(String selectedPort) {
		this.selectedPort = selectedPort;
	}

	/**
	 * @return the portList
	 */
	public ArrayList<String> getPortList() {
		return portList;
	}

	/**
	 * @param portList the portList to set
	 */
	public void setPortList(ArrayList<String> portList) {
		this.portList = portList;
	}

}

/* EOF */