package inhumane.net;

import inhumane.Player;
import javafx.beans.property.BooleanProperty;

import java.util.ArrayList;

/**
 * Created by martin on 4/9/16.
 */
public class Server {

	ArrayList<Player> players;

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

	public static BooleanProperty getInterruptedProperty(){
		return DiscoveryThread.interrupted;
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
