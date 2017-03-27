package org.schroen.embsysscope.classes;

import javafx.scene.chart.NumberAxis;

public class ScopePlotChart {

	private NumberAxis xAxis;
	private ScopeChannel channel0;
	private ScopeChannel channel1;

	private double timeDIV=1;
	private double TimeAxis = 0;
	private double Steps=1;
	private double StepsPerSec=12800;
	private double SampTime=0.09;
	private boolean raverage = true;

	public ScopePlotChart(ScopeChannel channel0,ScopeChannel channel1, NumberAxis xAxis){

		this.xAxis=xAxis;
		this.channel0=channel0;
		this.channel1=channel1;	
	}
	public void nextTimeStep(){

		this.channel0.setXValue(this.TimeAxis);
		this.channel1.setXValue(this.TimeAxis);
		//		this.TimeAxis++;
		this.TimeAxis=this.TimeAxis+getSteps();
	}

	public void plotTimeStep() throws InterruptedException {

		if((xAxis.getUpperBound()-(TimeAxis))<0){
			TimeAxis=0;
		}
		if((channel0.getCHSeries().getData().size()>(StepsPerSec/getSteps())&&(channel0.getCHState()==true))){
				channel0.removeDataFromSeries(0);
		}
		if((channel1.getCHSeries().getData().size()>(StepsPerSec/getSteps())&&(channel1.getCHState()==true))){
				channel1.removeDataFromSeries(0);
		}
		
		
		if((channel0.getCHSeries().getData().size()>((StepsPerSec/getSteps())-1))&&(channel1.getCHSeries().getData().size()>((StepsPerSec/getSteps())-1))){
			this.raverage=false;
		}
		else if((channel0.getCHSeries().getData().size()>((StepsPerSec/getSteps())-1))&&(channel1.getCHState()==false)){
			this.raverage=false;
		}
		else if((channel1.getCHSeries().getData().size()>((StepsPerSec/getSteps())-1))&&(channel0.getCHState()==false)){
			this.raverage=false;
		}
		else{
			if(this.raverage==false){
				TimeAxis=0;
		}
				this.raverage=true;	
		}
			channel0.addDataToSeries(raverage);
			channel1.addDataToSeries(raverage);	
	}


	public double getTimeAxis() {
		return TimeAxis;
	}
	public double getTimeDIV() {
		return timeDIV;
	}
	public void setTimeDIV(double timeDIV) {
		this.timeDIV = timeDIV;
		this.setSteps((StepsPerSec/((this.timeDIV/SampTime)*10)));
		System.out.println(Steps);


	}
	public double getSteps() {
		return Steps;
	}
	public void setSteps(double steps) {
		Steps = steps;
		System.out.println(Steps);
		channel0.getCHSeries().getData().clear();
		channel1.getCHSeries().getData().clear();
	}
}
