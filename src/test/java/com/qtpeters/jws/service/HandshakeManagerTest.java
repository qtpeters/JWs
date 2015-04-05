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
import com.qtpeters.jws.service.error.HandshakeException;
import com.qtpeters.jws.service.util.Data;

public class HandshakeManagerTest {

	protected BufferedReader reader;
	protected HandshakeManager hm;
	
	@Before
	public void setUp() {
		InputStream is = new ByteArrayInputStream( Data.getGoodHeader().getBytes() );
		InputStreamReader isr = new InputStreamReader( is );
		this.reader = new BufferedReader( isr ); 
		this.hm = new HandshakeManager( this.reader, null );
	}
	
	@Test
	public void getRequestLineTest() throws HandshakeException {
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
