package org.ajester.testmodel;

import org.ajester.CodeLocation;

public class ProblematicBooleanReturn {
	public static final CodeLocation IRRELEVANT_METHOD_LOCATION =
		new CodeLocation(ProblematicBooleanReturn.class, "irrelevantMethod");
	
	public boolean getTrue() {
		return true || irrelevantMethod();
	}

	private boolean irrelevantMethod() {
		return true;
	}
}
