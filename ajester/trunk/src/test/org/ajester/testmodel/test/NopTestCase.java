package org.ajester.testmodel.test;

import org.ajester.testmodel.code.Nop;

import junit.framework.TestCase;

public class NopTestCase extends TestCase {
	public void testNop() {
		new Nop().nop();
	}
}
