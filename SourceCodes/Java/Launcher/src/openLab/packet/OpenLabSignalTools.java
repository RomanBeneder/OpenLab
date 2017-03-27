/**
 * 
 */
package openLab.packet;

/**
 * @author Markus Lechner
 *
 */
public class OpenLabSignalTools {
	
	public enum SignalTool {
		OSCILLOSCOPE,
		SIGNALGENERATOR,
		MULTIMETER,
		CURRENT_VOLTAGE_SOURCE,
		LOGICANALYZER,
		ALL_SIGNAL_TOOLS;
	}
	
	public static final SignalTool[] arraySignalTools = new SignalTool[]{SignalTool.OSCILLOSCOPE,
			SignalTool.SIGNALGENERATOR,SignalTool.MULTIMETER,SignalTool.CURRENT_VOLTAGE_SOURCE,
			SignalTool.LOGICANALYZER};
}

/* EOF */