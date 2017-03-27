/**
 *
 */
package openLab.device.serial;

import java.util.ArrayList;

import openLab.device.DeviceEventListener;
import openLab.device.packets.ExtendedSampleData;
import openLab.device.packets.GenericPacket;
import openLab.device.packets.SampleDataPacket;
import openLab.resource.MSQ_Handler;
import openLab.resource.OpenLabDevice;

/**
 * @author Markus Lechner
 *
 */
public class HandleSampleData {
	private final int INITIAL_QUEUE_SIZE = 600;

	private int expectedSamplePacketsCH1 = 0;
	private int expectedSamplePacketsCH2 = 0;

	private long internalPacketNumCH1 = 1;
	private long internalPacketNumCH2 = 1;

	private Integer frequency;

	private ArrayList<Byte> tempSampleData = new ArrayList<Byte>(INITIAL_QUEUE_SIZE);
	private ArrayList<Byte> tempSampleDataCH1 = new ArrayList<Byte>(INITIAL_QUEUE_SIZE);
	private ArrayList<Byte> tempSampleDataCH2 = new ArrayList<Byte>(INITIAL_QUEUE_SIZE);

	private ArrayList<Integer> CH1 = new ArrayList<Integer>(INITIAL_QUEUE_SIZE);
	private ArrayList<Integer> CH2 = new ArrayList<Integer>(INITIAL_QUEUE_SIZE);

	//	MSQ_Handler msq_Handler = null;

	/**
	 * @param serialDevice
	 * @return
	 */
	public boolean initSamplePacketManagment(SerialDevice serialDevice) {
		if(serialDevice == null) {
			return false;
		}		

		//		msq_Handler = MSQ_Handler.getInstance();

		serialDevice.setEventListener(new DeviceEventListener() {
			@Override
			public void handleSampleDataReceived(SampleDataPacket packet){
				if (packet != null)	{
					tempSampleData = packet.getSampleData();

					if (tempSampleData != null) {
						if(packet.getChannelNum() == OpenLabDevice.CH1) {
							for (Byte b : tempSampleData) {
								if (b < 0) {
									CH1.add(new Integer(255 + (int) b));
								} else {
									CH1.add(new Integer(b));
								}
							}
							updateExtSampleDataQueue(OpenLabDevice.CH1, tempSampleData);
						} else if(packet.getChannelNum() == OpenLabDevice.CH2) {
							for (Byte b : tempSampleData) {
								if (b < 0) {
									CH2.add(new Integer(255 + (int) b));
								} else {
									CH2.add(new Integer(b));
								}
							}
							updateExtSampleDataQueue(OpenLabDevice.CH2, tempSampleData);
						} else {

						}
						CH1.clear();
						CH2.clear();
					}
				}
			}

			@Override
			public void handleExtendedSampleDataReceived(ExtendedSampleData packet) {
				if(packet != null)	{
					tempSampleData = packet.getSampleData();
					
					if(packet.getSampleData().isEmpty()) {
						System.err.println("Error: Device transmitts invalid sample data!");
						return;
					}					
					
					OpenLabDevice openLabDevice = OpenLabDevice.getInstance();					
					frequency = (int)packet.getSampleRate();

					if(packet.getPacketNumber() != 0) {
						if(packet.getChannelNum() == OpenLabDevice.CH1) {
							if(internalPacketNumCH1 == packet.getPacketNumber()) {
								if(packet.getPacketNumber() == 1) {									
									expectedSamplePacketsCH1 = (int)Math.ceil((openLabDevice.getMaxPacketSizeETS() / tempSampleData.size()));									
									tempSampleDataCH1.addAll(tempSampleData);
								} else {	
									int i = (int)internalPacketNumCH1;
									i--;

									for(int j=0;j<tempSampleData.size();i+=internalPacketNumCH1,j++) {
										tempSampleDataCH1.add(i, tempSampleData.get(j));
									}
								}

								if(expectedSamplePacketsCH1 == internalPacketNumCH1) {
									updateExtSampleDataQueue(OpenLabDevice.CH1,tempSampleDataCH1);
									internalPacketNumCH1 = 1;
									tempSampleDataCH1.clear();
								} else {
									internalPacketNumCH1++;
								}
							}

							//Faulty Packet was received - ETS reconstruction is impossible
							//Wait for the next packet and clean the previous sample data queues
							else {
								internalPacketNumCH1 = 1;
								tempSampleDataCH1.clear();
							}
						} else if(packet.getChannelNum() == OpenLabDevice.CH2) {
							if(internalPacketNumCH2 == packet.getPacketNumber()) {
								if(packet.getPacketNumber() == 1) {									
									expectedSamplePacketsCH2 = (int)Math.ceil((openLabDevice.getMaxPacketSizeETS()/tempSampleData.size()));
									tempSampleDataCH2.addAll(tempSampleData);
								} else {
									int i = (int)internalPacketNumCH2;
									i--;

									for(int j=0;j<tempSampleData.size();i+=internalPacketNumCH2,j++) {
										tempSampleDataCH2.add(i, tempSampleData.get(j));
									}
								}

								if(expectedSamplePacketsCH2 == internalPacketNumCH2) {
									updateExtSampleDataQueue(OpenLabDevice.CH2,tempSampleDataCH2);									
									internalPacketNumCH2 = 1;
									tempSampleDataCH2.clear();
								} else {
									internalPacketNumCH2++;
								}
							}

							//Faulty Packet was received - ETS reconstruction is impossible
							//Wait for the next packet and clean the previous sample data queues
							else {
								internalPacketNumCH2 = 1;
								tempSampleDataCH2.clear();
							}
						}
					}

					//Packet number is 0 -> RTS
					//Thus, the entire data packet is added to the data queue
					else {
						if(internalPacketNumCH1 != 1) {
							internalPacketNumCH1 = 1;
							tempSampleDataCH1.clear();
						}

						if(internalPacketNumCH2 != 1) {
							internalPacketNumCH2 = 1;
							tempSampleDataCH2.clear();
						}

						if(tempSampleData.size() > openLabDevice.getMaxPacketSizeRTS()) {
							while(tempSampleData.size() != openLabDevice.getMaxPacketSizeRTS()) {
								tempSampleData.remove((openLabDevice.getMaxPacketSizeRTS()-1));
							}							
						}						
						updateExtSampleDataQueue(packet.getChannelNum(),tempSampleData);
					}
				}
			}

			@Override
			public void handlePacketReceived(GenericPacket packet) {
				// TODO Auto-generated method stub

			}

			@Override
			public void handleDisconnect() {
				// TODO Auto-generated method stub

			}
		});
		return true;
	}


	private void updateExtSampleDataQueue(int channelNum, ArrayList<Byte> tempSampleData) {
		MSQ_Handler msq_Handler = MSQ_Handler.getInstance();	

		if(channelNum == OpenLabDevice.CH1) {
			msq_Handler.getRawSampleDataCH1().add(byteArrayToIntegerArray(tempSampleData));
		} else if(channelNum == OpenLabDevice.CH2) {
			msq_Handler.getRawSampleDataCH2().add(byteArrayToIntegerArray(tempSampleData));
		}
		return;
	}


	private ArrayList<Integer> byteArrayToIntegerArray(ArrayList<Byte> sampleData) {
		if(sampleData != null) {
			ArrayList<Integer> tempSampleData = new ArrayList<Integer>(sampleData.size());

			for (Byte b : sampleData) {
				if (b < 0) {
					tempSampleData.add(new Integer(255 + (int) b));
				} else {
					tempSampleData.add(new Integer((b)));
				}
			}

			OpenLabDevice openLabDevice = OpenLabDevice.getInstance();	
			
			if(openLabDevice.isHWFreqSupported()) {
				tempSampleData.add(frequency);
			}

			return tempSampleData;
		}
		return null;
	}
}

/* EOF */
