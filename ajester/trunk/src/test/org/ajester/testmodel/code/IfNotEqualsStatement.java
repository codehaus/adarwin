package org.ajester.testmodel.code;

import org.ajester.CodeLocation;

public class IfNotEqualsStatement implements Model {
	public static final CodeLocation LOCATION =
		new CodeLocation(IfNotEqualsStatement.class, GET_TRUE);
	
	private boolean bTrue = true;

	public boolean getTrue() {
		if (!bTrue) {
			return false;
		}
		return true;
	}
}
