package org.ajester.testmodel.test;

import org.ajester.testmodel.code.UnionOfBooleanReturnAndIfStatement;

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
