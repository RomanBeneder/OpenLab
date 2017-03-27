/**
 *
 */
package openLab.device;

/**
 * @author Markus Lechner
 *
 */
public enum TriggerMode {
	AUTO(0),
	NORMAL(1),
	SINGLE(2);

	private final int triggerMode;
	
	TriggerMode(int triggerMode) {
		this.triggerMode = triggerMode;		
	}
	
	
	public int getValue() {
		return this.triggerMode;
	}
}

/* EOF */
