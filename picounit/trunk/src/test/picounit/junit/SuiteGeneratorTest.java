package picounit.junit;

import junit.framework.TestSuite;
import picounit.Test;
import picounit.Verify;

public class SuiteGeneratorTest implements Test {
	// Unit
	private SuiteGenerator suiteGenerator = new SuiteGenerator();

	public void testGenerate(Verify verify) {
		TestSuite testSuite = suiteGenerator.generate(SingleTestMethod.class);

		verify.equal(SingleTestMethod.class.getName(), testSuite.getName());
		verify.equal(1, testSuite.countTestCases());
	}
	
	public static class SingleTestMethod implements Test {
		public void testSomething() {
		}
	}
}
