package org.ajester;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestResult;

public class TestResults {
	private Set failures;
	private Set errors;

	public TestResults(TestResult testResult) {
		this(convertEnumerationToSet(testResult.failures()),
			 convertEnumerationToSet(testResult.errors()));
	}

	public TestResults(Set failures, Set errors) {
		this.failures = failures;
		this.errors = errors;
	}
	
	public Set getFailures() {
		return failures;
	}
	
	public Set getErrors() {
		return errors;
	}

	private static Set convertEnumerationToSet(Enumeration enumeration) {
		Set set = new HashSet();
		
		while(enumeration.hasMoreElements()) {
			set.add(enumeration.nextElement());
		}
		
		return set;
	}
}
