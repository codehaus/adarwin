package org.ajester.testmodel.code;

import org.ajester.CodeLocation;

public class BooleanReturn implements Model {
	public static final CodeLocation GET_TRUE_LOCATION =
		new CodeLocation(BooleanReturn.class, "getTrue");
	
	public boolean getTrue() {
		return true;
	}
}
