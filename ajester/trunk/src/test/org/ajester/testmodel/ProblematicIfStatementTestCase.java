package org.ajester.testmodel;

import junit.framework.TestCase;

public class ProblematicIfStatementTestCase extends TestCase {
	public void testIfEqual() {
		assertTrue(new ProblematicIfStatement().ifEqual());
	}
}
