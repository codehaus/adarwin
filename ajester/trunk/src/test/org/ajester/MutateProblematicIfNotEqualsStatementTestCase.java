package org.ajester;

import org.ajester.testmodel.code.ProblematicIfNotEqualsStatement;
import org.ajester.testmodel.test.ProblematicIfStatementTestCase;

import junit.framework.TestCase;

public class MutateProblematicIfNotEqualsStatementTestCase extends TestCase {
	public void testMutatingIFNECausesLeavesTestStillPassing() throws Exception {
		Report report = new TestRunnerWrapper().run(ProblematicIfStatementTestCase.class,
			new IfStatementInstructionMutator(new CodeLocationMatcher(ProblematicIfNotEqualsStatement.LOCATION)));

		assertEquals(0, report.getFailures().size());
		assertEquals(0, report.getErrors().size());
	}
}
