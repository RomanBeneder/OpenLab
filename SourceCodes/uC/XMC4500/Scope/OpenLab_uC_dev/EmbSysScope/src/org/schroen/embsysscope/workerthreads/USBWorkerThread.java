package org.schroen.embsysscope.workerthreads;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.usb4java.BufferUtils;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceHandle;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;



public class USBWorkerThread extends Thread{

	private static final byte OUT_ENDPOINT = 0x04;
	private static final byte IN_ENDPOINT = (byte) 0x83;
	private static final long TIMEOUT = 0;

	private short productID=0x0058;
	private short vendorID=0x058b;

	private DeviceHandle handle = new DeviceHandle();
	private ByteBuffer	buffer; 

	public USBWorkerThread(int count) {

		int result = LibUsb.init(null);
		if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to initialize libusb.", result);

		setDaemon(true);
		setName("USBWorkerThread" + count);
	}

	@Override
	public void run() {


		int result = LibUsb.open(this.findDevice(), handle);

		if(result != LibUsb.SUCCESS) throw new LibUsbException("unable to open USB device", result);
		try{

			while (true) {            
				
				buffer = read(handle, 4);
				
				System.out.print(buffer);
				// Thread schlafen
				try {
					// fuer 1 micro sekunden
					sleep(TimeUnit.MICROSECONDS.toMicros(100));
				} catch (InterruptedException ex) {
					Logger.getLogger(USBWorkerThread.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		finally{
			LibUsb.close(handle);
		}

	}

	public void USBExit(){
		LibUsb.exit(null);
	}

	public Device findDevice()
	{
		// Read the USB device list
		DeviceList list = new DeviceList();
		int result = LibUsb.getDeviceList(null, list);
		if (result < 0) throw new LibUsbException("Unable to get device list", result);

		try
		{
			// Iterate over all devices and scan for the right one
			for (Device device: list)
			{
				DeviceDescriptor descriptor = new DeviceDescriptor();
				result = LibUsb.getDeviceDescriptor(device, descriptor);
				if (result != LibUsb.SUCCESS) throw new LibUsbException("Unable to read device descriptor", result);
				if (descriptor.idVendor() == this.vendorID && descriptor.idProduct() == this.productID)
					return device;
			}
		}
		finally
		{
			// Ensure the allocated device list is freed
			LibUsb.freeDeviceList(list, true);
		}
		// Device not found
		return null;
	}

	/**
	 * Writes some data to the device.
	 * 
	 * @param handle
	 *            The device handle.
	 * @param data
	 *            The data to send to the device.
	 */
	public static void write(DeviceHandle handle, byte[] data)
	{
		int result; 

		ByteBuffer buffer = BufferUtils.allocateByteBuffer(data.length);
		buffer.put(data);
		IntBuffer transferred = BufferUtils.allocateIntBuffer();
		result = LibUsb.bulkTransfer(handle, OUT_ENDPOINT, buffer, transferred, TIMEOUT);

		if (result != LibUsb.SUCCESS)
		{
			throw new LibUsbException("Unable to send data", result);
		}
		System.out.println(transferred.get() + " bytes sent to device");
	}

	/**
	 * Reads some data from the device.
	 * 
	 * @param handle
	 *            The device handle.
	 * @param size
	 *            The number of bytes to read from the device.
	 * @return The read data.
	 */
	public static ByteBuffer read(DeviceHandle handle, int size)
	{
		int result; 

		ByteBuffer buffer = BufferUtils.allocateByteBuffer(size).order(ByteOrder.LITTLE_ENDIAN);
		IntBuffer transferred = BufferUtils.allocateIntBuffer();
		result = LibUsb.bulkTransfer(handle, IN_ENDPOINT, buffer, transferred, TIMEOUT);

		if (result != LibUsb.SUCCESS)
		{
			throw new LibUsbException("Unable to read data", result);
		}
		System.out.println(transferred.get() + " bytes read from device");
		return buffer;
	}

	public short getProductID() {
		return productID;
	}
	public void setProductID(short productID) {
		this.productID = productID;
	}
	public short getVendorID() {
		return vendorID;
	}
	public void setVendorID(short vendorID) {
		this.vendorID = vendorID;
	}

}
