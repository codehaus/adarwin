package org.ajester;

import org.ajester.testmodel.code.ProblematicBooleanReturn;
import org.ajester.testmodel.test.ProblematicBooleanReturnTestCase;

import junit.framework.TestCase;

public class MutateProblematicBooleanReturnTestCase extends TestCase {
	public void testMutatingIrrelevantMethodLeavesTestsStillPassing() throws Exception {
		Report report = new TestRunnerWrapper().run(
			ProblematicBooleanReturnTestCase.class,
			new IfStatementMutator(ProblematicBooleanReturn.LOCATION));

		assertEquals(0, report.getFailures().size());
		assertEquals(0, report.getErrors().size());
	}
}
