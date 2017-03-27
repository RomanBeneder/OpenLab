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
public enum CouplingType {

	COUPLING_TYPE_OFF(0), COUPLING_TYPE_DC(1), COUPLING_TYPE_AC(2);

	private int id;
	private static final Map<Integer, CouplingType> intToTypeMap = new HashMap<Integer, CouplingType>();

	static {
		for (CouplingType type : CouplingType.values()) {
			intToTypeMap.put(type.id, type);
		}
	}

	public static CouplingType fromInt(int i) {
		CouplingType type = intToTypeMap.get(Integer.valueOf(i));
		if (type == null)
			return CouplingType.COUPLING_TYPE_OFF;
		return type;
	}


	private CouplingType(int id) {
		this.id = id;
	}

	public int getValue() {
		return this.id;
	}
}
