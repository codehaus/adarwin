package org.ajester;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import junit.framework.TestFailure;

public class Report {
	public static final String NO_PROBLEMS = "No problems";
	public static final String SOME_PROBLEMS = "Some problems";

	private final Set failures;
	private final Set errors;
	private final Set mutations;
	private final Coverage coverage;

	public Report(Set failures, Set errors, Set mutations, Coverage coverage) {
		this.failures = failures;
		this.errors = errors;
		this.mutations = mutations;
		this.coverage = coverage;
	}

	public String getReport() {
		Set problems = getProblems();
		if (problems.size() != 0) {
			List sortedProblems = new ArrayList(problems);
			Collections.sort(sortedProblems, new Comparator() {
				public int compare(Object left, Object right) {
					CodeLocation leftCode = (CodeLocation) left;
					CodeLocation rightCode = (CodeLocation) right;
					
					if (leftCode.equals(rightCode)) {
						return 0;
					}
					else if (!leftCode.getClassName().equals(rightCode.getClassName())) {
						return leftCode.getClassName().compareTo(rightCode.getClassName());
					}
					else {
						return leftCode.getMethodName().compareTo(rightCode.getMethodName());					
					}
				}
			});

			StringBuffer buffer = new StringBuffer(SOME_PROBLEMS + ":\n");

			for(Iterator iterator = sortedProblems.iterator(); iterator.hasNext();) {
				CodeLocation codeLocation = (CodeLocation) iterator.next();
				buffer.append("\t" + codeLocation);
				if (iterator.hasNext()) {
					buffer.append("\n");
				}
			}
			
			return buffer.toString();
		}
		else {
			return NO_PROBLEMS;
		}
	}

	public Set getFailures() {
		return failures;
	}

	public Set getErrors() {
		return errors;
	}

	public Set getMutations() {
		return mutations;
	}

	public Coverage getCoverage() {
		return coverage;
	}

	private Set getProblems() {
		Set problems = new HashSet();
		Set coveredMethods = union(getCoverage().getCoverage(), mutations);
		
		for (Iterator iterator = coveredMethods.iterator(); iterator.hasNext();) {
			CodeLocation codeLocation = (CodeLocation) iterator.next();
			
			Set testsCovering = getCoverage().getTestsCovering(codeLocation);
			if (!hasMatchingFailure(testsCovering)) {
				problems.add(codeLocation);
			}
		}
		
		return problems;
	}

	public static Set union(Set left, Set right) {
		Set leftNotRight = new HashSet(left);
		leftNotRight.removeAll(right);

		Set union = new HashSet(left);
		union.removeAll(leftNotRight);
		
		return union;
	}

	private boolean hasMatchingFailure(Set testsCovering) {
		for (Iterator iterator = getFailures().iterator(); iterator.hasNext();) {
			TestFailure testFailure = (TestFailure) iterator.next();
			
			TestCase failedTest = (TestCase) testFailure.failedTest();
			
			if (testsCovering.contains(
				new CodeLocation(failedTest.getClass(), failedTest.getName()))) {
				return true;
			}
		}
		return false;
	}
}

