package org.ajester;

import org.ajester.testmodel.BooleanReturn;
import org.ajester.testmodel.BooleanReturnTestCase;

import junit.framework.TestCase;

public class MethodCoverageTestCase extends TestCase {
	public void testCovered() throws Exception {
		MethodCoverageClassAdapter methodCoverageClassAdapter =
			new MethodCoverageClassAdapter(BooleanReturn.class.getName());

		new TestRunnerWrapper().run(BooleanReturnTestCase.class.getName(),
			methodCoverageClassAdapter);

		assertTrue(Coverage.getMethodsCovered().contains("getTrue"));
	}
}
