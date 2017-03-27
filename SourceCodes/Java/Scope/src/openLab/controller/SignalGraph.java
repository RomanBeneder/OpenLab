/**
 * 
 */
package openLab.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.util.Duration;
import openLab.resource.MSQ_Handler;
import openLab.resource.OpenLabDevice;

/**
 * @author Markus Lechner
 *
 */
public class SignalGraph {
	public final static int SERIES_CH1_POS = 0;
	public final static int SERIES_CH1_GND_POS = 1;
	public final static int SERIES_CH2_POS = 2;
	public final static int SERIES_CH2_GND_POS = 3;
	public final static int SERIES_TRIGGER_LVL = 4;
	public final static int CLEAR_SIGNAL_SERIES = 5;

	private final int REFRESH_RATE = 33;	
	public final double GRAPH_DATA_POINTS = 600.0;

	private XYChart.Series<Number, Number> seriesCH1 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> seriesCH1GND = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> seriesCH2 = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> seriesCH2GND = new XYChart.Series<Number, Number>();
	private XYChart.Series<Number, Number> seriesTriggerLvl = new XYChart.Series<Number, Number>();

	private MSQ_Handler msq_Handler = null;

	private final Timeline animationTimerCH1 = new Timeline(new KeyFrame(Duration.millis(REFRESH_RATE), new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent actionEvent){
			updateCH1SignalGraph();
		}
	}));

	private final Timeline animationTimerCH2 = new Timeline(new KeyFrame(Duration.millis(REFRESH_RATE), new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent actionEvent){
			updateCH2SignalGraph();
		}
	}));	


	/**
	 * 
	 */
	public SignalGraph() {
		configAnimationTimer();
		msq_Handler = MSQ_Handler.getInstance();
	}


	/**
	 * 
	 */
	private void configAnimationTimer() {
		animationTimerCH1.setCycleCount(Animation.INDEFINITE);
		animationTimerCH2.setCycleCount(Animation.INDEFINITE);
		return;
	}


	/**
	 * 
	 */
	public void startAnimTimerCH1() {
		animationTimerCH1.play();
	}


	/**
	 * @param shouldClear
	 */
	public void stopAnimTimerCH1(boolean shouldClear) {
		animationTimerCH1.stop();

		if(shouldClear) {
			if(!Platform.isFxApplicationThread()) {
				Platform.runLater(() -> {
					clearSeries(OpenLabDevice.CH1);
				});				
			} 
			clearSeries(OpenLabDevice.CH1);			
		}
		return;
	}


	/**
	 * @param shouldClear
	 */
	public void stopAnimTimerCH2(boolean shouldClear) {
		animationTimerCH2.stop();

		if(shouldClear) {
			if(!Platform.isFxApplicationThread()) {
				Platform.runLater(() -> {
					clearSeries(OpenLabDevice.CH2);
				});				
			} 
			clearSeries(OpenLabDevice.CH2);			
		}
		return;
	}


	/**
	 * 
	 */
	public void startAnimTimerCH2() {
		animationTimerCH2.play();
	}


	/**
	 * 
	 */
	private void updateCH1SignalGraph() {		

		if(msq_Handler.getSampleDataCH1().isEmpty()) {
			return;			
		}

		//Remove points which are not within the drawing area
		if (seriesCH1.getData().size() >= msq_Handler.getSampleDataCH1().peek().size()) {
			seriesCH1.getData().remove(0, (seriesCH1.getData().size() - msq_Handler.getSampleDataCH1().peek().size()));
		}
		
		seriesCH1.setData(msq_Handler.getSampleDataCH1().remove());

		if(msq_Handler.getSampleDataCH1().size() >= 2) {
			msq_Handler.getSampleDataCH1().clear();
		}
		return;
	}


	/**
	 * 
	 */
	private void updateCH2SignalGraph() {

		if(msq_Handler.getSampleDataCH2().isEmpty()) {
			return;			
		}

		//Remove points which are not within the drawing area
		if (seriesCH2.getData().size() >= msq_Handler.getSampleDataCH2().peek().size()) {
			seriesCH2.getData().remove(0, (seriesCH2.getData().size() - msq_Handler.getSampleDataCH2().peek().size()));
		}

		seriesCH2.setData(msq_Handler.getSampleDataCH2().remove());

		if(msq_Handler.getSampleDataCH2().size() >= 2) {
			msq_Handler.getSampleDataCH2().clear();
		}
		return;
	}


	/**
	 * @param lcSignalGraph
	 */
	public void configureSignalGraph(LineChart<Number, Number> lcSignalGraph) {		
		lcSignalGraph.getData().add(SERIES_CH1_POS, seriesCH1);
		lcSignalGraph.getData().add(SERIES_CH1_GND_POS, seriesCH1GND);
		lcSignalGraph.getData().add(SERIES_CH2_POS, seriesCH2);
		lcSignalGraph.getData().add(SERIES_CH2_GND_POS, seriesCH2GND);
		lcSignalGraph.getData().add(SERIES_TRIGGER_LVL, seriesTriggerLvl);

		seriesCH1.getNode().setStyle("-fx-stroke: #FF0000");
		seriesCH1GND.getNode().setStyle("-fx-stroke: #FF0000;-fx-stroke-width: 0.7px");

		seriesCH2.getNode().setStyle("-fx-stroke: #0000FF");
		seriesCH2GND.getNode().setStyle("-fx-stroke: #0000FF;-fx-stroke-width: 0.7px");

		seriesTriggerLvl.getNode().setStyle("-fx-stroke: #000000");	

		return;
	}


	/**
	 * @param channel
	 * @param knobValue
	 */
	public void indicateGNDlines(int channel, double knobValue, double xMinPos, double xMaxPos) {
		if(channel == OpenLabDevice.CH1) {
			seriesCH1GND.getData().remove(0, (seriesCH1GND.getData().size()));
			seriesCH1GND.getData().add(new Data<Number, Number>(xMinPos, ControlUtilities.REFERENCE_Y_POINT + knobValue));
			seriesCH1GND.getData().add(new Data<Number, Number>(xMaxPos, ControlUtilities.REFERENCE_Y_POINT + knobValue));		
		} else if(channel == OpenLabDevice.CH2) {
			seriesCH2GND.getData().remove(0, (seriesCH2GND.getData().size()));
			seriesCH2GND.getData().add(new Data<Number, Number>(xMinPos, (ControlUtilities.REFERENCE_Y_POINT + knobValue)));
			seriesCH2GND.getData().add(new Data<Number, Number>(xMaxPos, (ControlUtilities.REFERENCE_Y_POINT + knobValue)));				
		}
		return;
	}


	/**
	 * @param channel
	 */
	public void clearGNDlines(int channel) {
		if(channel == OpenLabDevice.CH1) {
			seriesCH1GND.getData().clear();
		} else if(channel == OpenLabDevice.CH2) {
			seriesCH2GND.getData().clear();
		} else if(channel == OpenLabDevice.BOTH_CHANNELS) {
			seriesCH1GND.getData().clear();
			seriesCH2GND.getData().clear();
		}	
		return;
	}


	/**
	 * 
	 */
	public void clearTriggerLvlLine() {
		seriesTriggerLvl.getData().clear();		
		return;
	}


	/**
	 * @param virtTriggerValue
	 */
	public void updateTriggerLvlInd(double virtTriggerValue, double xMinPos, double xMaxPos) {
		seriesTriggerLvl.getData().remove(0, seriesTriggerLvl.getData().size());
		seriesTriggerLvl.getData().add(new Data<Number, Number>(xMinPos, virtTriggerValue));
		seriesTriggerLvl.getData().add(new Data<Number, Number>(xMaxPos, virtTriggerValue));
		return;
	}


	/**
	 * @param channelNr
	 */
	public void clearSeries(int channelNr) {
		switch (channelNr) {
		case OpenLabDevice.CH1:
			seriesCH1.getData().remove(0, (seriesCH1.getData().size()));
			seriesCH1GND.getData().remove(0, (seriesCH1GND.getData().size()));
			break;

		case OpenLabDevice.CH2:
			seriesCH2.getData().remove(0, (seriesCH2.getData().size()));
			seriesCH2GND.getData().remove(0, (seriesCH2GND.getData().size()));
			break;

		case OpenLabDevice.BOTH_CHANNELS:
			seriesCH1.getData().remove(0, (seriesCH1.getData().size()));
			seriesCH1GND.getData().remove(0, (seriesCH1GND.getData().size()));

			seriesCH2.getData().remove(0, (seriesCH2.getData().size()));
			seriesCH2GND.getData().remove(0, (seriesCH2GND.getData().size()));
			break;

		case CLEAR_SIGNAL_SERIES:
			seriesCH1.getData().remove(0, (seriesCH1.getData().size()));
			seriesCH2.getData().remove(0, (seriesCH2.getData().size()));
			break;

		default:
			break;
		}
		return;
	}
}

/* EOF */
