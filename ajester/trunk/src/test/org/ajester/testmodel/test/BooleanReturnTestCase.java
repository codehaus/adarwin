package org.ajester.testmodel.test;

import org.ajester.testmodel.code.BooleanReturn;

import junit.framework.TestCase;

public class BooleanReturnTestCase extends TestCase {
	public void testGetTrueMethodReturnsTrue() {
		assertTrue(new BooleanReturn().getTrue());
	}
}
