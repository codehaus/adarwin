package picounit.suite;

import picounit.Runner;
import picounit.Suite;

public class SuiteSuite implements Suite {
	public void suite(Runner runner) {
		runner.run(SuiteMatcherTest.class);
		runner.run(SuiteOperatorTest.class);
		runner.run(SuiteRunnerTest.class);
	}
}
