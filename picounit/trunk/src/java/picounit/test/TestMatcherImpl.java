package picounit.test;

import picounit.Test;
import junit.framework.TestCase;

public class TestMatcherImpl implements TestMatcher {
	public boolean matches(Class testClass) {
		return Test.class.isAssignableFrom(testClass) ||
			TestCase.class.isAssignableFrom(testClass);
	}
}
