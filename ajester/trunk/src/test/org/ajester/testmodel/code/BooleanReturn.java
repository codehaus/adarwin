package org.ajester.testmodel.code;

import org.ajester.CodeLocation;

public class BooleanReturn implements Model {
	public static final CodeLocation LOCATION =
		new CodeLocation(BooleanReturn.class, GET_TRUE);
	
	public boolean getTrue() {
		return true;
	}
}
