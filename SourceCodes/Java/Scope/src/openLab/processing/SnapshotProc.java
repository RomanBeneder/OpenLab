/**
 * 
 */
package openLab.processing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import FileUtility.FileUtil;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import openLab.message.packet.ErrorType;
import openLab.message.packet.ErrorType.Snapshot;

/**
 * @author Markus Lechner
 *
 */
public class SnapshotProc {
	private final String SNAPSHOT_COLOR = new String("Color");
	private final String SNAPSHOT_GRAYSCALE = new String("Grayscale");
	
	private String fileName;
	private String filePath;
	private String fileFormat;
	private String snpColor;
	private WritableImage image;

	private ErrorType errorType = null;
	
	
	/**
	 * @param name
	 * @param path
	 * @param format
	 * @param color
	 * @param image
	 */
	public SnapshotProc(String name, String path, String format, String color, WritableImage image) {
		this.fileName = name;
		this.filePath = path;
		this.fileFormat = format;
		this.snpColor = color;
		this.image = image;
	}


	/**
	 * @return
	 */
	public int takeSnapshot() {

		//Make sure that the last character is a slash.
		if(!filePath.substring(filePath.length()).equals(File.separator)) {
			filePath = filePath.concat(File.separator);
		}

		SimpleDateFormat sDateFormat = new SimpleDateFormat(new String("ddMMyy_HHmm"));
		String createdFileDate = sDateFormat.format(new Date());

		FileUtil fileUtil = new FileUtil(fileName, fileFormat);
		
		int totalFiles = fileUtil.countExistingFiles(filePath);
		
		filePath = filePath.concat(fileName+totalFiles+"_"+createdFileDate+"."+fileFormat);
		
		File file = new File(filePath);

		BufferedImage bufImage = SwingFXUtils.fromFXImage(image, null);
		
		insertImageFrame(image, bufImage);
		
		if(snpColor.equals(SNAPSHOT_COLOR)) {
			try	{
				ImageIO.write(bufImage, fileFormat, file);
			}catch (IOException e){
				e.printStackTrace();
			}
		} else if(snpColor.equals(SNAPSHOT_GRAYSCALE)) {
			try {
				ImageIO.write(convertToGrayscale(bufImage), fileFormat, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			errorType = new ErrorType();			
			ErrorType.Snapshot errSnapshot = Snapshot.INVALID_SNP_COLOR;			
			errorType.setErrorType(errSnapshot.toString());
			return 1;
		}
		
		return 0;	
	}
	
	
	/**
	 * @param wImage
	 * @param bufImage
	 */
	private void insertImageFrame(WritableImage wImage, BufferedImage bufImage) {
		Graphics2D graphic2D = bufImage.createGraphics();

		graphic2D.setColor(Color.black);
		graphic2D.setStroke(new BasicStroke(2.0F));
		graphic2D.drawRect(0, 0, (int)wImage.getWidth(), (int)wImage.getHeight());
		graphic2D.dispose();
		
		return;
	}
	
	
	/**
	 * @param image
	 * @return
	 */
	private BufferedImage convertToGrayscale(final BufferedImage image){
		BufferedImage imageGray = new BufferedImage(image.getWidth(), image.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = imageGray.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		
		return imageGray;
	}


	/**
	 * @return the errorType
	 */
	public ErrorType getErrorType() {
		return errorType;
	}
}

/* EOF */
