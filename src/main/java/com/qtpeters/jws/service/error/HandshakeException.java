package com.qtpeters.jws.service.error;

public class HandshakeException extends Exception {
	
	private static final long serialVersionUID = 5758402594239603280L;

	public HandshakeException( String msg ) {
		super( msg );
	}

	public HandshakeException( String msg, Throwable t ) {
		super( msg, t );
	}
}
