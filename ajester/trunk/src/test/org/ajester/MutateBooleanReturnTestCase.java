package org.ajester;

import org.ajester.testmodel.BooleanReturn;
import org.ajester.testmodel.BooleanReturnTestCase;
import org.ajester.testmodel.IfStatement;

import junit.framework.TestCase;

public class MutateBooleanReturnTestCase extends TestCase {
	public void testMutatingBooleanModelCausesBooleanTestToFail() throws Exception {
		Report report = new TestRunnerWrapper().run(
			BooleanReturnTestCase.class, new BooleanReturnMutator(BooleanReturn.GET_TRUE_LOCATION));

		assertEquals(1, report.getFailures().size());
		assertEquals(0, report.getErrors().size());
		
		assertTrue(report.getCoverage().contains(BooleanReturn.GET_TRUE_LOCATION));
	}

	public void testMutatingSomeOtherClassLeavesBooleanTestPassing() throws Exception {
		Report report = new TestRunnerWrapper().run(
			BooleanReturnTestCase.class, new BooleanReturnMutator(IfStatement.IF_EQUAL_LOCATION));

		assertEquals(0, report.getFailures().size());
		assertEquals(0, report.getErrors().size());
		
		assertFalse(report.getCoverage().contains(IfStatement.IF_EQUAL_LOCATION));
	}
}
