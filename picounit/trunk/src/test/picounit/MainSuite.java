package picounit;

import picounit.around.AroundSuite;
import picounit.junit.JUnitSuite;
import picounit.runner.RunnerSuite;
import junit.framework.TestSuite;

public class MainSuite implements Suite {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		new MainRunner().run(MainSuite.class);
	}

	public void suite(Runner runner) {
		runner.run(EmptyTest.class);
		runner.run(MethodInvokerTest.class);
		runner.run(VerifyTest.class);
		
		runner.run(AroundSuite.class);
		runner.run(RunnerSuite.class);
		runner.run(TestSuite.class);
		runner.run(JUnitSuite.class);
	}
}
