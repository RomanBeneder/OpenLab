/**
 * 
 */
package FileUtility;

import java.io.File;

/**
 * @author Markus Lechner
 *
 */
public class FileUtil {
	private String fileName;
	private String fileExtension;


	/**
	 * @param fileName
	 * @param fileExtension
	 */
	public FileUtil(String fileName, String fileExtension) {
		this.fileName = fileName;
		this.fileExtension = fileExtension;
	}

	/**
	 * @return
	 */
	public String getFileName(String subDir) {
		String configDir;
		File fileConfig = null;

		if(fileName == null || fileExtension == null) {
			return null;
		}

		if(subDir == null) {
			configDir = System.getProperty("user.dir");
		} else {
			configDir = System.getProperty("user.dir") + "/" + fileName;
		}
		
		configDir = System.getProperty("user.dir") + "/" + fileName;
		fileConfig = new File(configDir);

		if(!fileConfig.exists()) {
			return new String(fileName + fileExtension);
		} 

		int totalFiles = countExistingFiles(configDir);
		
		return new String(configDir.concat("/").concat(fileName).
				concat(String.valueOf(totalFiles)).concat(fileExtension));
	}

	
	/**
	 * @param filePath
	 * @return
	 */
	public int countExistingFiles(String filePath) {
		int totalFiles = 0;
		File[] fList = new File(filePath).listFiles();

		for (File file : fList){
			if (file.isFile()){
				if(file.getName().contains(fileName)) {
					totalFiles++;
				}
			}
		}
		return totalFiles;
	}
}

/* EOF */
