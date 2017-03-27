/**
 *
 */
package openLab.launcher.property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Markus Lechner
 *
 */
public class LauncherConfig {
	private final String defPropFilePath = new String("/defaultConfiguration/defaultConfig.properties");

	
	/**
	 *
	 */
	public synchronized void loadConfiguration(Properties launcherProperties) {
		InputStream inputStream = null;

		inputStream = LauncherConfig.class.getResourceAsStream(defPropFilePath);

		if(inputStream != null) {
			try {
				launcherProperties.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return;
	}
}

/* EOF */