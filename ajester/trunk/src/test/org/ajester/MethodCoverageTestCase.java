package org.ajester;

import org.ajester.testmodel.code.BooleanReturn;
import org.ajester.testmodel.test.BooleanReturnAndIfStatementTestSuite;
import org.ajester.testmodel.test.BooleanReturnTestCase;

import java.util.Set;

import junit.framework.TestCase;

public class MethodCoverageTestCase extends TestCase {
	public void testCovered() throws Exception {
		Report report = new TestRunnerWrapper().run(BooleanReturnTestCase.class,
			new MethodCoverageMutator(BooleanReturn.GET_TRUE_LOCATION));

		assertTrue(report.getCoverage().contains(BooleanReturn.GET_TRUE_LOCATION));
		assertEquals(1, report.getCoverage().getCoverage().size());
	}

	public void testTwoClassesCovered() throws Exception {
		Report report = new TestRunnerWrapper().run(BooleanReturnAndIfStatementTestSuite.class,
			new MethodCoverageMutator(new PackageCodeMatcher("org.ajester.testmodel")));

		Set coverage = report.getCoverage().getCoverage();

		assertTrue(report.getCoverage().contains(BooleanReturn.GET_TRUE_LOCATION));
		assertEquals(7, coverage.size());
	}
}
