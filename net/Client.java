package inhumane.net;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * Created by martin on 4/9/16.
 */
public class Client {
	InetAddress serverIp;
	int port = 8888;


	void discoverServer() {
		try {
			//Open a random port to send package
			DatagramSocket socket = new DatagramSocket();
			socket.setBroadcast(true);

			byte[] sendData = Server.commands.DISCOVER_SERVER_REQUEST.toString().getBytes();

			//Try the 255.255.255.255 first
			try {
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), port);
				socket.send(sendPacket);
				System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//Broadcast the message over all the network interfaces
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();

				if (networkInterface.isLoopback() || !networkInterface.isUp()) {
					continue; // Don't want to broadcast to the loopback interface
				}

				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
					InetAddress broadcast = interfaceAddress.getBroadcast();
					if (broadcast == null) {
						continue;
					}

					//Send the broadcast package!
					try {
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
						socket.send(sendPacket);
					} catch (IOException e) {
						e.printStackTrace();
					}

					System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
				}
			}

			System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");

			//Wait for a response
			byte[] receiveBuffer = new byte[15000];
			DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			socket.receive(receivePacket);

			//We have a response
			System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());

			//Check if the message is correct
			String message = new String(receivePacket.getData()).trim();
			if (message.equals(Server.commands.DISCOVER_SERVER_RESPONSE)) {
				//DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller
				setServerIp(receivePacket.getAddress());
			}

			//Close the port!
			socket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setServerIp(InetAddress serverIp) {
		this.serverIp = serverIp;
	}
}
