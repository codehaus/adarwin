package picounit.junit;

import picounit.Runner;
import picounit.Suite;

public class JUnitSuite implements Suite {
	public void suite(Runner runner) {
		runner.run(SuiteGeneratorTest.class);
		runner.run(RecorderTest.class);
		runner.run(RecordingSuiteScopeFactoryTest.class);
		runner.run(RecordingTestScopeFactoryTest.class);
		runner.run(JUnitRunnerTest.class);
		runner.run(JUnitListenerTest.class);
	}
}
