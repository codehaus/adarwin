package picounit.junit;

import picounit.Runner;
import picounit.Suite;

public class JUnitSuite implements Suite {
	public void suite(Runner runner) {
		runner.run(SuiteGeneratorTest.class);
	}
}
