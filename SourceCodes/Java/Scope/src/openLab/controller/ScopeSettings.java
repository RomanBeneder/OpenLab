/**
 * 
 */
package openLab.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Markus Lechner
 *
 */
public class ScopeSettings {
	public final static String GUI_VERSION = new String("GUI_VERSION");
	public final static String GUI_VERSION_VAL = new String("1.0.2");
	public final static String GUI_BUILD_ID = new String("GUI_BUILD_ID");
	public final static String GUI_BUILD_ID_VAL = new String("20170322-102");

	public final static String UNKNOWN = new String("UNKNOWN");
	public final static String LANG_FORMAT_GER = new String("GER");
	public final static String LANG_FORMAT_ENG = new String("ENG");

	public final static String SNP_FILE_NAME = new String("SNP_NAME");
	public final static String SNP_FILE_PATH = new String("SNP_PATH");
	public final static String SNP_FILE_FORMAT = new String("SNP_FRMT");
	public final static String SNP_COLOR = new String("SNP_CLR");

	public final static String CSV_FILE_NAME = new String("CSV_NAME");
	public final static String CSV_FILE_PATH = new String("CSV_PATH");
	public final static String CSV_LANG_FORMAT = new String("CSV_LANG_FRMT");

	public final static String CFG_INVERT_CH1 = new String("INVERT_CH1");
	public final static String CFG_INVERT_CH2 = new String("INVERT_CH2");
	public final static String CFG_PROBE_ATT_CH1 = new String("PROBE_ATT_CH1");
	public final static String CFG_PROBE_ATT_CH2 = new String("PROBE_ATT_CH2");	
	public final static String CFG_SIG_COLOR_CH1 = new String("CFG_SIG_COLOR_CH1");	
	public final static String CFG_SIG_COLOR_CH2 = new String("CFG_SIG_COLOR_CH2");	
	public final static String CFG_HRZ_STEP_VALUE = new String("CFG_HRZ_STEP_VALUE");	
	public final static String CFG_HRZ_TIME_MODE = new String("CFG_HRZ_TIME_MODE");	
	public final static String CFG_PROBE_ATT_APPLY = new String("PROBE_ATT_APLLY");
	public final static String CFG_PROBE_COMP = new String("PROBE_COMP");

	private Map <String, String> systemSetting = new HashMap<String, String>();
	private Map <String, String> snpSetting = new HashMap<String, String>();
	private Map <String, String> exportCSVSetting = new HashMap<String, String>();	
	private Map <String, String> channelConfig = new HashMap<String, String>();

	/**
	 * 
	 */
	public ScopeSettings() {
		initSystemVersion();
		initSnapshotSettings();
		initExportCSVSettings();
		initChannelConfig();
	}


	/**
	 * 
	 */
	private void initSystemVersion() {
		systemSetting.put(GUI_VERSION, GUI_VERSION_VAL);
		systemSetting.put(GUI_BUILD_ID, GUI_BUILD_ID_VAL);
		return;
	}


	/**
	 * 
	 */
	private void initSnapshotSettings() {
		snpSetting.put(SNP_FILE_NAME, "Snapshot");
		snpSetting.put(SNP_FILE_PATH, UNKNOWN);
		snpSetting.put(SNP_FILE_FORMAT, "png");
		snpSetting.put(SNP_COLOR, "Color");
		return;
	}


	/**
	 * 
	 */
	private void initExportCSVSettings() {
		exportCSVSetting.put(CSV_FILE_NAME, "SignalGraphData");
		exportCSVSetting.put(CSV_FILE_PATH, UNKNOWN);
		exportCSVSetting.put(CSV_LANG_FORMAT, LANG_FORMAT_ENG);
		return;
	}


	/**
	 * 
	 */
	private void initChannelConfig() {
		channelConfig.put(CFG_INVERT_CH1, "false");
		channelConfig.put(CFG_INVERT_CH2, "false");
		channelConfig.put(CFG_PROBE_ATT_CH1, "10"); //Default: 10:1
		channelConfig.put(CFG_PROBE_ATT_CH2, "10"); //Default: 10:1
		channelConfig.put(CFG_SIG_COLOR_CH1, "Red");
		channelConfig.put(CFG_SIG_COLOR_CH2, "Blue");
		channelConfig.put(CFG_HRZ_STEP_VALUE, "50"); //Default: 50 steps per click
		channelConfig.put(CFG_HRZ_TIME_MODE, "Normal"); //Default: Normal Mode
		channelConfig.put(CFG_PROBE_ATT_APPLY, "false");
		channelConfig.put(CFG_PROBE_COMP, "false");
		return;
	}


	/**
	 * @return the systemSetting
	 */
	public Map<String, String> getSystemSetting() {
		return systemSetting;
	}


	/**
	 * @return the snpSetting
	 */
	public Map<String, String> getSnpSetting() {
		return snpSetting;
	}


	/**
	 * @return the exportCSVSetting
	 */
	public Map<String, String> getExportCSVSetting() {
		return exportCSVSetting;
	}


	/**
	 * @return the channelConfig
	 */
	public Map<String, String> getChannelConfig() {
		return channelConfig;
	}
}

/* EOF */
