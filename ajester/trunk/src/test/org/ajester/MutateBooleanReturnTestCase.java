package org.ajester;

import org.ajester.testmodel.code.BooleanReturn;
import org.ajester.testmodel.code.IfEqualsStatement;
import org.ajester.testmodel.test.BooleanReturnTestCase;

import junit.framework.TestCase;

public class MutateBooleanReturnTestCase extends TestCase {
	public void testMutatingBooleanModelCausesBooleanTestToFail() throws Exception {
		Report report = new TestRunnerWrapper().run(
			BooleanReturnTestCase.class,
				new BooleanReturnInstructionMutator(new CodeLocationMatcher(BooleanReturn.LOCATION)));

		assertEquals(1, report.getFailures().size());
		assertEquals(0, report.getErrors().size());
		
		assertTrue(report.getCoverage().contains(BooleanReturn.LOCATION));
	}

	public void testMutatingSomeOtherClassLeavesBooleanTestPassing() throws Exception {
		Report report = new TestRunnerWrapper().run(
			BooleanReturnTestCase.class,
				new BooleanReturnInstructionMutator(new CodeLocationMatcher(IfEqualsStatement.LOCATION)));

		assertEquals(0, report.getFailures().size());
		assertEquals(0, report.getErrors().size());
		
		assertFalse(report.getCoverage().contains(IfEqualsStatement.LOCATION));
	}
}
