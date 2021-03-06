package org.ajester;

import org.ajester.testmodel.code.BooleanReturn;
import org.ajester.testmodel.test.BooleanReturnAndIfStatementTestSuite;
import org.ajester.testmodel.test.BooleanReturnTestCase;

import java.util.Set;

import junit.framework.TestCase;

public class MethodCoverageTestCase extends TestCase {
	public void testCovered() throws Exception {
		Report report = new TestRunnerWrapper().run(BooleanReturnTestCase.class,
			new MethodCoverageInstructionMutator(new CodeLocationMatcher(BooleanReturn.LOCATION)));

		assertEquals(1, report.getCoverage().getCoverage().size());
		assertTrue(report.getCoverage().contains(BooleanReturn.LOCATION));
	}

	public void testTwoClassesCovered() throws Exception {
		Report report = new TestRunnerWrapper().run(BooleanReturnAndIfStatementTestSuite.class,
			new MethodCoverageInstructionMutator(new PackageCodeMatcher("org.ajester.testmodel.code")));

		Set coverage = report.getCoverage().getCoverage();

		assertEquals(2, coverage.size());
		assertTrue(report.getCoverage().contains(BooleanReturn.LOCATION));
	}
}
