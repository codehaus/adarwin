package org.ajester.testmodel.test;

import org.ajester.testmodel.code.ProblematicIfNotEqualsStatement;

import junit.framework.TestCase;

public class ProblematicIfNotEqualsStatementTestCase extends TestCase {
	public void testIfNotEqual() {
		assertFalse(new ProblematicIfNotEqualsStatement().getTrue());
	}
}
