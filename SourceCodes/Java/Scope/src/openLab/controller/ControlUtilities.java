/**
 * 
 */
package openLab.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import openLab.resource.OpenLabDevice;

/**
 * @author Markus Lechner
 *
 */
public class ControlUtilities {

	public final static double GRAPH_DATA_POINTS = 600.0;
	public final static int X_AXIS_DIVISIONS = 10;
	public final static int Y_AXIS_DIVISIONS = 8;	
	public final static double REFERENCE_Y_POINT = (ControlUtilities.Y_AXIS_DIVISIONS / (double)OpenLabDevice.ADC_RESOLUTION_8BIT);

	public static FXMLLoader loader = null;
	
	// the values the timePerDev knob will offer
	public final static double[] timeDivSteps = {
			1.0, 500.0e-3, 200.0e-3, 100.0e-3,
			50.0e-3, 20.0e-3, 10.0e-3,
			5.0e-3, 2.0e-3,	1.0e-3,
			500.0e-6, 200.0e-6, 100.0e-6,
			50.0e-6, 20.0e-6, 10.0e-6,
			5.0e-6, 2.5e-6,	1.0e-6,
			250.0e-9
	};

	public final static double[] voltDivSteps = {
			0.1, 0.2, 0.5, 1.0, 
			2.0, 5.0
	};

	public final static double[] verticalPosSteps = {
			0.0, 0.2, 0.4, 0.6, 0.8, 
			1.0, 1.2, 1.4, 1.6, 1.8,
			2.0, 2.2, 2.4, 2.6, 2.8,
			3.0, 3.2, 3.4, 3.6, 3.8,
			4.0, 4.2, 4.4, 4.6, 4.8,
			5.0, 5.2, 5.4, 5.6, 5.8,
			6.0, 6.2, 6.4, 6.6, 6.8,
			7.0, 7.2, 7.4, 7.6, 7.8,
			8.0 
	};


	public void changeSeriesColor(String cbItem, int channel, ArrayList<Label> labels, LineChart<Number, Number> lcSignalGraph) {	
		XYChart.Series<Number, Number> tempSeries = null;

		if(channel == OpenLabDevice.CH1) {
			tempSeries = lcSignalGraph.getData().get(SignalGraph.SERIES_CH1_POS);
		} else if (channel == OpenLabDevice.CH2) {
			tempSeries = lcSignalGraph.getData().get(SignalGraph.SERIES_CH2_POS);
		} else {
			return;
		}

		switch (cbItem) {
		case "Red":
			tempSeries.getNode().setStyle("-fx-stroke: #FF0000");
			setMeasurementColor(channel, "#FF0000", labels, lcSignalGraph);
			break;
		case "Green":
			tempSeries.getNode().setStyle("-fx-stroke: #008000");
			setMeasurementColor(channel, "#008000", labels, lcSignalGraph);
			break;
		case "Purple":
			tempSeries.getNode().setStyle("-fx-stroke: #8F00B3");
			setMeasurementColor(channel, "#8F00B3", labels, lcSignalGraph);
			break;
		case "Blue":
			tempSeries.getNode().setStyle("-fx-stroke: #0000FF");
			setMeasurementColor(channel, "#0000FF", labels, lcSignalGraph);
			break;
		case "Orange":
			tempSeries.getNode().setStyle("-fx-stroke: #E68A00");
			setMeasurementColor(channel, "#E68A00", labels, lcSignalGraph);
			break;
		default:
			return;
		}	
		return;
	}


	public void setMeasurementColor(int channel, String format, ArrayList<Label> label, LineChart<Number, Number> lcSignalGraph) {
		if(channel == OpenLabDevice.CH1) {
			lcSignalGraph.getData().get(SignalGraph.SERIES_CH1_GND_POS).getNode().setStyle("-fx-stroke: " + format);
		} else if (channel == OpenLabDevice.CH2) {
			lcSignalGraph.getData().get(SignalGraph.SERIES_CH2_GND_POS).getNode().setStyle("-fx-stroke: " + format);
		}

		if(label == null) {
			return;
		}

		for(int i=0; i<label.size(); i++) {
			label.get(i).setTextFill(Color.web(format));			
		}			
		return;
	}


	public void configureTriggerSection(ComboBox<String> cbTriggerSource) {
		cbTriggerSource.getItems().addAll("CH1", "CH2", "OFF");
		cbTriggerSource.setValue("CH1");
		return;
	}


	public String formatTriggerLevelValue(double triggerValue) {
		NumberFormat formatter = new DecimalFormat("0.00");
		NumberFormat formatterFine = new DecimalFormat("0");

		if((triggerValue != 0.0) && ((triggerValue > -1.0) && (triggerValue < 1.00))) {
			triggerValue *= 1.0e3;
			return (formatterFine.format(triggerValue) + "mV");
		}		
		return (formatter.format(triggerValue) + "V");
	}


	public String formatTDivValue(double tDiv)	{
		NumberFormat formatter = new DecimalFormat("0.0");

		if(tDiv >= 1.0) {
			return (formatter.format(tDiv) + "s/");
		} else if(tDiv >= 1.0e-3 && tDiv < 1.0) {
			tDiv *= 1.0e3;
			return (formatter.format(tDiv) + "ms/");
		} else if(tDiv >= 1.0e-6 && tDiv < 1.0e-3) {
			tDiv *= 1.0e6;
			return (formatter.format(tDiv) + "0µs/");
		} else if(tDiv >= 1.0e-9 && tDiv < 1.0e-6) {
			tDiv *= 1.0e9;
			return (formatter.format(tDiv) + "0ns/");
		} else
			return "***.**s/";
	}


	public String formatVDivValue(double vDiv) {
		NumberFormat formatter = new DecimalFormat("0.00");
		NumberFormat formatterFine = new DecimalFormat("0");

		if(vDiv < 1.00) {
			vDiv *= 1.0e3;
			return (formatterFine.format(vDiv) + "mV/");
		}		
		return (formatter.format(vDiv) + "V/");
	}


	public Scene loadGui(String file, Object controller){
		Parent root = null;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
		
		if(controller != null) {
			loader.setController(controller);
		}
		
		try {			
			root = loader.load();		
			
			return new Scene(root);
		} catch (IOException e) {
			e.printStackTrace();			
		}
		return null;
	}
	
	
	public void getVideoFileNames(Map <String, ArrayList<String>> videoFiles, File file, String type) {
		 ArrayList<String> fileName = new ArrayList<String>();
			
		if(file.exists()) {
			File[] fileList = file.listFiles();

			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isFile()) {					
					fileName.add(file.toString().concat(File.separator).concat(fileList[i].getName()));
				}
			}
		} 
		
		if(!fileName.isEmpty()) {
			videoFiles.put(type, fileName);
		}					
		return;
	}

	
	
	public void closeIamgeStream(InputStream is) {
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}


	/**
	 * @return the timeDivSteps
	 */
	public double[] getTimeDivSteps() {
		return timeDivSteps;
	}

	/**
	 * @return the voltDivSteps
	 */
	public double[] getVoltDivSteps() {
		return voltDivSteps;
	}


	/**
	 * @return the verticalPosSteps
	 */
	public double[] getVerticalPosSteps() {
		return verticalPosSteps;
	}
}

/* EOF */
