package org.ajester.testmodel.test;

import org.ajester.testmodel.code.ProblematicIfEqualsStatement;

import junit.framework.TestCase;

public class ProblematicIfStatementTestCase extends TestCase {
	public void testIfEqual() {
		assertTrue(new ProblematicIfEqualsStatement().getTrue());
	}
}
