package org.ajester;

import org.ajester.testmodel.IfStatement;
import org.ajester.testmodel.IfStatementTestCase;

import junit.framework.TestCase;
import junit.framework.TestFailure;

public class MutateIfStatementTestCase extends TestCase {
	public void testMutatingIFEQCausesIfStatementTestIfEqualToFail() throws Exception {
		TestResults results = new TestRunnerWrapper().run(IfStatementTestCase.class,
			new IfStatementMutator(IfStatement.IF_EQUAL_LOCATION));
		
		assertEquals(1, results.getFailures().size());
		TestFailure failure = (TestFailure) results.getFailures().toArray()[0];
		Util.assertEqualsTestCase(failure, IfStatementTestCase.class, "testIfEqual");
		assertEquals(0, results.getErrors().size());
	}
	
	public void testMutatingIFNECausesIfStatementIfNotEqualToFail() throws Exception {
		TestResults results = new TestRunnerWrapper().run(IfStatementTestCase.class,
			new IfStatementMutator(IfStatement.IF_NOT_EQUAL_LOCATION));
		
		assertEquals(1, results.getFailures().size());
		TestFailure failure = (TestFailure) results.getFailures().toArray()[0];
		Util.assertEqualsTestCase(failure, IfStatementTestCase.class, "testIfNotEqual");
		assertEquals(0, results.getErrors().size());
	}
}
