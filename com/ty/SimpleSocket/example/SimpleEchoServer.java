package com.ty.SimpleSocket.example;

import com.ty.SimpleSocket.*;
import java.io.IOException;

public class SimpleEchoServer {

	public static void main(String[] args) {
		String host = "localhost";
		int port = 8081;
		String[] inputs = "I came, I saw, I conquered".split(",");
		EchoHandler echo = new EchoHandler();
		
		try {
			SimpleSocketServer server = new SimpleSocketServer();
			server.startServer(port, echo);
			try {
				Thread.sleep(1000);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			SimpleSocketClient client = new SimpleSocketClient(host, port);

			for (String input : inputs) {
				client.println(input);
				client.readLine();
			}

			client.close();
			server.stopServer();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}