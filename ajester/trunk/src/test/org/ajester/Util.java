package org.ajester;

import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestFailure;

public class Util {
	public static void assertEqualsTestCase(TestFailure failure, Class testClass,
		String testMethod) {
		
		Test failedTest = failure.failedTest();
		
		Assert.assertEquals("FailedTest is not a " + testClass.getName(), testClass.getName(),
			failedTest.getClass().getName());

		TestCase testCase = (TestCase) failedTest;
		Assert.assertEquals("FailedMethod is not " + testMethod, testMethod, testCase.getName());
	}

	public static void assertContains(Set failureSet, String testClass, String testMethod) {
		boolean contains = false;
		
		for(Iterator iterator = failureSet.iterator(); iterator.hasNext() && !contains;) {
			TestFailure failure = (TestFailure) iterator.next();
			
			if (isEqual(failure.failedTest(), testClass, testMethod)) {
				contains = true;
			}
		}
		
		if (!contains) {
			Assert.fail("Does not contain: " + testClass + "." + testMethod);
		}
	}

	private static boolean isEqual(Test failedTest, String testClass, String testMethod) {
		final String actualTestClass = failedTest.getClass().getName();
		final String actualTestMethod = ((TestCase) failedTest).getName();
		
		return testClass.equals(actualTestClass) && testMethod.equals(actualTestMethod);
	}
}
