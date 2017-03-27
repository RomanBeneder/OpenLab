package org.schroen.embsysscope.classes;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.scene.chart.XYChart;
//import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class ScopeChannel{

	BlockingQueue<Integer> queue =  new LinkedBlockingQueue<Integer>(); /* Queue for data receive */
	private Series<Number, Number> CHSeries;			/* chart graph */
	private double XValue=1;										/* x-value of graph */
	private double YValue=1;										/* y-value of graph */
	private boolean CHState=false;							/* state of channel (ON/OFF) */
	private double CHVoltage = 3.3;							/* allowed channel voltage (XMC4500 3V3) */
	private double ADCresolution = 12;						/* ADC Resolution in BIT */
	private double offset=0;										/* function offset */
	private int voltDIV=1;												/* amplitude multiplicator */
	private double average =0;
	private boolean raverage = true;
	
	public ScopeChannel(BlockingQueue<Integer> queue){

		this.queue=queue;
		this.CHSeries=new XYChart.Series<>();

	}
	/**
	 * removes data form series data list on index
	 *
	 * @param index the index of the element to be removed
	 */
	public void removeDataFromSeries(int index){
		this.CHSeries.getData().remove(index);
	}

	/**
	 * add data to series data list 
	 *
	 * 	@param e element to be appended to this list
	 */
	public void addDataToSeries(boolean raverage){
		
		this.raverage = raverage;
		
		if(this.CHState==true){
			
			if(this.raverage==false){
				this.getYValue();
			}
			else{
//			this.CHSeries.getData().add(new XYChart.Data<Number, Number>(this.XValue, Math.sin((2*Math.PI/360)*XValue)));
			this.CHSeries.getData().add(new XYChart.Data<Number, Number>(this.XValue, this.getYValue()));
			}
//			this.queue.clear();
		}
		else{
			this.queue.clear();
			this.CHSeries.getData().clear();

		}
	}
	public Series<Number, Number> getCHSeries() {
		return CHSeries;
	}

	public void setXValue(double xValue) {
		XValue = xValue;
	}

	public double getXValue() {
		return XValue;
	}

	private double getYValue(){
		setYValue();
		return this.YValue;
	}

	private void setYValue() {

		try {
			this.YValue = this.queue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.YValue = (this.YValue*CHVoltage/((Math.pow(2, ADCresolution)-1))+this.offset)/voltDIV;
		this.average = this.average+this.YValue;
	}


	public double getCHVoltage() {
		return CHVoltage;
	}


	public void setCHVoltage(double cHVoltage) {
		CHVoltage = cHVoltage;
	}


	public double getADCresolution() {
		return ADCresolution;
	}


	public void setADCresolution(double aDCresolution) {
		ADCresolution = aDCresolution;
	}


	public boolean getCHState() {
		return CHState;
	}


	public void setCHState(boolean cHState) {
		CHState = cHState;
	}
	
	public void setOffset(double off) {
		this.offset=(off/10*voltDIV);
		CHSeries.getData().clear();
	}
	public int getVoltDIV() {
		return voltDIV;
	}
	public void setVoltDIV(int voltDIV) {
		this.voltDIV = voltDIV;
	}
}
