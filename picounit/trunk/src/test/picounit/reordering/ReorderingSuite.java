package picounit.reordering;

import picounit.MainRunner;
import picounit.Runner;
import picounit.Suite;

public class ReorderingSuite implements Suite {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		new MainRunner().run(ReorderingSuite.class);
	}

	public void suite(Runner runner) {
		runner.run(ReorderTest.class);
	}
}
