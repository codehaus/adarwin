package org.ajester;

import org.ajester.testmodel.BooleanReturn;
import org.ajester.testmodel.BooleanReturnTestCase;

import junit.framework.TestCase;

public class MutateBooleanReturnTestCase extends TestCase {
	public void testMutatingBooleanModelCausesBooleanTestToFail() throws Exception {
		TestResults results = new TestRunnerWrapper().run(
			BooleanReturnTestCase.class.getName(),
			new BooleanReturnClassAdapter(BooleanReturn.class.getName()));
		
		assertEquals(1, results.getFailures().size());
		assertEquals(0, results.getErrors().size());
	}

	public void testMutatingSomeOtherClassLeavesBooleanTestPassing() throws Exception {
		TestResults results = new TestRunnerWrapper().run(
			"org.ajester.testmodel.BooleanReturnTestCase",
			new BooleanReturnClassAdapter("someOtherClass"));
		
		assertEquals(0, results.getFailures().size());
		assertEquals(0, results.getErrors().size());
	}
}
