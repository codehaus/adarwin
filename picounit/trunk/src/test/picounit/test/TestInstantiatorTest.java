package picounit.test;

import picounit.Test;
import picounit.Verify;

public class TestInstantiatorTest implements Test {
	int count = 0;

	public void testTemp() {
		count = 1;
	}

	public void testTestIsInstantiatedForEachTestMethod(Verify verify) {
		verify.equal(0, count);
	}
}
