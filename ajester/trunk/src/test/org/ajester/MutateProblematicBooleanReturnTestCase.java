package org.ajester;

import org.ajester.testmodel.ProblematicBooleanReturn;
import org.ajester.testmodel.ProblematicBooleanReturnTestCase;

import junit.framework.TestCase;

public class MutateProblematicBooleanReturnTestCase extends TestCase {
	public void testMutatingIrrelevantMethodLeavesTestsStillPassing() throws Exception {
		Report report = new TestRunnerWrapper().run(
			ProblematicBooleanReturnTestCase.class,
			new IfStatementMutator(ProblematicBooleanReturn.IRRELEVANT_METHOD_LOCATION));

		assertEquals(0, report.getFailures().size());
		assertEquals(0, report.getErrors().size());
	}
}
