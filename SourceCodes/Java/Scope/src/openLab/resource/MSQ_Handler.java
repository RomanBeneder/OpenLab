/**
 * 
 */
package openLab.resource;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import openLab.message.packet.GenericMessage;

/**
 * @author Markus Lechner
 *
 */
public class MSQ_Handler {
	private static MSQ_Handler msqHandler = null;

	private ConcurrentLinkedQueue<GenericMessage> msqProcessReq = new ConcurrentLinkedQueue<GenericMessage>();
	private ConcurrentLinkedQueue<GenericMessage> msqUIReq = new ConcurrentLinkedQueue<GenericMessage>();
	
	private ConcurrentLinkedQueue<ArrayList<Integer>> rawSampleDataCH1 = new ConcurrentLinkedQueue<ArrayList<Integer>>();
	private ConcurrentLinkedQueue<ArrayList<Integer>> rawSampleDataCH2 = new ConcurrentLinkedQueue<ArrayList<Integer>>();
	
	private ConcurrentLinkedQueue<ObservableList<Data<Number, Number>>> sampleDataCH1 = new ConcurrentLinkedQueue<ObservableList<Data<Number, Number>>>();
	private ConcurrentLinkedQueue<ObservableList<Data<Number, Number>>> sampleDataCH2 = new ConcurrentLinkedQueue<ObservableList<Data<Number, Number>>>();
	
	/**
	 * @return
	 */
	public static MSQ_Handler getInstance() {
		if(msqHandler == null) {
			synchronized(MSQ_Handler.class)	{
				msqHandler = new MSQ_Handler();
			}
		}
		return msqHandler;
	}

	/**
	 * @return the msqRequest
	 */
	public ConcurrentLinkedQueue<GenericMessage> getMsqProcessReq() {
		return msqProcessReq;
	}

	/**
	 * @return the rawSampleDataCH1
	 */
	public synchronized ConcurrentLinkedQueue<ArrayList<Integer>> getRawSampleDataCH1() {
		return rawSampleDataCH1;
	}

	/**
	 * @return the rawSampleDataCH2
	 */
	public synchronized ConcurrentLinkedQueue<ArrayList<Integer>> getRawSampleDataCH2() {
		return rawSampleDataCH2;
	}

	/**
	 * @return the msqResponse
	 */
	public ConcurrentLinkedQueue<GenericMessage> getMsqUIReq() {
		return msqUIReq;
	}



	/**
	 * @return the sampleDataCH1
	 */
	public ConcurrentLinkedQueue<ObservableList<Data<Number, Number>>> getSampleDataCH1() {
		return sampleDataCH1;
	}

	/**
	 * @param sampleDataCH1 the sampleDataCH1 to set
	 */
	public void setSampleDataCH1(ConcurrentLinkedQueue<ObservableList<Data<Number, Number>>> sampleDataCH1) {
		this.sampleDataCH1 = sampleDataCH1;
	}

	/**
	 * @return the sampleDataCH2
	 */
	public ConcurrentLinkedQueue<ObservableList<Data<Number, Number>>> getSampleDataCH2() {
		return sampleDataCH2;
	}

	/**
	 * @param sampleDataCH2 the sampleDataCH2 to set
	 */
	public void setSampleDataCH2(ConcurrentLinkedQueue<ObservableList<Data<Number, Number>>> sampleDataCH2) {
		this.sampleDataCH2 = sampleDataCH2;
	}
}

/* EOF */