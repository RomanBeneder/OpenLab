/**
 * 
 */
package openLab.message.packet;

/**
 * @author Markus Lechner
 *
 */
public class GenericEventMSG extends GenericMessage {
	private GenericEvent genericEvent;
	
	public enum GenericEvent {
		EXECUTION,
		SINGLE_SHOT_PROC,
		SINGLE_SHOT_ABRT,
		RUN_STOP_PROC,
		RUN_STOP_ABRT,
		AUTO_SCALE;
	}

	/**
	 * @return the genericEvent
	 */
	public GenericEvent getGenericEvent() {
		return genericEvent;
	}

	/**
	 * @param genericEvent the genericEvent to set
	 */
	public void setGenericEvent(GenericEvent genericEvent) {
		this.genericEvent = genericEvent;
	}
}

/* EOF */