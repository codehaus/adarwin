package org.ajester;

import org.ajester.testmodel.code.ProblematicIfEqualsStatement;
import org.ajester.testmodel.test.ProblematicIfStatementTestCase;

import junit.framework.TestCase;

public class MutateProblematicIfEqualsStatementTestCase extends TestCase {
	public void testMutatingIFEQCausesLeavesTestStillPassing() throws Exception {
		Report report = new TestRunnerWrapper().run(ProblematicIfStatementTestCase.class,
			new IfStatementInstructionMutator(new CodeLocationMatcher(ProblematicIfEqualsStatement.IF_EQUAL_LOCATION)));

		assertEquals(0, report.getFailures().size());
		assertEquals(0, report.getErrors().size());
	}
}
