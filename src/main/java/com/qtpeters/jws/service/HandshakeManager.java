package com.qtpeters.jws.service;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qtpeters.jws.service.error.HandshakeException;
import com.qtpeters.jws.service.error.HttpVersionException;
import com.qtpeters.jws.service.error.ParseException;
import com.qtpeters.jws.service.error.RequestMethodException;
import com.qtpeters.jws.util.Tools;

// TODO Need to make this an impl
// TODO Need to create an interface for the manager
// TODO Need to move the handshakw manager and tests to
//      their own package to preserve privacy
public class HandshakeManager {

	private static Logger logger = LogManager.getLogger( HandshakeManager.class ); 
	
	enum RequestLineToken {
		METHOD, REQ_URI, HTTP_VERSION;
	}
	
	private final BufferedReader reader;
	private final PrintWriter writer;
	
	public HandshakeManager( BufferedReader reader, PrintWriter writer ) {
		this.reader = reader;
		this.writer = writer;
	}
	
	public void execute() throws HandshakeException {
		
		Map<RequestLineToken, String> requestLineMap = getRequestLine();
		validateRequest( requestLineMap );
		
		// TODO Do something with this URI
		String uri = requestLineMap.get( RequestLineToken.REQ_URI );
		
		Map<String, String> requestHeaders = getRequestHeaders();
		validateHeaders( requestHeaders );
		
		// TODO Process headers
	}
	
	private void validateRequest( Map<RequestLineToken, String> requestLineMap ) 
			throws HandshakeException {
		
		String method = requestLineMap.get( RequestLineToken.METHOD ); 
		if ( ! method.equalsIgnoreCase( "GET" ) ) {
			throw new RequestMethodException( "Request must be GET." );
		};
		
		float version = Float.valueOf( 
			requestLineMap.get( RequestLineToken.HTTP_VERSION ) 
		);
		
		if ( ! ( version >= 1.1f ) ) {
			throw new HttpVersionException( "HTTP version MUST be at least 1.1" );
		};
		
	}
	
	private void validateHeaders( final Map<String, String> headers ) 
			throws HandshakeException {
		
	}
	
	Map<RequestLineToken, String> getRequestLine() throws HandshakeException 	{
		
		Map<RequestLineToken, String> reqLineParts = new HashMap<>();
		String requestLine = Tools.getLine( this.reader );
		if ( requestLine != null ) {
			String [] rlParts = requestLine.split( " " );
			
			if ( rlParts.length != 3 ) {
				throw new ParseException( "Request header incorrect. Got: " + requestLine );
			}
			
			reqLineParts.put( RequestLineToken.METHOD, rlParts[0] );
			reqLineParts.put( RequestLineToken.REQ_URI, rlParts[1] );
			reqLineParts.put( RequestLineToken.HTTP_VERSION, rlParts[2] );
		}
		
		return reqLineParts;
	}
	
	Map<String, String> getRequestHeaders() {
		
		Map<String, String> headers = new HashMap<>();
		String line = "";
		while ( ( line = Tools.getLine( this.reader ) ) != null ) {
			if ( line.matches( "^(\\w+-?)+:\\s+.*$" ) ) {
				String [] headerParts = line.split( ":\\s+" );
				headers.put( headerParts[0], headerParts[1] );
			} else {
				logger.trace( "Non matching header: " + line );
			}
		}
		
		return headers;
	}
}
