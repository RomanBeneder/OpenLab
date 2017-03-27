/**
 * 
 */
package openLab.device;

/**
 * @author harald
 *
 */
public class DeviceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1393886059882351169L;

	/**
	 * 
	 */
	public DeviceException() {
	}

	/**
	 * @param message
	 */
	public DeviceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DeviceException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DeviceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DeviceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
