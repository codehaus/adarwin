package picounit.junit;

import picounit.Runner;
import picounit.Suite;
import picounit.Test;
import picounit.Verify;
import junit.framework.TestSuite;

public class SuiteGeneratorTest implements Test {
	// Unit
	private SuiteGenerator suiteGenerator = new SuiteGenerator();

	public void testGenerateTestSuiteForSingleTest(Verify verify) {
//		System.out.println("\n testGenerateTestSuiteForSingleTest");
		TestSuite testSuite = suiteGenerator.generate(SingleTestMethod.class);
//		verify.equal(SingleTestMethod.class.getName(), testSuite.getName());
//		verify.equal(1, testSuite.countTestCases());
	}

	public void testGenerateTestSuiteForSingleSuite(Verify verify) {
//		System.out.println("\n testGenerateTestSuiteForSingleSuite");

		TestSuite testSuite = suiteGenerator.generate(SuiteWithSingleTest.class);
	}
	
	public static class SingleTestMethod implements Test {
		public void testSomething() {
		}
	}
	
	public static class SuiteWithSingleTest implements Suite {
		public void suite(Runner runner) {
			runner.run(SingleTestMethod.class);
		}
	}
}
