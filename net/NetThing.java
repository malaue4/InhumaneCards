package inhumane.net;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.net.*;

/**
 * Created by martin on 17-04-2016.
 */
public abstract class NetThing {
	InetAddress address;
	int port = 8888;
	volatile BooleanProperty listening = new SimpleBooleanProperty(false);
	volatile BooleanProperty interupted = new SimpleBooleanProperty(false);
	Thread listenThread;

	public void stopListening(){
		listening.set(false);
		interupted.set(true);
		listenThread = null;
	}

	public void startListening(){
		if(!listening.get()) {
			listenThread = new Thread(this::receive);
			listenThread.start();
		}
	}

	void receive(){
		//listen to port
		listening.set(true);
		try {
			//keep socket open
			DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			socket.setSoTimeout(1000);

			while(listening.get()) {
				//Receive a packet
				byte[] receiveBuffer = new byte[15000];
				DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);

				try {
					socket.receive(packet);
				} catch (SocketTimeoutException exception){
					continue;
				}

				handle(packet);
			}
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		interupted.set(false);
	}

	void send(byte[] data, InetAddress receiverAddress){
		// send stuff
		System.out.printf("Sending %s to %s%n", new String(data).trim(), receiverAddress.getHostName());
		try {
			DatagramSocket socket = new DatagramSocket();
			socket.setBroadcast(true);
			socket.setSoTimeout(500);
			DatagramPacket sendPacket = new DatagramPacket(data, data.length, receiverAddress, port);
			socket.send(sendPacket);
			socket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException exception){
			System.out.println("Send request timed out");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	abstract void handle(DatagramPacket packet);

	public boolean isListening() {
		return listening.get();
	}
}
