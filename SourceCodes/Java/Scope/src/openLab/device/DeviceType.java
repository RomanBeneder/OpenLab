/**
 *
 */
package openLab.device;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Harald
 *
 */
public enum DeviceType {
	/**
	 * Invalid command.
	 */
	INVALID(-1),
	/**
	 *
	 */
	DEVICE(0),
	/**
	 *
	 */
	OSCILLOSCOPE(1),
	/**
	 *
	 */
	SIGNALGENERATOR(2);

	private final int code;
	private static final Map<Integer, DeviceType> intToTypeMap = new HashMap<Integer, DeviceType>();

	static {
		for (DeviceType type : DeviceType.values()) {
			intToTypeMap.put(type.code, type);
		}
	}

	public static DeviceType fromInt(int i) {
		DeviceType type = intToTypeMap.get(Integer.valueOf(i));
		if (type == null)
			return DeviceType.INVALID;
		return type;
	}

	/**
	 *
	 * @param code
	 * @param string
	 */
	private DeviceType(final int code) {
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
