package com.ty.SimpleSocket;

import java.net.ServerSocket;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class ServerThread extends Thread {
	private ServerSocket serverSocket;
	private volatile boolean isListening;
	private RequestHandler requestHandler;
	private List<ConnectionThread> connections = new ArrayList<>();

	public ServerThread(ServerSocket serverSocket, RequestHandler requestHandler) {
		this.serverSocket = serverSocket;
		this.requestHandler = requestHandler;
	}

	@Override
	public void run() {
		System.out.println("Server: Server Thread begins");
		isListening = true;
		while (isListening) {
			if (serverSocket == null || serverSocket.isClosed()) {
				isListening = false;
				break;
			}

			try {
				ConnectionThread connection = new ConnectionThread(serverSocket.accept(), requestHandler);
				connection.start();	
				System.out.println("Server: Socket accepted");
				connections.add(connection);
			} catch (IOException ex) {
				// Close the serverSocket
				System.out.println("Server: The ServerSocket has closed");			
			}	
		}
		System.out.println("Server: Server Thread ends");
	}

	// TODO: Synchronize
	public void stopRunning() throws IOException {
		System.out.println("Server: Stop Server Thread");
		for (ConnectionThread connection : connections) 
			connection.stopRunning();
		isListening = false;
		connections.clear();

		if (serverSocket != null && !serverSocket.isClosed()) 
			serverSocket.close();
	}
}