package org.ajester.testmodel.test;

import org.ajester.testmodel.code.IfEqualsStatement;
import org.ajester.testmodel.code.IfNotEqualsStatement;

import junit.framework.TestCase;

public class IfStatementTestCase extends TestCase {
	public void testIfEqualMethodReturnsTrue() {
		assertTrue(new IfEqualsStatement().getTrue());
	}
	
	public void testIfNotEqualMethodReturnsTrue() {
		assertTrue(new IfNotEqualsStatement().getTrue());
	}
}
