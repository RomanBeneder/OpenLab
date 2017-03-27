package org.schroen.embsysscope.workerthreads;

import org.schroen.embsysscope.classes.ScopePlotChart;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class DrawWorkerThread extends Task<Void> {

	private ScopePlotChart plotChannels;
	private DrawAnimation draw;
	
	public DrawWorkerThread(ScopePlotChart plotChannels){
		
		this.plotChannels = plotChannels;
		this.draw = new DrawAnimation(this.plotChannels);
	}

	@Override
	protected Void call() throws Exception {
		while(true){
			if(this.draw.isDrawState()==true)
				Platform.runLater(this.draw);
//			Thread.sleep(5);
			Thread.sleep(0, 1);
		}
	}

}