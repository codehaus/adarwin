package org.ajester;

import org.ajester.testmodel.BooleanReturn;
import org.ajester.testmodel.BooleanReturnTestCase;
import org.ajester.testmodel.IfStatement;

import junit.framework.TestCase;

public class MutateBooleanReturnTestCase extends TestCase {
	public void testMutatingBooleanModelCausesBooleanTestToFail() throws Exception {
		TestResults results = new TestRunnerWrapper().run(
			BooleanReturnTestCase.class,
			new BooleanReturnMutator(BooleanReturn.GET_TRUE_LOCATION));
		
		assertEquals(1, results.getFailures().size());
		assertEquals(0, results.getErrors().size());
	}

	public void testMutatingSomeOtherClassLeavesBooleanTestPassing() throws Exception {
		TestResults results = new TestRunnerWrapper().run(
			BooleanReturnTestCase.class,
			new BooleanReturnMutator(IfStatement.IF_EQUAL_LOCATION));
		
		assertEquals(0, results.getFailures().size());
		assertEquals(0, results.getErrors().size());
	}
}
