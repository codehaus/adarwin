package org.ajester.testmodel.test;

import org.ajester.testmodel.code.ProblematicIfStatement;

import junit.framework.TestCase;

public class ProblematicIfStatementTestCase extends TestCase {
	public void testIfEqual() {
		assertTrue(new ProblematicIfStatement().ifEqual());
	}

	public void testIfNotEqual() {
		assertFalse(new ProblematicIfStatement().ifNotEqual());
	}
}
