package com.qtpeters.jws.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qtpeters.jws.util.Tools;

public class Processor implements Runnable {

	private static Logger logger = LogManager.getLogger( Processor.class );
	private final Executor exe = Executors.newSingleThreadExecutor();
	
	private int port;
	private ServerSocket serverSocket;
	private Socket socket;
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
		while ( this.running ) {
			InputStream is = Tools.getInputStream( this.socket );
			if ( is != null ) {
				InputStreamReader isr = new InputStreamReader( is );
				BufferedReader br = new BufferedReader( isr );
				String line = "";
				while ( ( line = Tools.getLine( br) ) != null ) {
					if ( ! line.equals( "" ) ) {
						logger.info( "LINE: " + line );
					} else {
						logger.info( "Empty string found, finished" );
						System.exit( 0 );
					}
				}
//				byte [] packet = Tools.getPacket( is );
//				if ( packet != null ) {
//					logger.info( "PACKET RECEIVED: LENGTH: " + packet.length );
//					System.exit( 0 );
//				}
			}
		}
	}
}
