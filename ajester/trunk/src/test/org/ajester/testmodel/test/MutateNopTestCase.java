package org.ajester.testmodel.test;

import org.ajester.BooleanReturnMutator;
import org.ajester.Report;
import org.ajester.TestRunnerWrapper;
import org.ajester.testmodel.code.Nop;

import junit.framework.TestCase;

public class MutateNopTestCase extends TestCase {
	public void testMutateBooleanReturnLeavesTestPassing() throws Exception {
		Report report = new TestRunnerWrapper().run(
			NopTestCase.class, new BooleanReturnMutator(Nop.NOP_LOCATION));

		assertEquals(0, report.getFailures().size());
		assertEquals(0, report.getErrors().size());

		assertEquals(Report.NO_PROBLEMS, report.getReport());
	}
}
