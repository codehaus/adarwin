package org.ajester;

import org.ajester.testmodel.BooleanReturn;
import org.ajester.testmodel.BooleanReturnTestCase;

import junit.framework.TestCase;

public class MethodCoverageTestCase extends TestCase {
	public void testCovered() throws Exception {
		new TestRunnerWrapper().run(BooleanReturnTestCase.class,
			new MethodCoverageMutator(BooleanReturn.GET_TRUE_LOCATION));

		assertTrue(Coverage.getMethodsCovered().contains(
			BooleanReturn.GET_TRUE_LOCATION.getMethodName()));
	}
}
