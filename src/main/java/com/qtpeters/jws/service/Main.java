package com.qtpeters.jws.service;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qtpeters.jws.util.Tools;

public class Main {

	private static final Logger logger = LogManager.getLogger( Main.class ); 
	private static final int DEFAULT_PORT = 42069;
	
	private boolean running;
	private int port;
	private Processor processor;
	
	private void setShutdownThread( final Main m ) {
		Runtime.getRuntime().addShutdownHook( new Thread() {
			@Override public void run() {
				logger.info( "Shutting down JWs!" );
				m.shutdown();
			}
		});
	}
	
	public Main( final String [] args ) {
		this.port = args.length > 0 
		? Tools.cast( args[0], DEFAULT_PORT ) 
		: DEFAULT_PORT;
	}
	
	public void start() {
		logger.info( "Initalizing JWs" );
		this.processor = new Processor( port );
		this.processor.startup();
		this.running = true;
		setShutdownThread( this );
		
		while ( this.running ) {
			Tools.sleep( 15, TimeUnit.SECONDS );
		}
	}
	
	public void shutdown() {
		this.running = false;
		this.processor.shutdown();
	}
}
