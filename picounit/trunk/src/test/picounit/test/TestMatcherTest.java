package picounit.test;

import picounit.Test;
import picounit.Verify;

public class TestMatcherTest implements Test {
	// Unit
	private final TestMatcher testMatcher = new TestMatcherImpl();

	public void testTestPasses(Verify verify) {
		verify.that(testMatcher.matches(Test.class));
	}

	public void testNonTestDoesNotPass(Verify verify) {
		verify.not(testMatcher.matches(Object.class));
	}
}
