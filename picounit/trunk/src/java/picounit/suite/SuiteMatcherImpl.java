package picounit.suite;

import picounit.Suite;

public class SuiteMatcherImpl implements SuiteMatcher {
	public boolean matches(Class suiteClass) {
		return Suite.class.isAssignableFrom(suiteClass);
	}
}
