package org.ajester.testmodel.test;

import org.ajester.testmodel.code.IfNotEqualsStatement;

import junit.framework.TestCase;

public class IfNotEqualsStatementTestCase extends TestCase {
	public void testIfNotEqualMethodReturnsTrue() {
		assertTrue(new IfNotEqualsStatement().getTrue());
	}
}
