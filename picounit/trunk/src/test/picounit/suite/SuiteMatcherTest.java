package picounit.suite;

import picounit.Suite;
import picounit.Test;
import picounit.Verify;

public class SuiteMatcherTest implements Test {
	// Unit
	private final SuiteMatcher suiteMatcher = new SuiteMatcherImpl();

	public void testSuitePasses(Verify verify) {
		verify.that(suiteMatcher.matches(Suite.class));
	}

	public void testNonSuiteDoesNotPass(Verify verify) {
		verify.not(suiteMatcher.matches(Object.class));
	}
}
