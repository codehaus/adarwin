package org.ajester.testmodel;

import org.ajester.CodeLocation;

public class ProblematicIfStatement {
	public static final CodeLocation IF_EQUAL_LOCATION = 
		new CodeLocation(ProblematicIfStatement.class, "ifEqual");
	
	private boolean bTrue = true;
	private boolean nop;
	
	public boolean ifEqual() {
		if (bTrue) {
			nop = true;
		}
		return true;
	}
}
