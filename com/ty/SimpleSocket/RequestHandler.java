package com.ty.SimpleSocket;

import java.io.PrintWriter;
import java.io.IOException;

public interface RequestHandler {

	String getResponse(String request);

	default void handle(PrintWriter out, String request) {
		try {
			if (out != null) {
				String response = getResponse(request);
				out.println(response);
				System.out.println("To client: " + response);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}