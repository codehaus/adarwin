package picounit.test;

import picounit.Test;
import picounit.util.Empty;
import junit.framework.TestCase;

public class TestMatcherImpl extends Empty implements TestMatcher {
	public boolean matches(Class testClass) {
		return Test.class.isAssignableFrom(testClass) ||
			TestCase.class.isAssignableFrom(testClass);
	}
}
