package picounit.around;

import picounit.Runner;
import picounit.Suite;
import picounit.around.mock.MockAroundSuite;
import picounit.around.setup.SetUpAroundSuite;

public class AroundSuite implements Suite {
	public void suite(Runner runner) {
		runner.run(AroundMatcherTest.class);
		runner.run(AroundRunnerTest.class);

		runner.run(MockAroundSuite.class);
		runner.run(SetUpAroundSuite.class);
	}
}
