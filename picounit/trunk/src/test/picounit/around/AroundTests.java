package picounit.around;

import picounit.Runner;
import picounit.Suite;
import picounit.around.mock.MockAroundTests;
import picounit.around.setup.SetUpAroundTests;

public class AroundTests implements Suite {
	public void suite(Runner runner) {
		runner.run(AroundMatcherTest.class);
		runner.run(AroundRunnerTest.class);

		runner.run(MockAroundTests.class);
		runner.run(SetUpAroundTests.class);
	}
}
