package com.qtpeters.jws.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qtpeters.jws.util.Tools;

public class Main {

	private static final Logger logger = LogManager.getLogger( Main.class ); 
	private static final int DEFAULT_PORT = 42069;
	
	private int port;
	private ServerSocket serverSocket;
	private Socket socket;
	
	public Main( final String [] args ) {
		if ( args.length > 0 ) {
			this.port = Tools.cast( args[0], DEFAULT_PORT );
		} else {			
			this.port = DEFAULT_PORT;
		}
	}
	
	public void init() {
		
		logger.info( "Initalizing JWs" );
		
		try {
			this.serverSocket = new ServerSocket( port );
			this.socket = serverSocket.accept(); // New Thread here
			InputStream is = this.socket.getInputStream();
		} catch ( IOException e ) {
			logger.fatal( "Unable to initialize server socket, exiting..." );
			System.exit( 1 );
		}
	}
}
