/**
 * 
 */
package openLab.messageX;

/**
 * @author Markus Lechner
 *
 */
public class SerialInterfaceMSG {
	public final static String dialogTitleSerialError = new String("Serial Interface Connection Problem");
	public final static String dialogTextSerialError = new String("The configured Port has been disconnected!");

	public final static String dialogTitleNoSerialPortSelected = new String("Serial Interface Connection Problem");
	public final static String dialogTextNoSerialPortSelected = new String("No serial port has been found!\nPlease"
			+ " try again to reconnect your device.");

	public final static String dialogTitleNoInvalidDevice = new String("Invalid Device");
	public final static String dialogTextInvalidDevice = new String("The connected device is"
			+ " not an OpenLab device!\nThe corresponding device has been disconected.");

	public final static String dialogTitlePortInUse = new String("Error: Port In Use");
	public final static String dialogTextPortInUse = new String("The corresponding Port is currently"
			+ " in use!\nPlease unplug the device and perform\na reconnect manually!");

	public final static String dialogTitleInvHW = new String("Warning: Unsupported Hardware!");
	public final static String dialogTextInvHW1 = new String("The connected hardware might not work as expected!\n");
	public final static String dialogTextInvHW2 = new String("\nRequired hardware verison: V");
	public final static String dialogTextInvHW3 = new String("\nConnected hardware version: V");
}

/* EOF */