package openLab.message.packet;

import openLab.message.packet.ErrorType.Severity;

/**
 * @author Markus Lechner
 *
 */
public class ErrorMSG extends GenericMessage {
	private ErrorType errorType;
	private String description;
	private String timestamp;
	private Severity severity;
	private Exception exception;
	
	/**
	 * @return the errorType
	 */
	public ErrorType getErrorType() {
		return errorType;
	}
	/**
	 * @param errorType the errorType to set
	 */
	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the severity
	 */
	public Severity getSeverity() {
		return severity;
	}
	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
	/**
	 * @return the exception
	 */
	public Exception getException() {
		return exception;
	}
	/**
	 * @param exception the exception to set
	 */
	public void setException(Exception exception) {
		this.exception = exception;
	}
}

/* EOF */