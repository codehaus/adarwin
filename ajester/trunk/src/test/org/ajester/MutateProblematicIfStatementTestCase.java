package org.ajester;

import junit.framework.TestCase;

public class MutateProblematicIfStatementTestCase extends TestCase {
	public void testMutatingIFEQCausesIfStatementTestIfEqualToFail() throws Exception {
		TestResults results = new TestRunnerWrapper().run(
			"org.ajester.testmodel.ProblematicIfStatementTestCase",
			new IfStatementCodeAdapter("org.ajester.testmodel.ProblematicIfStatement", "ifEqual"));

		assertEquals(0, results.getFailures().size());
		assertEquals(0, results.getErrors().size());
	}
}
