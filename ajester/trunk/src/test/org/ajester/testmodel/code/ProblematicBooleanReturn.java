package org.ajester.testmodel.code;

import org.ajester.CodeLocation;

public class ProblematicBooleanReturn implements Model {
	public static final CodeLocation LOCATION =
		new CodeLocation(ProblematicBooleanReturn.class, "irrelevantMethod");
	
	public boolean getTrue() {
		return true || irrelevantMethod();
	}

	private boolean irrelevantMethod() {
		return true;
	}
}
