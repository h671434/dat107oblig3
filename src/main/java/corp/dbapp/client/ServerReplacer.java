package corp.dbapp.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Closes the previous running instance of the program on startup. Used during
 * development, because I forget the program during testing.
 * 
 * @author Aleksander XXVI
 *
 */
public class ServerReplacer extends Thread {

	private int port;

	public ServerReplacer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		searchOlderInstances();

		System.out.println("Server starting...");
		while (true)
			runServer();
	}

	private void searchOlderInstances() {
		System.out.println("Attempting to find older instance...");
		try (Socket socket = new Socket("127.0.0.1", port)) {
			logOtherInstanceFound(socket.isConnected());
		} catch (Exception e) {
			logOtherInstanceFound(false);
		}
	}

	private static void logOtherInstanceFound(boolean otherInstanceFound) {
		System.out.println(otherInstanceFound ? "FOUND ANOTHER INSTANCE RUNNING! It has been signaled to shut down."
				: "No older instance found.");
	}

	private void runServer() {
		try (ServerSocket serverSocket = new ServerSocket(port, 1)) {
			System.out.println("Server started!");
			try (Socket clientSocket = serverSocket.accept()) {
				System.out.println("Signal to shutdown received. Shutting down.");
				System.exit(0);
			}
		} catch (IOException e) {
			System.out.println("The other application is still shutting down...");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
			}
		}
	}

}
