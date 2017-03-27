/**
 * 
 */
package openLab.device;

import java.util.HashMap;
import java.util.Map;

/**
 * @author harald
 *
 */
public enum TriggerType {
	RISING_EDGE(0),
	FALLING_EDGE(1),
	BOTH(2),
	DISABLE(3);

	private final int id;
	private static final Map<Integer, TriggerType> intToTypeMap = new HashMap<Integer, TriggerType>();

	static {
		for (TriggerType type : TriggerType.values()) {
			intToTypeMap.put(type.id, type);
		}
	}

	public static TriggerType fromInt(int i) {
		TriggerType type = intToTypeMap.get(Integer.valueOf(i));
		if (type == null)
			return TriggerType.DISABLE;
		return type;
	}

	private TriggerType(int id) {
		this.id = id;
	}

	public int getValue() {
		return this.id;
	}
}

/* EOF */