package org.ajester.testmodel;

import junit.framework.TestCase;

public class IfStatementTestCase extends TestCase {
	public void testIfEqual() {
		assertTrue(new IfStatement().ifEqual());
	}
	
	public void testIfNotEqual() {
		assertTrue(new IfStatement().ifNotEqual());
	}
}
