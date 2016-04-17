package inhumane.net;

import javafx.beans.property.BooleanProperty;

import java.net.DatagramPacket;

/**
 * Created by martin on 4/9/16.
 */
public class Server extends NetThing{

	@Override
	void handle(DatagramPacket packet) {
		System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));
		String message = new String(packet.getData()).trim();
		if(message.equals(Server.commands.DISCOVER_SERVER_REQUEST.toString())){
			byte[] sendData = Server.commands.DISCOVER_SERVER_RESPONSE.toString().getBytes();

			//Send a response
			//DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
			send(sendData, packet.getAddress());

			System.out.println(getClass().getName() + ">>>Sent packet to: " + packet.getAddress().getHostAddress());
		}
	}

	enum commands{
		DISCOVER_SERVER_REQUEST,
		DISCOVER_SERVER_RESPONSE,
		GAME_SERVER_REQUEST_JOIN, 			// A client request to join the game
		GAME_SERVER_RESPONSE_JOIN_ACCEPT,	// The request is accepted
		GAME_SERVER_RESPONSE_JOIN_DENY,		// Or the request is denied
		GAME_SERVER_MESSAGE_LEAVE,			// A client informs the server that they are leaving the game
		GAME_SERVER_MESSAGE_QUIT			// The server informs players that it is shutting down
	}

	public static boolean startDiscovery(int port){
		if(!isDiscovering()) {
			DiscoveryThread.setPort(port);
			Thread discoveryThread = new Thread(DiscoveryThread.getInstance());
			discoveryThread.start();
			return true;
		} else {
			System.out.println("Already discovery enabled!");
			return false;
		}
	}

	public static boolean isDiscovering(){
		return DiscoveryThread.getInstance().isRunning();
	}

	public BooleanProperty getInterruptedProperty(){
		return interupted;//DiscoveryThread.interrupted;
	}

	public static boolean stopDiscovery(){
		if(!DiscoveryThread.getInstance().isInterrupted()) {
			DiscoveryThread.getInstance().interrupt();
			return true;
		} else {
			return false;
		}
	}
}
