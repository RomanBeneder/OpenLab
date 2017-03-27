package openLab.message.packet;

/**
 * @author Markus Lechner
 *
 */
public class ErrorType {
	private String errorType;
	
	
	public enum Snapshot{
		INVALID_SNP_COLOR;
	}
	
	public enum Severity{
		Low,
		Moderate,
		Major,
		Critical;
	}

	/**
	 * @return the errorType
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * @param errorType the errorType to set
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}	
}

/* EOF */