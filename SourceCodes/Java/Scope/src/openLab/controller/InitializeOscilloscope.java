/**
 * 
 */
package openLab.controller;

import java.io.File;

import openLab.message.packet.MessageCode;
import openLab.message.packet.UIControlMSG;
import openLab.processing.RequestProc;
import openLab.properties.PropIndex;
import openLab.properties.ScopeConfig;
import openLab.resource.MSQ_Handler;

/**
 * @author mLechner
 *
 */
public class InitializeOscilloscope {
	public final static String DIR_FEEDBACK = new String("Feedback");
	public final static String DIR_CONFIG = new String("Configuration");
	public final static String DIR_LOGGING = new String("Logging");	

	private RequestProc requestProcessing = null;
	
	/**
	 * 
	 */
	public void startRequestProcessing() {
		requestProcessing = new RequestProc();
		requestProcessing.initRequestProcessing();
		return;
	}
	
	
	/**
	 * 
	 */
	public void disconnectSerialDevice() {
		requestProcessing.terminateConnection();
		return;
	}
	
	
	public boolean introduceFeedback() {
		ScopeConfig scopeConfiguration = ScopeConfig.getInstance();
		
		if(Boolean.parseBoolean(scopeConfiguration.getScopeProperties().get(PropIndex.INTRO_FEEDBACK_DIALOG).toString())) {
			scopeConfiguration.getScopeProperties().setProperty(PropIndex.INTRO_FEEDBACK_DIALOG, "false");			
			
			MSQ_Handler msq_Handler = MSQ_Handler.getInstance();
			
			UIControlMSG uiControlMSG = new UIControlMSG();
			uiControlMSG.setMsgCode(MessageCode.UIControl);	
			uiControlMSG.setIntroduceFeedback(true);

			msq_Handler.getMsqProcessReq().add(uiControlMSG);
			return true;
		}			
		return false;
	}
	
	
	public void saveScopeConfiguration() {
		ScopeConfig scopeConfiguration = ScopeConfig.getInstance();					
		scopeConfiguration.saveConfiguration();			
		return;
	}
	
	
	/**
	 * 
	 */
	public void createDirectory() {
		File feedbackDir = new File(DIR_FEEDBACK);
		File configDir = new File(DIR_CONFIG);
		File configLogger = new File(DIR_LOGGING);

		if (!feedbackDir.exists()) {
			try{
				feedbackDir.mkdir();
			} catch(SecurityException se){
				se.printStackTrace();
			}
		}

		if (!configDir.exists()) {
			try{
				configDir.mkdir();
			} catch(SecurityException se){
				se.printStackTrace();
			}
		}

		if (!configLogger.exists()) {
			try{
				configLogger.mkdir();
			} catch(SecurityException se){
				se.printStackTrace();
			}
		}
		return;
	}
}

/* EOF */
