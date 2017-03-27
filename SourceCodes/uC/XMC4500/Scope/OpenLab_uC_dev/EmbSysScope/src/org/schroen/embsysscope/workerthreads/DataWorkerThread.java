package org.schroen.embsysscope.workerthreads;

import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.CopyOnWriteArrayList;

public class DataWorkerThread implements Runnable {

	private final BlockingQueue<String> SerialQueue;
	private final BlockingQueue<Integer> queueCh0;
	private final BlockingQueue<Integer> queueCh1;

	private final int Ch0Mask = 0x8002;		/* channel 0 */		
	private final int Ch1Mask = 0xC003;		/* channel 1 */
	private final int UnMask = 0x3FFC;		/* Unmask channels to extract data */
	private double falsMsg=0;
	private String rec=null;
	private boolean	threadstate = true;
	
	public DataWorkerThread(BlockingQueue<String> squeue, BlockingQueue<Integer> queueCh0,BlockingQueue<Integer> queueCh1){
		this.SerialQueue = squeue; 
		this.queueCh0 = queueCh0; 
		this.queueCh1 = queueCh1; 

	}

	@Override
	public void run() {
		while(threadstate){
			try {
				rec = SerialQueue.take();
				if(rec!=null){
					int value = Integer.parseInt(rec,16);
					this.SerialQueue.clear();
					splitData(value);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void splitData(int value){


		if((value&Ch1Mask)==Ch1Mask){
			value=((value&UnMask)>>2);
			try {
				this.queueCh1.put(value);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//			System.out.println("Channel: 1");
		}
		else if((value&Ch0Mask)==Ch0Mask){
			value=((value&UnMask)>>2);
			try {
				this.queueCh0.put(value);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			falsMsg++;
			System.out.println("FALSE MESSAGES: " + falsMsg);
		}
	}
	public boolean getThreadstatus() {
		return threadstate;
	}

	public void setThreadstatus(boolean threadstatus) {
		this.threadstate = threadstatus;
	}

}

















