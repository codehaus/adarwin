package org.ajester.testmodel;

public class ProblematicIfStatement {
	private boolean bTrue = true;
	private boolean nop;
	
	public boolean ifEqual() {
		if (bTrue) {
			nop = true;
		}
		return true;
	}
}
