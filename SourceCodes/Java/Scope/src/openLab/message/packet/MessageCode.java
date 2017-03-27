/**
 * 
 */
package openLab.message.packet;

/**
 * @author Markus Lechner
 *
 */
public enum MessageCode {
	INVALID,
	TRIGGER,
	MEASUREMENT,
	MEASUREMENT_RESP,
	CHANNEL_CTRL,
	SAMPLE_RATE,
	SNAPSHOT,
	UIControl,
	ERROR_MSG,
	GENERIC_EVENT,
	EXPORT_CSV,
	DEVICE_INTER_REQ,
	DEVICE_INTER_UPD;
}

/* EOF */
