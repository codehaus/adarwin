package org.ajester.testmodel.code;

import org.ajester.CodeLocation;

public class IfEqualsStatement implements Model {
	public static final CodeLocation IF_EQUAL_LOCATION =
		new CodeLocation(IfEqualsStatement.class, GET_TRUE);
	
	private boolean bTrue = true;
	
	public boolean getTrue() {
		if (bTrue) {
			return true;
		}
		return false;
	}
}
