package picounit.around;

import picounit.Test;
import picounit.Verify;

public class AroundMatcherTest implements Test {
	// Unit
	private AroundMatcher aroundMatcher = new AroundMatcherImpl();
	
	public void testTestPasses(Verify verify) {
		verify.that(aroundMatcher.matches(Around.class));
	}

	public void testNonTestDoesNotPass(Verify verify) {
		verify.not(aroundMatcher.matches(Object.class));
	}
}
