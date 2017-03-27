/**
 *
 */
package openLab.launcher.process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;

/**
 * @author mLechner
 *
 */
public class ProcessManager {
	private String[] vmArgument = null;
	private String workDir = new String();
	private String logFileName = new String("log");
	private Process process = null;

	public enum ProcessState {
		Error,
		RUNNING,
		SECURITY,
		FILE_IO,
		TERMINATED
	}


	/**
	 * @param vmArgument
	 * @param workDir
	 */
	public ProcessManager(String[] vmArgument, String workDir) {
		this.vmArgument = vmArgument;
		this.workDir = workDir;
	}


	/**
	 * @return
	 */
	public ProcessState createProcess() {
		if(vmArgument == null) {
			return ProcessState.Error;
		}

		ProcessBuilder pb = new ProcessBuilder(vmArgument);

		pb.directory(new File(workDir));
		File fLogging = new File(logFileName);		
		
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(fLogging);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.appendTo(fLogging));

		try {
			process = pb.start();
		} catch (IOException e) {
			e.printStackTrace();
			return ProcessState.SECURITY;
		}

		assert pb.redirectInput() == Redirect.PIPE;
		assert pb.redirectOutput().file() == fLogging;

		try {
			assert process.getInputStream().read() == -1;
		} catch (IOException e) {
			e.printStackTrace();
			return ProcessState.FILE_IO;
		}	
		return ProcessState.RUNNING;
	}


	/**
	 * @return
	 */
	public String getLogFileName() {
		return logFileName;
	}



	/**
	 * @param logFileName
	 */
	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}



	/**
	 * @return
	 */
	public Process getProcess() {
		return process;
	}

}

/* EOF */
