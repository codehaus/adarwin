package org.ajester;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestFailure;

public class Util {

	public static void assertEqualsTestCase(TestFailure failure, String testClass,
		String testMethod) {
		
		Test failedTest = failure.failedTest();
		Assert.assertEquals("FailedTest is not a " + testClass, testClass,
			failedTest.getClass().getName());

		TestCase testCase = (TestCase) failedTest;
		Assert.assertEquals("FailedMethod is not " + testMethod, testMethod, testCase.getName());
	}
}
