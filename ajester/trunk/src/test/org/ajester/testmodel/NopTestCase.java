package org.ajester.testmodel;

import junit.framework.TestCase;

public class NopTestCase extends TestCase {
	public void testNop() {
		new Nop().nop();
	}
}
