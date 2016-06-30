package com.ty.SimpleSocket;

import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

public class SimpleSocketClient {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public SimpleSocketClient(String host, int port) throws IOException {
		socket = new Socket(host, port);
		
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String readLine() {
		String result = "";
		try {
			if (in != null) {
				result = in.readLine();
				System.out.println("From server: " + result);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public void println(String line) {
		try {
			if (out != null) {
				out.println(line);
				System.out.println("To server: " + line);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void close() throws IOException {
		if (in != null)
			in.close();

		if (out != null)
			out.close();
		
		if (socket != null && !socket.isClosed())
			socket.close();
		System.out.println("Client: The Socket has closed");
	}
}