package org.ajester.testmodel.code;

import org.ajester.CodeLocation;

public class ProblematicIfEqualsStatement implements Model {
	public static final CodeLocation IF_EQUAL_LOCATION = 
		new CodeLocation(ProblematicIfEqualsStatement.class, GET_TRUE);

	private boolean bTrue = true;

	public boolean getTrue() {
		if (bTrue) {
			nop();
		}

		return true;
	}

	private void nop() {
	}
}
