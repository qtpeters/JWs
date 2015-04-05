package com.qtpeters.jws.service.util;

public class Data {
	public static String getGoodHeader() {
		StringBuilder goodHeader = new StringBuilder();
		goodHeader.append( "GET /whatever HTTP/1.1\r\n" );
		goodHeader.append( "Host: example.com:8000\r\n" );
		goodHeader.append( "Upgrade: websocket\r\n" );
		goodHeader.append( "Connection: Upgrade\r\n" );
		goodHeader.append( "Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==\r\n" );
		goodHeader.append( "Sec-WebSocket-Version: 13\r\n" );
		
		return goodHeader.toString();
	}
}
