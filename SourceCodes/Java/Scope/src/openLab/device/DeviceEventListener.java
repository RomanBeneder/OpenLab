/**
 *
 */
package openLab.device;

import java.util.EventListener;

import openLab.device.packets.ExtendedSampleData;
import openLab.device.packets.GenericPacket;
import openLab.device.packets.SampleDataPacket;

/**
 * Events they may be fired by a {@link Device}
 *
 * @author Harald Schloffer
 *
 */
public interface DeviceEventListener extends EventListener {
	/**
	 * Will be fired every time a new packet has been received.
	 *
	 * @param packet
	 *            Received packet. Check the command code to determine packet
	 *            type.
	 */
	public void handlePacketReceived(GenericPacket packet);

	/**
	 * Will be fired every time a new packet has been received.
	 *
	 * @param packet
	 *            Received packet. Check the command code to determine packet
	 *            type.
	 */
	public void handleExtendedSampleDataReceived(ExtendedSampleData packet);

	/**
	 * Will be fired every time a {@link SampleDataPacket} has been received.
	 *
	 * @param packet
	 *            Received {@link SampleDataPacket}
	 */
	public void handleSampleDataReceived(SampleDataPacket packet);

	/**
	 * Will be fired if the connection to the device has been lost.
	 */
	public void handleDisconnect();
}
