/**
 * 
 */
package openLab.message.packet;

import javafx.scene.image.WritableImage;

/**
 * @author Markus Lechner
 *
 */
public class SnapshotMSG extends GenericMessage {
	private String fileName;
	private String filePath;
	private String fileFormat;
	private String color;	
	private WritableImage image;
	
	
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
	 * @return the fileFormat
	 */
	public String getFileFormat() {
		return fileFormat;
	}
	/**
	 * @param fileFormat the fileFormat to set
	 */
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the image
	 */
	public WritableImage getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(WritableImage image) {
		this.image = image;
	}

}

/* EOF */
