/**
 *
 */
package openLab.device;

import java.util.HashMap;
import java.util.Map;

/**
 * @author OpenLab
 *
 */
public enum SamplingMode {
	REALTIME_SAMPLING(0), SEQUENTIAL_ETS(1), RANDOM_ETS(2), INITIAL(3);

	private final int id;
	private static final Map<Integer, SamplingMode> intToTypeMap = new HashMap<Integer, SamplingMode>();

	static {
		for (SamplingMode mode : SamplingMode.values()) {
			intToTypeMap.put(mode.id, mode);
		}
	}

	public static SamplingMode fromInt(int i) {
		SamplingMode mode = intToTypeMap.get(Integer.valueOf(i));
		if (mode == null)
			return SamplingMode.REALTIME_SAMPLING;
		return mode;
	}

	private SamplingMode(int id) {
		this.id = id;
	}

	public int getValue() {
		return this.id;
	}
}
