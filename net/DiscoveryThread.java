package inhumane.net;

import java.io.IOException;
import java.net.*;

/**
 * Created by martin on 09-04-2016.
 *
 */


class DiscoveryThread implements Runnable {
	private static int port = 8888;
	private volatile boolean running;
	private volatile boolean interrupted;

	static void setPort(int port) {
		DiscoveryThread.port = port;
	}

	@Override
	public void run() {
		running = true;
		interrupted = false;
		System.out.println("Discover Start");
		try{
			//keep socket open
			DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			socket.setSoTimeout(500);

			System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");
			while(!interrupted){

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

			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Discover Stop");
		running = false;
		interrupted = false;
	}

	boolean isRunning(){
		return running;
	}

	void interrupt(){
		interrupted = true;
	}

	static DiscoveryThread getInstance() {
		return DiscoveryThreadHolder.INSTANCE;
	}

	public boolean isInterrupted() {
		return interrupted;
	}

	private static class DiscoveryThreadHolder {
		private static final DiscoveryThread INSTANCE = new DiscoveryThread();
	}

}