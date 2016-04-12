package inhumane.net;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by martin on 4/11/16.
 *
 */

class LobbyThread implements Runnable{
	private static int port = 8888;
	private volatile boolean running;
	private static volatile BooleanProperty interrupted = new SimpleBooleanProperty(false);

	private static LobbyThread ourInstance = new LobbyThread();
	public static LobbyThread getInstance() {
		return ourInstance;
	}

	@Override
	public void run() {

	}

	static void setPort(int port) {
		LobbyThread.port = port;
	}

	boolean isRunning(){
		return running;
	}

	void interrupt(){
		interrupted.set(true);
	}

	boolean isInterrupted() {
		return interrupted.get();
	}
}
