package com.ty.SimpleSocket.example;

import com.ty.SimpleSocket.RequestHandler;

public class EchoHandler implements RequestHandler {

	@Override
	public String getResponse(String request) {
		return request;
	}
}