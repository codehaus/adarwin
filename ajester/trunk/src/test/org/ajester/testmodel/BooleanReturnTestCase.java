package org.ajester.testmodel;

import junit.framework.TestCase;

public class BooleanReturnTestCase extends TestCase {
	public void testBoolean() {
		assertTrue(new BooleanReturn().getTrue());
	}
}
