/*

+ * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schroen.embsysscope.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.schroen.embsysscope.classes.ScopeChannel;
import org.schroen.embsysscope.classes.ScopePlotChart;
import org.schroen.embsysscope.workerthreads.DataWorkerThread;
import org.schroen.embsysscope.workerthreads.DrawWorkerThread;
import org.schroen.embsysscope.workerthreads.SerialWorkerThread;
import org.schroen.embsysscope.workerthreads.USBWorkerThread;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import jssc.SerialPortList;

/**
 * FXML Controller class
 *
 * @author embsys
 */
public class Controller implements Initializable {
	@FXML private MenuItem MItemClose;
	@FXML private MenuItem MItemAbout;
	@FXML private Slider TDivSlider;
	@FXML private Pane logo_pane;
	@FXML private ToggleButton CH0ToggleBtn;
	@FXML private Button combtn;
	@FXML private Slider CH0AmpSlider;
	@FXML private Slider CH0OffSlider;
	@FXML private ToggleButton CH1ToggleBtn;
	@FXML private Slider CH1AmpSlider;
	@FXML private Slider CH1OffSlider;
	@FXML private GridPane pane_r_top;
	@FXML private TextField txtField;
	@FXML private Label LmsDiv;
	@FXML private Label LVCh0;
	@FXML private Label LVCh1;
	@FXML private Label LVCh2;
	@FXML private BorderPane BdPane;
	@FXML private ComboBox<String> comBox;

	private NumberAxis xAxis;
	private NumberAxis yAxis;
	private ScopeChannel channel0;
	private ScopeChannel channel1;
	private ScopePlotChart plotChannel;
	private LineChart<Number, Number> chart;
	private boolean connState=false;
	private String prevTxtField;
	private String[] portList;
	private String port="default";

	BlockingQueue<String> squeue 	=  new LinkedBlockingQueue<String>();
	BlockingQueue<Integer> queueCh0 =  new LinkedBlockingQueue<Integer>();
	BlockingQueue<Integer> queueCh1 =  new LinkedBlockingQueue<Integer>();
	ObservableList<String> boxList	=  FXCollections.observableArrayList();

	public static SerialWorkerThread serial;
	public static USBWorkerThread usb;
	public static DataWorkerThread data;
	public static DrawWorkerThread draw;
	public static Thread t1;
	public static Thread t2;
	public static Thread t3;

	/**
	 * Initializes the controller class.
	 */

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		this.createChart();
		
		data = new DataWorkerThread(squeue, queueCh0, queueCh1);
		t2 = new Thread(data);
		t2.start();
		
		draw = new DrawWorkerThread(plotChannel);
		t3 = new Thread(draw);
		t3.start();

		comBox.getItems().add("update ports");
		
	}    

	@FXML
	private void comboBox(ActionEvent event) {

		port = comBox.getValue().toString();

		portList = SerialPortList.getPortNames();
		comBox.getItems().addAll(portList);

		System.out.println(port);
	}

	@FXML
	private void ConnectionButton(ActionEvent event) {

//		if((connState==false)){
		if((connState==false)&&(port!="default")){
			serial = new SerialWorkerThread(squeue, this.port);
			t1 = new Thread(serial);
			t1.start();

		}
	}

	@FXML
	private void TimeDivTxtField(ActionEvent event) {

		String value = txtField.getText();
		int erg = Controller.isNumeric(value);

		if(erg>0){
			TDivSlider.setValue(Integer.valueOf(value));
			LmsDiv.setText(Integer.toString(erg)+" ms/DIV");
			prevTxtField=value;
			plotChannel.setTimeDIV(erg);	
		}
		else{
			txtField.setText(prevTxtField);
		}	
	}

	@FXML
	private void TimeDivSlider(MouseEvent event) {

		int value = (int) TDivSlider.getValue();
		LmsDiv.setText(Integer.toString(value)+" ms/DIV");
		txtField.setText(String.valueOf(value));
		prevTxtField=String.valueOf(value);
		plotChannel.setTimeDIV(value);
	}

	@FXML
	private void Channel0ToggleButton(ActionEvent event) {
		if(connState==true){
			Paint colour = CH0ToggleBtn.getTextFill();
			if(colour==Color.web("RED")){
				CH0ToggleBtn.setText("RUN");
				CH0ToggleBtn.setTextFill(Color.web("GREEN"));
				channel0.setCHState(false);
			}
			else{
				CH0ToggleBtn.setText("STOP");
				CH0ToggleBtn.setTextFill(Color.web("RED"));
				channel0.setCHState(true);
				System.gc();
			}
		}
		else if(serial.getConnectionState()==true){
			connState=true;
			CH0ToggleBtn.setText("STOP");
			CH0ToggleBtn.setTextFill(Color.web("RED"));
			channel0.setCHState(true);		
		}
	}

	@FXML
	private void Channel0AmplitudeSlider(MouseEvent event) {
		int value = (int) CH0AmpSlider.getValue();
		channel0.setVoltDIV(value);
		LVCh0.setText(Integer.toString(value)+" V/DIV");

	}

	@FXML
	private void Channel0OffsetSlider(MouseEvent event) {

		double value = CH0OffSlider.getValue();
		channel0.setOffset(value);
	}

	@FXML
	private void Channel1ToggleButton(ActionEvent event) {

		if(connState==true){
			Paint colour = CH1ToggleBtn.getTextFill();
			if(colour==Color.web("RED")){
				CH1ToggleBtn.setText("RUN");
				CH1ToggleBtn.setTextFill(Color.web("GREEN"));
				channel1.setCHState(false);
			}
			else{
				CH1ToggleBtn.setText("STOP");
				CH1ToggleBtn.setTextFill(Color.web("RED"));
				channel1.setCHState(true);

			}
		}
		else if(serial.getConnectionState()==true){
			connState=true;
			CH1ToggleBtn.setText("STOP");
			CH1ToggleBtn.setTextFill(Color.web("RED"));
			channel1.setCHState(true);
		}
	}

	@FXML
	private void Channel1AmplitudeSlider(MouseEvent event) {
		int value = (int) CH1AmpSlider.getValue();
		channel1.setVoltDIV(value);
		LVCh1.setText(Integer.toString(value)+" V/DIV");
	}

	@FXML
	private void Channel1OffsetSlider(MouseEvent event) {
		double value = CH1OffSlider.getValue();
		channel1.setOffset(value);
	}

	public void createChart(){

		this.xAxis = new NumberAxis(0, 12800, 1280);
		this.yAxis = new NumberAxis(-5, 5, 1);
		this.chart = new LineChart<>(this.xAxis, this.yAxis);
		this.xAxis.setTickLabelsVisible(false);
		this.yAxis.setTickLabelsVisible(false);
		this.yAxis.setAutoRanging(false);
		this.chart.setCreateSymbols(false);
		this.chart.setAnimated(false);
		this.chart.setLegendVisible(false);
		this.xAxis.setForceZeroInRange(false);
		this.chart.setVerticalGridLinesVisible(true);
		this.chart.setHorizontalGridLinesVisible(true);
		this.channel0 = new ScopeChannel(queueCh0); 
		this.channel1 = new ScopeChannel(queueCh1); 
		this.channel0.getCHSeries().setName("Channel 0");
		this.channel1.getCHSeries().setName("Channel 1");
		this.chart.getData().add(channel0.getCHSeries());
		this.chart.getData().add(channel1.getCHSeries());
		BdPane.setCenter(chart);	

		this.plotChannel = new ScopePlotChart(channel0, channel1, xAxis);
	}

	public static int isNumeric(String value) {
		try {
			int number = Integer.parseInt(value);
			return number; 
		}
		catch(NumberFormatException e) {
			return -1;
		}
	}
}