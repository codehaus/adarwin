package picounit.around.mock;

import picounit.Runner;
import picounit.Suite;

public class MockAroundSuite implements Suite {
	public void suite(Runner runner) {
		runner.run(MockAroundTest.class);
		runner.run(MockResolverTest.class);
	}
}
