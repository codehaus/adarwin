package org.ajester;

import org.ajester.testmodel.code.IfEqualsStatement;
import org.ajester.testmodel.code.IfNotEqualsStatement;
import org.ajester.testmodel.test.IfEqualsStatementTestCase;
import org.ajester.testmodel.test.IfNotEqualsStatementTestCase;

import junit.framework.TestCase;
import junit.framework.TestFailure;

public class MutateIfStatementTestCase extends TestCase {
	public void testMutatingIFEQCausesIfStatementTestIfEqualToFail() throws Exception {
		Report report = new TestRunnerWrapper().run(IfEqualsStatementTestCase.class,
			new IfStatementMutator(IfEqualsStatement.IF_EQUAL_LOCATION));
		
		assertEquals(1, report.getFailures().size());
		TestFailure failure = (TestFailure) report.getFailures().toArray()[0];
		Util.assertEqualsTestCase(failure, IfEqualsStatementTestCase.class,
			"testIfEqualMethodReturnsTrue");
		assertEquals(0, report.getErrors().size());
		
		assertTrue(report.getCoverage().contains(IfEqualsStatement.IF_EQUAL_LOCATION));
		assertFalse(report.getCoverage().contains(IfNotEqualsStatement.LOCATION));
	}
	
	public void testMutatingIFNECausesIfStatementIfNotEqualToFail() throws Exception {
		Report report = new TestRunnerWrapper().run(IfNotEqualsStatementTestCase.class,
			new IfStatementMutator(IfNotEqualsStatement.LOCATION));
		
		assertEquals(1, report.getFailures().size());
		TestFailure failure = (TestFailure) report.getFailures().toArray()[0];
		Util.assertEqualsTestCase(failure, IfNotEqualsStatementTestCase.class,
			"testIfNotEqualMethodReturnsTrue");
		assertEquals(0, report.getErrors().size());
		
		assertTrue(report.getCoverage().contains(IfNotEqualsStatement.LOCATION));
		assertFalse(report.getCoverage().contains(IfEqualsStatement.IF_EQUAL_LOCATION));
	}
}
