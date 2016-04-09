package inhumane.net;

import java.io.IOException;
import java.net.*;

/**
 * Created by martin on 09-04-2016.
 *
 */


class DiscoveryThread implements Runnable {
	private DatagramSocket socket;
	private static int port = 8888;
	private volatile boolean isRunning;

	static void setPort(int port) {
		DiscoveryThread.port = port;
	}

	@Override
	public void run() {
		isRunning = true;
		System.out.println("Discover Start");
		try{
			//keep socket open
			socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			socket.setSoTimeout(5000);

			while(isRunning){
				System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");

				//Receive a packet
				byte[] receiveBuffer = new byte[15000];
				DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
				try {
					socket.receive(packet);
				} catch (SocketTimeoutException exception){
					continue;
				}

				//Packet received
				System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
				System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));

				//See if packet holds the right command (message)
				String message = new String(packet.getData()).trim();
				if(message.equals(Server.commands.DISCOVER_SERVER_REQUEST.toString())){
					byte[] sendData = Server.commands.DISCOVER_SERVER_RESPONSE.toString().getBytes();

					//Send a response
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
					socket.send(sendPacket);

					System.out.println(getClass().getName() + ">>>Sent packet to: " + sendPacket.getAddress().getHostAddress());
				}
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Discover Stop");
	}

	void kill(){
		isRunning = false;
	}

	static DiscoveryThread getInstance() {
		return DiscoveryThreadHolder.INSTANCE;
	}

	private static class DiscoveryThreadHolder {
		private static final DiscoveryThread INSTANCE = new DiscoveryThread();
	}

}