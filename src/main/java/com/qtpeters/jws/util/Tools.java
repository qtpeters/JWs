package com.qtpeters.jws.util;

public class Tools {

	public static int cast( final String arg, final int defaultValue  ) {
		int value = 0;
		
		try {
			value = Integer.valueOf( arg );
		} catch ( NumberFormatException nfe ) {
			value = defaultValue;
		}
		
		return value;
	}
}
