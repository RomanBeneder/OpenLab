/**
 * 
 */
package openLab.device;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Harald Schloffer
 *
 */
public enum ErrorCode {
	/**
	 * Operation successful, no error detected
	 */
	OK(0),
	/**
	 * General warning. Command may result in unexpected outcome.
	 */
	WARNING(1),
	/**
	 * Command is not supported
	 */
	NOT_SUPPORTED(2),
	/**
	 * The command requested is not known by the receiver
	 */
	UNKNOWN_COMMAND(3),
	/**
	 * Operation failed but exact reason is not available
	 */
	FAILED(4),
	/**
	 * Operation failed because of a memory overflow on the OpenLab device
	 */
	OVERFLOW(5);
	private final int code;
	private static final Map<Integer, ErrorCode> intToTypeMap = new HashMap<Integer, ErrorCode>();

	static {
		for (ErrorCode type : ErrorCode.values()) {
			intToTypeMap.put(type.code, type);
		}
	}

	public static ErrorCode fromInt(int i) {
		ErrorCode type = intToTypeMap.get(Integer.valueOf(i));
		if (type == null)
			return ErrorCode.FAILED;
		return type;
	}

	/**
	 * 
	 * @param code
	 * @param string
	 */
	private ErrorCode(final int code) {
		this.code = code;
	}

	/**
	 * 
	 * @return
	 */
	public int getValue() {
		return this.code;
	}
}
