package org.ajester;

import org.ajester.testmodel.code.IfNotEqualsStatement;
import org.ajester.testmodel.test.IfNotEqualsStatementTestCase;

import junit.framework.TestCase;
import junit.framework.TestFailure;

public class MutateIfNotEqualsStatementTestCase extends TestCase {
	public void testMutatingIFNECausesIfStatementIfNotEqualToFail() throws Exception {
		Report report = new TestRunnerWrapper().run(IfNotEqualsStatementTestCase.class,
			new IfStatementMatcher(new CodeLocationMatcher(IfNotEqualsStatement.LOCATION)),
			new IfStatementInstructionMutator());
		
		assertEquals(1, report.getFailures().size());
		TestFailure failure = (TestFailure) report.getFailures().toArray()[0];
		Util.assertEqualsTestCase(failure, IfNotEqualsStatementTestCase.class,
			"testIfNotEqualMethodReturnsTrue");
		assertEquals(0, report.getErrors().size());
		
		assertTrue(report.getCoverage().contains(IfNotEqualsStatement.LOCATION));
	}
}
