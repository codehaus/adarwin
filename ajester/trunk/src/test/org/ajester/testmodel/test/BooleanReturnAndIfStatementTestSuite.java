package org.ajester.testmodel.test;

import junit.framework.TestSuite;

public class BooleanReturnAndIfStatementTestSuite {
	public static TestSuite suite() {
		TestSuite suite = new TestSuite();
		
		suite.addTestSuite(BooleanReturnTestCase.class);
		suite.addTestSuite(IfStatementTestCase.class);
		
		return suite;
	}
}
