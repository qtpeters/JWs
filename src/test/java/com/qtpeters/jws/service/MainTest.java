package com.qtpeters.jws.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.qtpeters.jws.service.util.Data;

public class MainTest {
 
	@Test
	public void startTest() throws Exception {
		String [] args = { "44444" };
		Main main = new Main( args );
		
		Executors.newSingleThreadExecutor().execute( new Runnable() {
			public void run() {
				
				try {
					
					System.out.println( "Starting Client Thread" );
					Thread.sleep( 5000 );		
					System.out.println( "Executing Client Thread" );

					Socket client = new Socket( "127.0.0.1", 44444 );
					BufferedReader reader = new BufferedReader( 
							new InputStreamReader( 
									client.getInputStream() 
									) 
							);
					
					PrintWriter writer = new PrintWriter( 
							client.getOutputStream() 
							);
					
					char [] buffer = Data.getGoodHeader().toCharArray();
					writer.write( buffer, 0, buffer.length );
					
				client.close();
				} catch ( Exception e ) {
					e.printStackTrace();
				}
				
			}
		});
		
		System.out.println( "Starting JWs" );
		
		main.start();
	}
}
