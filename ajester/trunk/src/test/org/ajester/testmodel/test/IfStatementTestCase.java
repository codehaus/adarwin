package org.ajester.testmodel.test;

import org.ajester.testmodel.code.IfStatement;

import junit.framework.TestCase;

public class IfStatementTestCase extends TestCase {
	public void testIfEqualMethodReturnsTrue() {
		assertTrue(new IfStatement().ifEqual());
	}
	
	public void testIfNotEqualMethodReturnsTrue() {
		assertTrue(new IfStatement().ifNotEqual());
	}
}
