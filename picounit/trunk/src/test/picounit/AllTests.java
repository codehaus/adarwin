package picounit;

import picounit.around.AroundTests;
import picounit.junit.JUnitSuite;
import picounit.runner.RunnerTests;
import picounit.test.TestTests;

public class AllTests implements Suite {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		new MainRunner().run(AllTests.class);
	}

	public void suite(Runner runner) {
		runner.run(EmptyTest.class);
		runner.run(MethodInvokerTest.class);
		runner.run(VerifyTest.class);
		
		runner.run(AroundTests.class);
		runner.run(RunnerTests.class);
		runner.run(TestTests.class);
		runner.run(JUnitSuite.class);
	}
}
