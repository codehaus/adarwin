package org.ajester.testmodel;

import junit.framework.TestCase;

public class BooleanReturnTestCase extends TestCase {
	public void testGetTrueMethodReturnsTrue() {
		assertTrue(new BooleanReturn().getTrue());
	}
}
