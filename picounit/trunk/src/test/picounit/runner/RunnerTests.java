package picounit.runner;

import picounit.Runner;
import picounit.Suite;
import picounit.suite.SuiteRunnerTests;

public class RunnerTests implements Suite {
	public void suite(Runner runner) {
		runner.run(RegistryTest.class);
		runner.run(ReportTest.class);
		runner.run(ReportingTest.class);
		runner.run(MainRunnerTest.class);

		runner.run(SuiteRunnerTests.class);
	}
}
