package example;

import picounit.MainRunner;
import picounit.Runner;
import picounit.Suite;

public class KissingTests implements Suite {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		new MainRunner().run(KissingTests.class);
	}

	public void suite(Runner runner) {
		runner.run(GirlTest.class);
	}
}
