package org.ajester;

import org.ajester.testmodel.ProblematicIfStatement;
import org.ajester.testmodel.ProblematicIfStatementTestCase;

import junit.framework.TestCase;

public class MutateProblematicIfStatementTestCase extends TestCase {
	public void testMutatingIFEQCausesIfStatementTestIfEqualToFail() throws Exception {
		TestResults results = new TestRunnerWrapper().run(
			ProblematicIfStatementTestCase.class,
			new IfStatementMutator(ProblematicIfStatement.IF_EQUAL_LOCATION));

		assertEquals(0, results.getFailures().size());
		assertEquals(0, results.getErrors().size());
	}
}
