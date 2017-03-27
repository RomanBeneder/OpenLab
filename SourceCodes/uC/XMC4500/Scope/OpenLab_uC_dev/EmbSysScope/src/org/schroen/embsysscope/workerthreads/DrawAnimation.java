package org.schroen.embsysscope.workerthreads;

import org.schroen.embsysscope.classes.ScopePlotChart;

public class DrawAnimation implements Runnable {

	private ScopePlotChart plotChannels;
	private boolean drawState=true;

	public DrawAnimation(ScopePlotChart plotChannel){

		this.plotChannels = plotChannel;

	}

	@Override
	public void run() {
		try {
				setDrawState(false);
				//				while(cnt!=10){
				this.plotChannels.nextTimeStep();
				this.plotChannels.plotTimeStep();
//				}
				setDrawState(true);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public boolean isDrawState() {
		return drawState;
	}

	private void setDrawState(boolean drawState) {
		this.drawState = drawState;
	}
	

	
	
	
	
//	public void destroyAnimation(){
//		
//		this.animation.stop();
//		this.animation = null;
//		
//	}
//	
//	public void createAnimation(){
//
//		this.animation = new Timeline();
//		this.animation.getKeyFrames().
//		add(new KeyFrame(Duration.millis(duration), (ActionEvent actionEvent) -> {
//
//			try {
//				this.plotChannel.nextTimeStep();
//				this.plotChannel.plotTimeStep();
//				
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//
//			}
//		}));
//
////		this.animation.setCycleCount(2100000);
//		this.animation.setCycleCount(Animation.INDEFINITE);
//		this.animation.play();
//		System.out.println(this.animation.getKeyFrames().size());
//	}
}
