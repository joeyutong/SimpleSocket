package com.ty.SimpleSocket;

import java.net.ServerSocket;
import java.io.IOException;

public class SimpleSocketServer {
	private ServerThread server;

	public void startServer(int port, RequestHandler requestHandler) throws IOException {
		server = new ServerThread(new ServerSocket(port), requestHandler);
		server.start();
		System.out.println("Server: The server has started");
	}

	public void stopServer() throws IOException {
		server.stopRunning();
		System.out.println("Server: The server has closed");
	}
}