/**
 *
 */
package openLab.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import openLab.controller.InitializeOscilloscope;

/**
 * @author Markus Lechner
 *
 */
public class ScopeConfig {
	private final String defPropFilePath = new String("/DefaultConfiguration/defaultConfig.properties");
	private final String appPropFileName = new String("configuration.properties");

	private static ScopeConfig scopeConfig = null;
	private Properties scopeProperties = new Properties();


	/**
	 * @return
	 */
	public static ScopeConfig getInstance() {
		if(scopeConfig == null)	{
			synchronized(ScopeConfig.class)	{
				scopeConfig = new ScopeConfig();
			}
		}
		return scopeConfig;
	}


	/**
	 *
	 */
	public void saveConfiguration() {
		OutputStream osAppProperties = null;
		String configDir = System.getProperty("user.dir")+File.separator+
				InitializeOscilloscope.DIR_CONFIG+File.separator+appPropFileName;

		try {
			osAppProperties = new FileOutputStream(configDir);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			scopeProperties.store(osAppProperties, null);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (osAppProperties != null) {
				try {
					osAppProperties.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return;
	}


	/**
	 *
	 */
	public synchronized void loadConfiguration() {
		InputStream inputStream = null;
		File fileConfig = new File(System.getProperty("user.dir")+File.separator+
				InitializeOscilloscope.DIR_CONFIG+File.separator+appPropFileName);

		if(!fileConfig.exists()) {
			inputStream = ScopeConfig.class.getResourceAsStream(defPropFilePath);
		} else {
			try {
				inputStream = new FileInputStream(fileConfig);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		if(inputStream != null) {
			try {
				scopeProperties.load(inputStream);
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


	/**
	 * @return the scopeProperties
	 */
	public Properties getScopeProperties() {
		return scopeProperties;
	}
}

/* EOF */