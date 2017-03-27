/**
 *
 */
package openLab.launcher.process;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;

import openLab.launcher.process.ProcessManager.ProcessState;
import openLab.launcher.property.PropertyKey;
import openLab.launcher.resource.MSQ_Management;
import openLab.packet.ErrorCode;
import openLab.packet.GenericMessage;
import openLab.packet.MessageCode;
import openLab.packet.OpenLabSignalTools;
import openLab.packet.OpenLabSignalTools.SignalTool;
import openLab.packet.SignalToolCMD;
import openLab.packet.SignalToolCMD_MSG;

/**
 * @author mLechner
 *
 */
public class ProcessSupervisor implements Runnable {
	private final static int MIN_ARG_SIZE = 2;		
	private final String ARG_SEPERATOR = new String(",");	
	private Thread processSupervisorThread = null;
	private HashMap<OpenLabSignalTools.SignalTool, ProcessManager> processMonitorMap = new HashMap<OpenLabSignalTools.SignalTool, ProcessManager>();
	private Properties launcherProperties = new Properties();

	private boolean oscilloscopeRunningStatusSent = false;
	private boolean signalgeneratorRunningStatusSent = false;

	/**
	 * 
	 */
	public ProcessSupervisor(Properties launcherProperties) {
		this.launcherProperties = launcherProperties;
	}


	/**
	 * 
	 */
	public void executeProcessSupervisor() {
		processSupervisorThread = new Thread(this);
		processSupervisorThread.setName("Process.Supervisor");
		processSupervisorThread.setDaemon(true);
		processSupervisorThread.start();

		return;
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {		
		/*
		 * Create an instance of the MSQ Management Service
		 * This Object is utilized for the message passing
		 * between the ProcessSupervisor and the Controller class
		 */
		MSQ_Management msqManagement = MSQ_Management.getInstance();

		while(true) {
			checkProcessMessageQueue(msqManagement);
			subProcessMonitoring();			
			try	{
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}


	/**
	 * @param msqManagement
	 */
	private void checkProcessMessageQueue(MSQ_Management msqManagement) {
		if(!msqManagement.getMsqProcessSupervisor().isEmpty()) {

			GenericMessage genericMessage = msqManagement.getMsqProcessSupervisor().remove();

			switch (genericMessage.getMsgCode()) {
			case SIGNAL_TOOL_CMD:
				SignalToolCMD_MSG signalToolCMD_MSG = new SignalToolCMD_MSG();
				signalToolCMD_MSG = (SignalToolCMD_MSG)genericMessage;
				
				//If the Signal-Toolkit is already running, the corresponding request is 
				//removed to avoid undefined behaviour.
				if(processMonitorMap.containsKey(signalToolCMD_MSG.getSignalTool())) {
					break;
				}				
				
				executeSignalToolRequest(signalToolCMD_MSG);
				break;				
			default:
				break;
			}
		}
	}


	/**
	 * @param signalToolCMD_MSG
	 */
	private void executeSignalToolRequest(SignalToolCMD_MSG signalToolCMD_MSG) {
		switch (signalToolCMD_MSG.getSignalTool()) {
		case OSCILLOSCOPE:
			if(signalToolCMD_MSG.getSignalToolCommand() == SignalToolCMD.EXECUTE) {
				if(!isSignalToolExecuting(SignalTool.OSCILLOSCOPE)) {
					initOscilloscopeProcessSettings();
				} else {
					break;
				}
			}
			break;
		case SIGNALGENERATOR:
			if(signalToolCMD_MSG.getSignalToolCommand() == SignalToolCMD.EXECUTE) {
				if(!isSignalToolExecuting(SignalTool.SIGNALGENERATOR)) {
					initSignalgeneratorProcessSettings();
				} else {
					break;
				}
			}
			break;				
		case ALL_SIGNAL_TOOLS:
			if(signalToolCMD_MSG.getSignalToolCommand() == SignalToolCMD.TERMINATE) {
				terminateSubProcess(null, true);
			}
			break;
		default:
			break;
		}


		return;
	}


	/**
	 * @param signalTool
	 * @return
	 */
	private boolean isSignalToolExecuting(SignalTool signalTool) {
		if (processMonitorMap.containsKey(signalTool)) {
			return true;
		}
		return false;
	}


	/**
	 * 
	 */
	private void subProcessMonitoring() {
		if(!processMonitorMap.isEmpty()) {				
			for(int i=0;(i<=OpenLabSignalTools.arraySignalTools.length) && (i<=processMonitorMap.size());i++) {
				if(processMonitorMap.containsKey(OpenLabSignalTools.arraySignalTools[i])) {
					if(processMonitorMap.get(OpenLabSignalTools.arraySignalTools[i]).getProcess().isAlive()) {
						//TODO perform regular sub-process check here....						
						if(processMonitorMap.containsKey(SignalTool.OSCILLOSCOPE) && !oscilloscopeRunningStatusSent) {
							sendMSGtoControllerUI(null, null, null, "Oscilloscope is executing.");
							oscilloscopeRunningStatusSent = true;
						} 
						
						if(processMonitorMap.containsKey(SignalTool.SIGNALGENERATOR) && !signalgeneratorRunningStatusSent) {
							sendMSGtoControllerUI(null, null, null, "Signalgenerator is executing.");
							signalgeneratorRunningStatusSent = true;
						}
					} else {
						if(processMonitorMap.get(OpenLabSignalTools.arraySignalTools[i]).getProcess().exitValue() != 0) {
							sendMSGtoControllerUI(OpenLabSignalTools.arraySignalTools[i], SignalToolCMD.ENABLE, ErrorCode.PROCESS_CRASHED,
									OpenLabSignalTools.arraySignalTools[i].toString() + ": Process crashed. \n Check LOG file.");
						} else {
							sendMSGtoControllerUI(OpenLabSignalTools.arraySignalTools[i], SignalToolCMD.ENABLE, ErrorCode.NONE,
									OpenLabSignalTools.arraySignalTools[i].toString() + ": Process terminated.");								
						}
						
						processMonitorMap.remove(OpenLabSignalTools.arraySignalTools[i]);

						if(OpenLabSignalTools.arraySignalTools[i] == SignalTool.OSCILLOSCOPE && !oscilloscopeRunningStatusSent) {
							oscilloscopeRunningStatusSent = false;
						} 
						
						if(OpenLabSignalTools.arraySignalTools[i] == SignalTool.SIGNALGENERATOR && !signalgeneratorRunningStatusSent) {
							signalgeneratorRunningStatusSent = false;
						}						
					} 
				}					
			}
		} 
		return;
	}


	/**
	 * @param signalTool
	 * @param termAllSubProc
	 * @return
	 */
	public boolean terminateSubProcess(OpenLabSignalTools.SignalTool signalTool, boolean termAllSubProc) {
		if((!processMonitorMap.isEmpty() && (signalTool != null) && (!termAllSubProc))) {
			if(processMonitorMap.containsKey(signalTool)) {
				processMonitorMap.get(signalTool).getProcess().destroy();
				return true;
			} else {
				return false;
			}		
		} else {
			for(int i=0;(i<=OpenLabSignalTools.arraySignalTools.length) && (i<=processMonitorMap.size());i++) {				
				if(processMonitorMap.containsKey(OpenLabSignalTools.arraySignalTools[i])) {
					processMonitorMap.get(OpenLabSignalTools.arraySignalTools[i]).getProcess().destroy();
					processMonitorMap.remove(OpenLabSignalTools.arraySignalTools[i]);
				}					
			}
			return true;
		}		
	}


	/**
	 * 
	 */
	private void initOscilloscopeProcessSettings() {
		String[] vmArguments = new String[] {};
		String tempArg = new String();

		String toolDir = getProcessDir(PropertyKey.SUBFOLDER_SIGNALTOOLS, 
				PropertyKey.OSCILLOSCOPE_FOLDER,
				PropertyKey.OSCILLOSCOPE_FILE_NAME,true);

		String workDir = getProcessDir(PropertyKey.SUBFOLDER_SIGNALTOOLS, 
				PropertyKey.OSCILLOSCOPE_FOLDER,null,false);

		tempArg = String.valueOf(launcherProperties.get(PropertyKey.OSCILLOSCOPE_VM_ARG))
				.concat(",").concat(toolDir);					
		vmArguments = getStringArray(tempArg);

		//create new Process 
		ProcessManager processManager = new ProcessManager(vmArguments, workDir);
		processManager.setLogFileName("LOG_Oscilloscope.txt");

		ProcessState processState = processManager.createProcess();
		if(processState.equals(ProcessManager.ProcessState.RUNNING)) {
			processMonitorMap.put(SignalTool.OSCILLOSCOPE, processManager);
			sendMSGtoControllerUI(SignalTool.OSCILLOSCOPE, SignalToolCMD.DISABLE, ErrorCode.NONE, "Oscilloscope: will be started...");
			System.out.println("ERROR NONE");
		} else if(processState.equals(ProcessManager.ProcessState.SECURITY)){
			sendMSGtoControllerUI(SignalTool.OSCILLOSCOPE, SignalToolCMD.ENABLE, ErrorCode.PERMISSION_DENIED, "Oscilloscope: Permission denied!");
			System.out.println("ERROR SECURITY");
		} else {
			System.out.println("ERROR UNKNOWN");
		}
		return;
	}


	/**
	 * 
	 */
	private void initSignalgeneratorProcessSettings() {
		String[] vmArguments = new String[] {};
		String tempArg = new String();

		String toolDir = getProcessDir(PropertyKey.SUBFOLDER_SIGNALTOOLS, 
				PropertyKey.SIGNALGENERATOR_FOLDER,
				PropertyKey.SIGNALGENERATOR_FILE_NAME,true);

		String workDir = getProcessDir(PropertyKey.SUBFOLDER_SIGNALTOOLS, 
				PropertyKey.SIGNALGENERATOR_FOLDER,null,false);

		tempArg = String.valueOf(launcherProperties.get(PropertyKey.SIGNALGENERATOR_VM_ARG))
				.concat(",").concat(toolDir);					
		vmArguments = getStringArray(tempArg);

		//create new Process 
		ProcessManager processManager = new ProcessManager(vmArguments, workDir);
		processManager.setLogFileName("LOG_Signalgenerator.txt ");

		ProcessState processState = processManager.createProcess();
		if(processState.equals(ProcessManager.ProcessState.RUNNING)) {
			processMonitorMap.put(SignalTool.SIGNALGENERATOR, processManager);
			sendMSGtoControllerUI(SignalTool.SIGNALGENERATOR, SignalToolCMD.DISABLE, ErrorCode.NONE, "Signalgenerator: will be started...");
		} else if(processState.equals(ProcessManager.ProcessState.SECURITY)){
			sendMSGtoControllerUI(SignalTool.SIGNALGENERATOR, SignalToolCMD.ENABLE, ErrorCode.PERMISSION_DENIED, "Signalgenerator: Permission denied!");
		}
		return;
	}


	/**
	 * @param subFolder
	 * @param deviceFolder
	 * @param fileName
	 * @param toolDir
	 * @return
	 */
	private String getProcessDir(String subFolder, String deviceFolder, String fileName, boolean toolDir) {
		String dir = new String();

		if(toolDir) {
			dir = dir.concat(System.getProperty("user.dir"))
					.concat(File.separator).concat(String.valueOf(launcherProperties.get(subFolder)))
					.concat(File.separator).concat(String.valueOf(launcherProperties.get(deviceFolder)))
					.concat(File.separator).concat(String.valueOf(launcherProperties.get(fileName)));
		} else {
			dir = dir.concat(System.getProperty("user.dir"))
					.concat(File.separator).concat(String.valueOf(launcherProperties.get(subFolder)))
					.concat(File.separator).concat(String.valueOf(launcherProperties.get(deviceFolder)));
		}
		return dir;
	}


	/**
	 * @param signalTool
	 * @param signalToolCommand
	 * @param errorCode
	 * @param status
	 */
	private void sendMSGtoControllerUI(OpenLabSignalTools.SignalTool signalTool, SignalToolCMD signalToolCommand, ErrorCode errorCode, String status) {
		SignalToolCMD_MSG signalToolCMD_MSG = new SignalToolCMD_MSG();
		signalToolCMD_MSG.setMsgCode(MessageCode.SIGNAL_TOOL_CMD);

		signalToolCMD_MSG.setSignalTool(signalTool);
		signalToolCMD_MSG.setSignalToolCommand(signalToolCommand);
		signalToolCMD_MSG.setErrorCode(errorCode);
		signalToolCMD_MSG.setStatus(status);

		MSQ_Management msqManagement = MSQ_Management.getInstance();
		msqManagement.getMsqControllerUI().add(signalToolCMD_MSG);		
		return;
	}


	/**
	 * @param str
	 * @return
	 */
	public String[] getStringArray(String str) {
		int argc = MIN_ARG_SIZE;

		for (int i = 0; i < str.length(); i++) {
			if (Character.isLetter(str.charAt(i)))
				argc++;
		}		

		String[] strArray = new String[argc];

		if(str.contains(",")) {
			strArray = str.split(ARG_SEPERATOR);
		} else {
			strArray[0] = str;
		}
		return strArray;
	}


	/**
	 * @return the processMonitorMap
	 */
	public HashMap<OpenLabSignalTools.SignalTool, ProcessManager> getProcessMonitorMap() {
		return processMonitorMap;
	}

}

/* EOF */
