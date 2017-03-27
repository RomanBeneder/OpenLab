/**
 * 
 */
package openLab.processing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import FileUtility.FileUtil;
import dialog.StandardDialog;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert.AlertType;
import openLab.controller.ControlUtilities;

/**
 * @author Markus Lechner
 *
 */
public class ExportCSVProc {
	private final String CSV_HEADER_DUAL_CH = new String("Channel1;;;Channel2\nTime [x];Volt [y];;Time [x];Volt [y]\n");
	private final String CSV_SEPERATOR_SEMICOLON = new String(";");
	private final String CSV_POINT_ENG = new String(".");
	private final String CSV_COMMA_GER = new String(",");

	private boolean exportCH1 = false;
	private boolean exportCH2 = false;

	private double horzScale;	

	private String csvHeaderSingleCH = new String("Channelx\nTime [x];Volt [y]\n");

	private String fileName;
	private String filePath;
	private String langFormat;

	private ArrayList<Double> measuredDataCH1 = new ArrayList<Double>();
	private ArrayList<Double> measuredDataCH2 = new ArrayList<Double>();

	/**
	 * @param exportCH1
	 * @param exportCH2
	 * @param horzScale
	 * @param fileName
	 * @param filePath
	 * @param langFormat
	 */
	public ExportCSVProc(boolean exportCH1, boolean exportCH2, double horzScale, String fileName, String filePath, String langFormat) {
		this.exportCH1 = exportCH1;
		this.exportCH2 = exportCH2;
		this.horzScale = horzScale;
		this.fileName = fileName;
		this.filePath = filePath;
		this.langFormat = langFormat;
	}


	/**
	 * 
	 */
	public void executeCSVExport() {
		Thread t = new Thread(SignalGraphExportToCSV);
		t.setName("Thread: CSV_Export");
		t.setDaemon(true);
		t.start();

		return;
	}


	/**
	 * 
	 */
	private final Task<Void> SignalGraphExportToCSV = new Task<Void>() {

		@Override
		protected Void call() {
			StringBuilder signalGraphData = new StringBuilder();

			calculateSignalData(signalGraphData);			
			String tempSignalGraphData = new String(signalGraphData.toString());

			if(langFormat.equals("GER")) {
				tempSignalGraphData = tempSignalGraphData.replace(CSV_POINT_ENG, CSV_COMMA_GER);
			} else {
				tempSignalGraphData = tempSignalGraphData.replace(CSV_COMMA_GER, CSV_POINT_ENG);
			}			

			if(!filePath.substring(filePath.length()).equals(File.separator)) {
				filePath = filePath.concat(File.separator);
			}

			SimpleDateFormat sDateFormat = new SimpleDateFormat(new String("ddMMyy_HHmm"));
			String createdFileDate = sDateFormat.format(new Date());

			FileUtil fileUtil = new FileUtil(fileName, "csv");			
			int totalFiles = fileUtil.countExistingFiles(filePath);

			filePath = filePath.concat(fileName+totalFiles+"_"+createdFileDate+"."+"csv");

			File fileSignalGraph = new File(filePath);
			FileWriter fwSignalGraph = null;

			try {
				fwSignalGraph = new FileWriter(fileSignalGraph);
				fwSignalGraph.write(tempSignalGraphData);
				fwSignalGraph.flush();
				fwSignalGraph.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}


		/**
		 * @param signalGraphData
		 * @return
		 */
		private boolean calculateSignalData(StringBuilder signalGraphData) {
			if(exportCH1 && exportCH2) {
				if(measuredDataCH1 != null && measuredDataCH2 != null) {
					double timeFact = ((1.0 / measuredDataCH1.size()) * (horzScale * ControlUtilities.X_AXIS_DIVISIONS));				

					//Insert the header line for channel identification
					signalGraphData.append(CSV_HEADER_DUAL_CH);				

					for(int i=0; (i < measuredDataCH1.size()) || (i < measuredDataCH2.size()); i++) {					
						signalGraphData.append(timeFact * i).append(CSV_SEPERATOR_SEMICOLON).
						append(measuredDataCH1.get(i)).append(";;");

						signalGraphData.append(timeFact * i).append(CSV_SEPERATOR_SEMICOLON).
						append(measuredDataCH2.get(i)).append("\n");
					}
				}
			} else if(exportCH1 && (measuredDataCH1 != null)) {
				insertSignalGraphData("CH1", signalGraphData, measuredDataCH1);
			} else if(exportCH2 && (measuredDataCH2 != null)) {
				insertSignalGraphData("CH2", signalGraphData, measuredDataCH2);
			} else {
				return false;
			}
			return true;
		}


		/**
		 * @param channel
		 * @param signalGraphData
		 * @param measuredData
		 */
		private void insertSignalGraphData(String channel, StringBuilder signalGraphData, ArrayList<Double> measuredData) {
			double timeFact = ((1.0 / measuredData.size()) * (horzScale * ControlUtilities.X_AXIS_DIVISIONS));				

			//Insert the header line for channel identification
			signalGraphData.append(csvHeaderSingleCH.replaceFirst("x", channel));				

			for(int i=0; i < measuredData.size(); i++) {					
				signalGraphData.append(timeFact * i).append(CSV_SEPERATOR_SEMICOLON).
				append(measuredData.get(i)).append("\n");
			}

			return;
		}

		@Override
		protected void setException(Throwable t) {
			// TODO Auto-generated method stub
			super.setException(t);
			System.out.println("ExportCSV:" + t);
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();
			
			StandardDialog standardDialog = new StandardDialog(null);
			Platform.runLater(() -> {
				standardDialog.setTitle("CSV Export");
				standardDialog.setContentText("The CSV file was exported successfully!\nDirectory: " + filePath);
				standardDialog.createDialog(AlertType.INFORMATION);
			});			
			
			System.out.println("ExportCSV: Exported CSV data to: " + filePath);
		}

		@Override
		protected void failed() {
			// TODO Auto-generated method stub
			super.failed();
			System.out.println("ExportCSV: Operation failed!");
		}

	};

	/**
	 * @param measuredDataCH1 the measuredDataCH1 to set
	 */
	public void setMeasuredDataCH1(ArrayList<Double> measuredDataCH1) {
		this.measuredDataCH1 = measuredDataCH1;
	}

	/**
	 * @param measuredDataCH2 the measuredDataCH2 to set
	 */
	public void setMeasuredDataCH2(ArrayList<Double> measuredDataCH2) {
		this.measuredDataCH2 = measuredDataCH2;
	}

}

/* EOF */
