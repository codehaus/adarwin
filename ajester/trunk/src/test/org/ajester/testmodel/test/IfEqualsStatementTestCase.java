package org.ajester.testmodel.test;

import org.ajester.testmodel.code.IfEqualsStatement;

import junit.framework.TestCase;

public class IfEqualsStatementTestCase extends TestCase {
	public void testIfEqualMethodReturnsTrue() {
		assertTrue(new IfEqualsStatement().getTrue());
	}
}
