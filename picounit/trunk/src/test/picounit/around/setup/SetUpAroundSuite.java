package picounit.around.setup;

import picounit.Runner;
import picounit.Suite;

public class SetUpAroundSuite implements Suite {
	public void suite(Runner runner) {
		runner.run(SetUpAroundTest.class);
	}
}
