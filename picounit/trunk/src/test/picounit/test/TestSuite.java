package picounit.test;

import picounit.Runner;
import picounit.Suite;

public class TestSuite implements Suite {
	public void suite(Runner runner) {
		runner.run(FilterTest.class);
		runner.run(FixtureTest.class);
		runner.run(PicoTest.class);
		runner.run(TestInstantiatorTest.class);
		runner.run(TestMatcherTest.class);
		runner.run(TestScopeFactoryTest.class);
		runner.run(TestRunnerTest.class);
	}
}
