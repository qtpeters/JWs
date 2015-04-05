package com.qtpeters.jws.service.error;

public class HttpVersionException extends HandshakeException {
	private static final long serialVersionUID = -4775548942827592254L;
	public HttpVersionException( String msg ) {
		super(msg);
	}
}
