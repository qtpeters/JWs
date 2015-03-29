package com.qtpeters.jws.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Tools {

	private static Logger logger = LogManager.getLogger( Tools.class );
	
	public static int cast( final String arg, final int defaultValue  ) {
		int value = 0;
		
		try {
			value = Integer.valueOf( arg );
		} catch ( NumberFormatException nfe ) {
			value = defaultValue;
		}
		
		return value;
	}

	public static void closeSocket(Socket socket) {
		try {
			socket.close();
		} catch (IOException ioe) {
			logger.error( "Unable to close socket.", ioe );
		}
	}

	public static Socket accept( ServerSocket serverSocket ) {

		Socket socket = null;
		
		try {
			logger.info( "Before Accept" );
			socket = serverSocket.accept();
			logger.info( "After Accept" );
		} catch ( IOException e ) {
			logger.error( "Unable to accept incomming connection." );
		}
		
		return socket;
	}

	public static InputStream getInputStream(Socket socket) {
		
		InputStream is = null;
		
		try {
			is = socket.getInputStream();
		} catch ( IOException ioe ) {
			logger.error( "Unable to acquire inputstream" );
		}
		
		return is;
	}

	public static byte[] getPacket(InputStream is) {
		
		byte [] packet = null;
		
		try {
			packet = IOUtils.toByteArray( is );
		} catch ( IOException ioe ) {
			logger.error( "Unable to acquire inputstream" );
		}
		
		return packet;
	}

	public static void closeSockets( Socket socket, ServerSocket serverSocket ) {
		
		try {
			socket.close();
		} catch ( IOException ioe ) {
			logger.error( "Unable to close socket" );
		}
		
		try {
			serverSocket.close();
		} catch ( IOException ioe ) {
			logger.error( "Unable to close server socket" );
		}
	}

	public static void sleep( int timeout, TimeUnit unit ) {
		
		try {
			unit.sleep( timeout );
		} catch ( InterruptedException e ) {
			logger.warn( "Sleep was intruppted, I hate that." );
			e.printStackTrace();
		}
	}

	public static String getLine( BufferedReader br ) {
		
		String line = null;
		
		try {
			line = br.readLine();
		} catch ( IOException ioe ) {
			logger.error( "Unable to read line!" );
		}
		
		return line;
	}
}
