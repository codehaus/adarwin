package org.ajester.testmodel;

import org.ajester.CodeLocation;

public class ProblematicIfStatement {
	public static final CodeLocation IF_EQUAL_LOCATION = 
		new CodeLocation(ProblematicIfStatement.class, "ifEqual");

	public static final CodeLocation IF_NOT_EQUAL_LOCATION =
		new CodeLocation(ProblematicIfStatement.class, "ifNotEqual");
	
	private boolean bTrue = true;

	public boolean ifEqual() {
		if (bTrue) {
			nop();
		}

		return true;
	}

	public boolean ifNotEqual() {
		if (!bTrue) {
			nop();
		}

		return false;
	}

	private void nop() {
	}
}
