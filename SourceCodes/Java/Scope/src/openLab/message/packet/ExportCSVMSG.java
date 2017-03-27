/**
 * 
 */
package openLab.message.packet;

/**
 * @author Markus Lechner
 *
 */
public class ExportCSVMSG extends GenericMessage {
	private String fileName;
	private String filePath;
	private String langFormat;
	
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the langFormat
	 */
	public String getLangFormat() {
		return langFormat;
	}
	/**
	 * @param langFormat the langFormat to set
	 */
	public void setLangFormat(String langFormat) {
		this.langFormat = langFormat;
	}
}

/* EOF */