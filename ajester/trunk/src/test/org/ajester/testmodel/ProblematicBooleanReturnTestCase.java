package org.ajester.testmodel;

import junit.framework.TestCase;

public class ProblematicBooleanReturnTestCase extends TestCase {
	public void testGetTrue() {
		assertTrue(new ProblematicBooleanReturn().getTrue());
	}
}
