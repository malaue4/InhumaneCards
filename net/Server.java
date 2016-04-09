package inhumane.net;

/**
 * Created by martin on 4/9/16.
 */
public class Server {
	boolean isDiscovering = false;

	enum commands{
		DISCOVER_SERVER_REQUEST,
		DISCOVER_SERVER_RESPONSE,
		GAME_SERVER_REQUEST_JOIN, 			// A client request to join the game
		GAME_SERVER_RESPONSE_JOIN_ACCEPT,	// The request is accepted
		GAME_SERVER_RESPONSE_JOIN_DENY,		// Or the request is denied
		GAME_SERVER_MESSAGE_LEAVE,			// A client informs the server that they are leaving the game
		GAME_SERVER_MESSAGE_QUIT			// The server informs players that it is shutting down
	}

	public void startDiscovery(int port){
		if(!isDiscovering) {
			isDiscovering = true;
			DiscoveryThread.setPort(port);
			Thread discoveryThread = new Thread(DiscoveryThread.getInstance());
			discoveryThread.start();
		} else {
			System.out.println("Already discovery enabled!");
		}
	}

	public void stopDiscovery(){
		DiscoveryThread.getInstance().kill();
		isDiscovering = false;
	}
}
