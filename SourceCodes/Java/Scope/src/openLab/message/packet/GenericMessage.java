/**
 * 
 */
package openLab.message.packet;

/**
 * @author Markus Lechner
 *
 */
public abstract class GenericMessage {
	private MessageCode msgCode = MessageCode.INVALID;
	private ErrorCode errorCode = ErrorCode.NONE;
	
	
	/**
	 * @return the msgCode
	 */
	public MessageCode getMsgCode() {
		return msgCode;
	}
	/**
	 * @param msgCode the msgCode to set
	 */
	public void setMsgCode(MessageCode msgCode) {
		this.msgCode = msgCode;
	}
	/**
	 * @return the errorCode
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}

/* EOF */