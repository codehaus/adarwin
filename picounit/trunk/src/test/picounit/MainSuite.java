package picounit;

import picounit.around.AroundSuite;
import picounit.junit.JUnitSuite;
import picounit.runner.RunnerSuite;
import picounit.test.TestSuite;

public class MainSuite implements Suite {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		MainRunner.create().run(MainSuite.class).print();
	}

	public void suite(Runner runner) {
		runner.run(VerifyTest.class);

		runner.run(AroundSuite.class);
		runner.run(RunnerSuite.class);
		runner.run(TestSuite.class);
		runner.run(JUnitSuite.class);
	}
}
