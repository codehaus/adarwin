package org.ajester.testmodel;

public class IfStatement {
	private boolean bTrue = true;
	
	public boolean ifEqual() {
		if (bTrue) {
			return true;
		}
		return false;
	}
	
	public boolean ifNotEqual() {
		if (!bTrue) {
			return false;
		}
		return true;
	}
}
