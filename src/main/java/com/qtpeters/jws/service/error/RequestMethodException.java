package com.qtpeters.jws.service.error;

public class RequestMethodException extends HandshakeException {
	private static final long serialVersionUID = 6238521713100987226L;
	public RequestMethodException( String msg ) {
		super( msg );
	}
}
