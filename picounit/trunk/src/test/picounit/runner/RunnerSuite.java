package picounit.runner;

import picounit.Runner;
import picounit.Suite;
import picounit.suite.SuiteSuite;

public class RunnerSuite implements Suite {
	public void suite(Runner runner) {
		runner.run(RegistryTest.class);
		runner.run(ReportingTest.class);
		runner.run(MainRunnerTest.class);
		runner.run(MethodRunnerTest.class);

		runner.run(SuiteSuite.class);
	}
}
