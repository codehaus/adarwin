package org.ajester;

import org.ajester.testmodel.IfStatement;
import org.ajester.testmodel.IfStatementTestCase;

import junit.framework.TestCase;
import junit.framework.TestFailure;

public class MutateIfStatementTestCase extends TestCase {
	public void testMutatingIFEQCausesIfStatementTestIfEqualToFail() throws Exception {
		Report report = new TestRunnerWrapper().run(IfStatementTestCase.class,
			new IfStatementMutator(IfStatement.IF_EQUAL_LOCATION));
		
		assertEquals(1, report.getFailures().size());
		TestFailure failure = (TestFailure) report.getFailures().toArray()[0];
		Util.assertEqualsTestCase(failure, IfStatementTestCase.class,
			"testIfEqualMethodReturnsTrue");
		assertEquals(0, report.getErrors().size());
		
		assertTrue(report.getCoverage().contains(IfStatement.IF_EQUAL_LOCATION));
		assertFalse(report.getCoverage().contains(IfStatement.IF_NOT_EQUAL_LOCATION));
	}
	
	public void testMutatingIFNECausesIfStatementIfNotEqualToFail() throws Exception {
		Report report = new TestRunnerWrapper().run(IfStatementTestCase.class,
			new IfStatementMutator(IfStatement.IF_NOT_EQUAL_LOCATION));
		
		assertEquals(1, report.getFailures().size());
		TestFailure failure = (TestFailure) report.getFailures().toArray()[0];
		Util.assertEqualsTestCase(failure, IfStatementTestCase.class,
			"testIfNotEqualMethodReturnsTrue");
		assertEquals(0, report.getErrors().size());
		
		assertTrue(report.getCoverage().contains(IfStatement.IF_NOT_EQUAL_LOCATION));
		assertFalse(report.getCoverage().contains(IfStatement.IF_EQUAL_LOCATION));
	}
}
