package org.ajester.testmodel;

import org.ajester.CodeLocation;

public class IfStatement {
	public static final String IF_EQUAL = "ifEqual";
	public static final String IF_NOT_EQUAL = "ifNotEqual";
	
	public static final CodeLocation IF_EQUAL_LOCATION =
		new CodeLocation(IfStatement.class, IF_EQUAL);
	
	public static final CodeLocation IF_NOT_EQUAL_LOCATION =
		new CodeLocation(IfStatement.class, IF_NOT_EQUAL);
	
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
