package org.ajester;

import org.ajester.testmodel.BooleanReturn;
import org.ajester.testmodel.BooleanReturnTestCase;

import junit.framework.TestCase;

public class MutateBooleanReturnTestCase extends TestCase {
	public void testMutatingBooleanModelCausesBooleanTestToFail() throws Exception {
		BooleanReturnTestCase.class.getName();
		TestResults results = new TestRunnerWrapper().run(
			BooleanReturnTestCase.class.getName(),
			BooleanReturn.class.getName(),
			new BooleanReturnCodeAdapter(BooleanReturn.class.getName()));
//			new BooleanReturnCodeAdapter("somethingElse"));//BooleanReturn.class.getName()));
		
		assertEquals(1, results.getFailures().size());
		assertEquals(0, results.getErrors().size());
	}

	public void testMutatingSomeOtherClassLeavesBooleanTestPassing() throws Exception {
		TestResults results = new TestRunnerWrapper().run(
			"org.ajester.testmodel.BooleanReturnTestCase",
			"someOtherClass",
			new BooleanReturnCodeAdapter("someOtherClass"));
		
		assertEquals(0, results.getFailures().size());
		assertEquals(0, results.getErrors().size());
	}
}
