package org.ajester;

import org.ajester.testmodel.code.IfEqualsStatement;
import org.ajester.testmodel.test.IfEqualsStatementTestCase;

import junit.framework.TestCase;
import junit.framework.TestFailure;

public class MutateIfEqualsStatementTestCase extends TestCase {
	public void testMutatingIFEQCausesIfStatementTestIfEqualToFail() throws Exception {
		Report report = new TestRunnerWrapper().run(IfEqualsStatementTestCase.class,
			new IfStatementMutator(IfEqualsStatement.IF_EQUAL_LOCATION));
		
		assertEquals(1, report.getFailures().size());
		TestFailure failure = (TestFailure) report.getFailures().toArray()[0];
		Util.assertEqualsTestCase(failure, IfEqualsStatementTestCase.class,
			"testIfEqualMethodReturnsTrue");
		assertEquals(0, report.getErrors().size());
		
		assertTrue(report.getCoverage().contains(IfEqualsStatement.IF_EQUAL_LOCATION));
	}
}
