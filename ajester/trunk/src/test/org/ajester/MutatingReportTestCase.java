package org.ajester;

import junit.framework.TestCase;

public class MutatingReportTestCase extends TestCase {
	public void testMutate() throws Exception {
		CodeLocation location = new CodeLocation(Report.class.getName(), "getProblemString");
		TestResults results = new TestRunnerWrapper().run(
			ReportTestCase.class, new IfStatementMutator(location));
		
		assertEquals(1, results.getFailures().size());
		assertEquals(0, results.getErrors().size());
	}
}
