package org.ajester;

import org.ajester.testmodel.code.Nop;
import org.ajester.testmodel.test.NopTestCase;

import junit.framework.TestCase;

public class MutateNopTestCase extends TestCase {
	public void testMutateBooleanReturnLeavesTestPassing() throws Exception {
		Report report = new TestRunnerWrapper().run(NopTestCase.class,
			new BooleanReturnMatcher(new CodeLocationMatcher(Nop.NOP_LOCATION)),
			new BooleanReturnInstructionMutator());

		assertEquals(0, report.getFailures().size());
		assertEquals(0, report.getErrors().size());

		assertEquals(Report.NO_PROBLEMS, report.getReport());
	}
}
