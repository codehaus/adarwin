package org.ajester;

import java.util.Collections;

import junit.framework.TestCase;

public class ReportTestCase extends TestCase {
	public void testReportHasNoProblemsWithTestResultWithNoFailuresOrErrors() {
		TestResults testResults = new TestResults(Collections.EMPTY_SET, Collections.EMPTY_SET);
		String className = "className";
		
		Report report = new Report(className, testResults);
				
		assertEquals(Report.SOME_PROBLEMS + ": " + className, report.getReport());
	}
}
