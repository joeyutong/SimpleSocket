package com.ty.SimpleSocket;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;

public class ConnectionThread extends Thread {
	private Socket socket;
	private RequestHandler requestHandler;
	private volatile boolean isRunning;
	private BufferedReader in;
	private PrintWriter out;

	public ConnectionThread(Socket socket, RequestHandler requestHandler) {
		this.socket = socket;
		this.requestHandler = requestHandler;

		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("Server: Connection Thread begins");
		isRunning = true;
		while (isRunning) {
			if (socket == null || socket.isClosed()) {
				isRunning = false;
				System.out.println("Server: isRunning = false");
				break;
			}

			try {
				if (in != null) {
					String readLine;
					while ((readLine = in.readLine()) != null) {
						System.out.println("From Client: " + readLine);
						requestHandler.handle(out, readLine);
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Server: Connection Thread ends");
	}

	public void stopRunning() throws IOException {
		System.out.println("Server: Stop Connection Thread");
		isRunning = false;
		if (in != null)
			in.close();

		if (out != null)
			out.close();

		if (socket != null && !socket.isClosed()) 
			socket.close();
	}
}