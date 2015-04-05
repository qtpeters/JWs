package com.qtpeters.jws.service.error;

public class ParseException extends HandshakeException {
	
	private static final long serialVersionUID = 5758402594239603280L;

	public ParseException( String msg ) {
		super( msg );
	}

	public ParseException( String msg, Throwable t ) {
		super( msg, t );
	}
}
