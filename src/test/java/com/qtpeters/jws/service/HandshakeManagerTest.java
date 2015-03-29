package com.qtpeters.jws.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.qtpeters.jws.service.HandshakeManager.RequestLineToken;
import com.qtpeters.jws.service.error.ParseException;

public class HandshakeManagerTest {

	protected BufferedReader reader;
	protected HandshakeManager hm;
	
	protected String getGoodHeader() {
		StringBuilder goodHeader = new StringBuilder();
		goodHeader.append( "GET /whatever HTTP/1.1\r\n" );
		goodHeader.append( "Host: example.com:8000\r\n" );
		goodHeader.append( "Upgrade: websocket\r\n" );
		goodHeader.append( "Connection: Upgrade\r\n" );
		goodHeader.append( "Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==\r\n" );
		goodHeader.append( "Sec-WebSocket-Version: 13\r\n" );
		
		return goodHeader.toString();
	}
	
	@Before
	public void setUp() {
		InputStream is = new ByteArrayInputStream( getGoodHeader().getBytes() );
		InputStreamReader isr = new InputStreamReader( is );
		this.reader = new BufferedReader( isr ); 
		this.hm = new HandshakeManager( this.reader, null );
	}
	
	@Test
	public void getRequestLineTest() throws ParseException {
		Map<RequestLineToken, String> reqLineMap = hm.getRequestLine();
		assertEquals( "GET", reqLineMap.get( RequestLineToken.METHOD ) );
		assertEquals( "/whatever", reqLineMap.get( RequestLineToken.REQ_URI ) );
		assertEquals( "HTTP/1.1", reqLineMap.get( RequestLineToken.HTTP_VERSION ) );
	}
	
	@Test
	public void getRequestHeadersTest() {
		Map<String, String> requestHeaders = hm.getRequestHeaders();
		
		String msg = "%s header is incorrect";
		
		assertEquals( "Not the right number of headers", requestHeaders.size(), 5 );
		assertTrue( String.format( msg, "Host" ), requestHeaders.get( "Host" ).equals( "example.com:8000" ) );
		assertTrue( String.format( msg, "Upgrade" ), requestHeaders.get( "Upgrade" ).equals( "websocket" ) );
		assertTrue( String.format( msg, "Connection" ), requestHeaders.get( "Connection" ).equals( "Upgrade" ) );
		assertTrue( String.format( msg, "Sec-WebSocket-Key" ), requestHeaders.get( "Sec-WebSocket-Key" ).equals( "dGhlIHNhbXBsZSBub25jZQ==" ) );
		assertTrue( String.format( msg, "Sec-WebSocket-Version" ), requestHeaders.get( "Sec-WebSocket-Version" ).equals( "13" ) );
	}
	
	@After
	public void tearDown() throws Exception {
		this.reader.close();
	}
}
