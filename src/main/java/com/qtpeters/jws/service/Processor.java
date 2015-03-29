package com.qtpeters.jws.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qtpeters.jws.util.Tools;

public class Processor implements Runnable {

	private static Logger logger = LogManager.getLogger( Processor.class );
	private final Executor exe = Executors.newSingleThreadExecutor();
	
	private ServerSocket serverSocket;
	private Socket socket;
	
	private int port;
	private boolean running;
	
	public Processor( int port ) {
		logger.info( "Creating Processor" );
		this.port = port;
	}
	
	public void startup() {
		try {
			logger.info( "Processor starting up" );
			this.serverSocket = new ServerSocket( port );
			exe.execute( this );
		} catch ( IOException e ) {
			logger.fatal( "Unable to initialize server socket." );
		}
	}
	
	public void shutdown() {
		logger.info( "Processor shutting down" );
		this.running = false;
		Tools.closeSockets( this.socket, this.serverSocket );
	}
	
	public void run() {
		
		logger.info( "Processor running" );
		this.socket = Tools.accept( serverSocket );
		this.running = true;
		
		BufferedReader reader = Tools.getReader( this.socket );
		PrintWriter writer = Tools.getWriter( this.socket );
		
		while ( this.running ) {
//				byte [] packet = Tools.getPacket( is );
//				if ( packet != null ) {
//					logger.info( "PACKET RECEIVED: LENGTH: " + packet.length );
//					System.exit( 0 );
//				}
		}
	}
}
