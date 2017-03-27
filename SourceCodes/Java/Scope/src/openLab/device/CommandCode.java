package openLab.device;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Harald Schloffer
 *
 */
public enum CommandCode {
	/**
	 * Invalid command.
	 */
	INVALID_COMMAND(-1, "IVC"),
	/**
	 *
	 */
	SET_TRIGGER_SETTINGS(1, "STS"),
	/**
	 *
	 */
	SET_CHANNEL_SETTINGS(2, "SCS"),
	/**
	 *
	 */
	SET_TIME_BASE(3, "STB"),
	/**
	 *
	 */
	GET_HARDWARE_VERSION(4, "GHWV"),
	/**
	 *
	 */
	GET_SOFTWARE_VERSION(5, "GSWV"),
	/**
	 *
	 */
	SET_LOOP_BACK(6, "SLB"),
	/**
	 *
	 */
	SET_SAMPLING_MODE(7, "SSM"),
	/**
	 *
	 */
	SAMPLE_DATA(8, "SD"),
	/**
	 *
	 */
	HARDWARE_VERSION(9, "HWV"),
	/**
	 *
	 */
	SOFTWARE_VERSION(10, "SWV"),
	/**
	 *
	 */
	EXTENDED_SAMPLE_DATA(11, "ESD"),
	/**
	 *
	 */
	NOT_ACKNOWLEDGED(126, "NACK"),
	/**
	 *
	 */
	ACKNOWLEDGE(127, "ACK");

	private final int code;
	private final String string;
	private static final Map<Integer, CommandCode> intToTypeMap = new HashMap<Integer, CommandCode>();
	private static final Map<String, CommandCode> strToTypeMap = new HashMap<String, CommandCode>();

	static {
		for (CommandCode type : CommandCode.values()) {
			intToTypeMap.put(type.code, type);
		}
	}

	static {
		for (CommandCode type : CommandCode.values()) {
			strToTypeMap.put(type.string, type);
		}
	}

	/**
	 * Get the command code object that corresponds to the provided number.
	 *
	 * @param i
	 *            Command number
	 * @return {@link CommandCode} object. Check if the returned command code is
	 *         valid.
	 */
	public static CommandCode fromInt(int i) {
		CommandCode type = intToTypeMap.get(Integer.valueOf(i));
		if (type == null)
			return CommandCode.INVALID_COMMAND;
		return type;
	}

	/**
	 * Get the command code object that corresponds to the provided string.
	 *
	 * @param s
	 *            Command code expressed as string
	 * @return {@link CommandCode} object. Check if the returned command code is
	 *         valid.
	 */
	public static CommandCode fromString(String s) {
		CommandCode type = strToTypeMap.get(s);
		if (type == null)
			return CommandCode.INVALID_COMMAND;
		return type;
	}

	/**
	 *
	 * @param code
	 * @param string
	 */
	private CommandCode(final int code, final String string) {
		this.code = code;
		this.string = string;
	}

	/**
	 *
	 * @return
	 */
	public int getValue() {
		return this.code;
	}

	/**
	 *
	 * @return
	 */
	public String getString() {
		return this.string;
	}
}
