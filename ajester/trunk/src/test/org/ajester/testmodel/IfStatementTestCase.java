package org.ajester.testmodel;

import junit.framework.TestCase;

public class IfStatementTestCase extends TestCase {
	public void testIfEqualMethodReturnsTrue() {
		assertTrue(new IfStatement().ifEqual());
	}
	
	public void testIfNotEqualMethodReturnsTrue() {
		assertTrue(new IfStatement().ifNotEqual());
	}
}
