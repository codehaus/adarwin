package org.ajester.testmodel.test;

import org.ajester.testmodel.code.ProblematicBooleanReturn;

import junit.framework.TestCase;

public class ProblematicBooleanReturnTestCase extends TestCase {
	public void testGetTrue() {
		assertTrue(new ProblematicBooleanReturn().getTrue());
	}
}
