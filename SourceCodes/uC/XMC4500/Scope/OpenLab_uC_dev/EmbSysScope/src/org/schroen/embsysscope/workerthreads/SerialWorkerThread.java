package org.schroen.embsysscope.workerthreads;

import java.util.concurrent.BlockingQueue;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialWorkerThread implements Runnable{

	private String[]	portList;
	private String		portName=null;
	private String buffer;
	private SerialPort 	serialPort = new SerialPort(null);
	private final BlockingQueue<String> queue;
	private boolean connectionState=false;
	private boolean	threadstate = true;

	public SerialWorkerThread(BlockingQueue<String> queue, String port) {

		this.queue = queue; 
		this.portName = port;
	}

	@Override
	public void run() {

//		this.searchDevice();
				this.connDevice();
		while(threadstate){
			try {
				this.SerialRead();
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void createPortList() {
		this.portList = SerialPortList.getPortNames();
	}


	public void SerialRead(){

		try {
			buffer = this.serialPort.readHexString(2);
			if(buffer!= null){
				buffer=buffer.replaceAll(" ", "");
				try {
					this.queue.put(buffer);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  


	}

	public void connDevice(){

		Boolean  port=null;
		byte[] rdy = {'R','D','Y'};
		SerialPort serialPort = new SerialPort(portName);
		
		while(connectionState==false){

			try {
				port = serialPort.openPort();


				if(port==true){
					serialPort.setParams(	
							SerialPort.BAUDRATE_115200, 
							SerialPort.DATABITS_8, 
							SerialPort.STOPBITS_1, 
							SerialPort.PARITY_NONE);//Set params

					this.serialPort = serialPort;
					this.serialPort.purgePort(1|2);

					try {
						while (this.serialPort.readBytes()==null) {
							Thread.sleep(100);
							//							this.serialPort.writeString(RDY);
							this.serialPort.purgePort(1|2);
							this.serialPort.writeBytes(rdy);

						}
						System.out.println("connected to: "+ portName);
						connectionState=true;
						return;	
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					


				}
			} catch (SerialPortException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1);
			}//Open serial port

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public void CloseSerialPort() {
		try {
			serialPort.closePort();
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
	}

	public String getPortName() {
		return portName;
	}


	public void setPortName(String portName) {
		this.portName = portName;
	}


	public String[] getPortList() {
		return portList;
	}

	public boolean getConnectionState() {
		return connectionState;
	}

	public void setConnectionState(boolean connectionState) {
		this.connectionState = connectionState;
	}

	public boolean getThreadstatus() {
		return threadstate;
	}

	public void setThreadstatus(boolean threadstatus) {
		this.threadstate = threadstatus;
	}
}
