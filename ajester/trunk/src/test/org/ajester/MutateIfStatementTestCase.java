package org.ajester;

import junit.framework.TestCase;
import junit.framework.TestFailure;

public class MutateIfStatementTestCase extends TestCase {
	public void testMutatingIFEQCausesIfStatementTestIfEqualToFail() throws Exception {
		TestResults results = new TestRunnerWrapper().run(
			"org.ajester.testmodel.IfStatementTestCase",
			new IfStatementCodeAdapter("org.ajester.testmodel.IfStatement", "ifEqual"));
		
		assertEquals(1, results.getFailures().size());
		TestFailure failure = (TestFailure) results.getFailures().toArray()[0];
		Util.assertEqualsTestCase(failure, "org.ajester.testmodel.IfStatementTestCase",
			"testIfEqual");
		assertEquals(0, results.getErrors().size());
	}
	
	public void testMutatingIFNECausesIfStatementIfNotEqualToFail() throws Exception {
		TestResults results = new TestRunnerWrapper().run(
			"org.ajester.testmodel.IfStatementTestCase",
			new IfStatementCodeAdapter("org.ajester.testmodel.IfStatement", "ifNotEqual"));
		
		assertEquals(1, results.getFailures().size());
		TestFailure failure = (TestFailure) results.getFailures().toArray()[0];
		Util.assertEqualsTestCase(failure, "org.ajester.testmodel.IfStatementTestCase",
			"testIfNotEqual");
		assertEquals(0, results.getErrors().size());
	}
}
