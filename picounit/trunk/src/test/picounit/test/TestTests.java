package picounit.test;

import picounit.Runner;
import picounit.Suite;

public class TestTests implements Suite {
	public void suite(Runner runner) {
		runner.run(FilterTest.class);
		runner.run(FixtureTest.class);
		runner.run(TestMatcherTest.class);
		runner.run(TestOperatorTest.class);
		runner.run(TestRunnerTest.class);
		runner.run(TestInstantiatorTest.class);

		runner.run(PicoTest.class);
	}
}
