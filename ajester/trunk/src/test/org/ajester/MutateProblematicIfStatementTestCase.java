package org.ajester;

import org.ajester.testmodel.code.ProblematicIfStatement;
import org.ajester.testmodel.test.ProblematicIfStatementTestCase;

import junit.framework.TestCase;

public class MutateProblematicIfStatementTestCase extends TestCase {
	public void testMutatingIFEQCausesLeavesTestStillPassing() throws Exception {
		Report report = new TestRunnerWrapper().run(
			ProblematicIfStatementTestCase.class,
			new IfStatementMutator(ProblematicIfStatement.IF_EQUAL_LOCATION));

		assertEquals(0, report.getFailures().size());
		assertEquals(0, report.getErrors().size());
	}
	
	public void testMutatingIFNECausesLeavesTestStillPassing() throws Exception {
		Report report = new TestRunnerWrapper().run(
			ProblematicIfStatementTestCase.class,
			new IfStatementMutator(ProblematicIfStatement.IF_NOT_EQUAL_LOCATION));

		assertEquals(0, report.getFailures().size());
		assertEquals(0, report.getErrors().size());
	}
}
