package org.ajester.testmodel.code;

import org.ajester.CodeLocation;

public class ProblematicIfNotEqualsStatement implements Model {
	public static final CodeLocation LOCATION =
		new CodeLocation(ProblematicIfEqualsStatement.class, GET_TRUE);

	private boolean bTrue = true;

	public boolean getTrue() {
		if (!bTrue) {
			nop();
		}

		return false;
	}

	private void nop() {
	}
}
