package com.qtpeters.jws.service;

import org.junit.Test;

public class MainTest {
 
	@Test
	public void initTest() throws Exception {
		
		String [] args = { "44444" };
		Main main = new Main( args );
		main.init();
	}
}
