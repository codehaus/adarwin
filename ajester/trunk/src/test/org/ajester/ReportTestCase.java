package org.ajester;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class ReportTestCase extends TestCase {
	public void testReportHasSomeProblemsWithTestResultWithNoFailuresOrErrorsAndMutatedMethodCalled() {
		CodeLocation codeLocation = new CodeLocation("class", "method");
		Coverage coverage = new Coverage();
		coverage.methodCovered(codeLocation);
		Report report = new Report(Collections.EMPTY_SET, Collections.EMPTY_SET,
			coverage.getCoverage(), coverage);

		assertEquals(Report.SOME_PROBLEMS + ":\n\t" + codeLocation, report.getReport());
	}
	
	public void testUnionIdenticalSets() {
		Set left = new HashSet();
		left.add("1");
		left.add("2");
		left.add("3");
		
		Set union = Report.union(left, left);
		
		assertEquals(left, union);
	}
}
