package org.ajester.testmodel;

import junit.framework.TestCase;

public class UnionOfBooleanReturnAndIfStatementTestCase extends TestCase {
	public void testBoolean() {
		assertTrue(new UnionOfBooleanReturnAndIfStatement().getTrue());
	}
	
	public void testIfEqual() {
		assertTrue(new UnionOfBooleanReturnAndIfStatement().ifEqual());
	}
	
	public void testIfNotEqual() {
		assertTrue(new UnionOfBooleanReturnAndIfStatement().ifNotEqual());
	}
}
