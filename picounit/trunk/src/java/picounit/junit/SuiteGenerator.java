package picounit.junit;

import picounit.MainRunner;
import junit.framework.TestSuite;

public class SuiteGenerator {
	public TestSuite generate(Class someClass) {
		JUnitListener junitListener = new JUnitListener();

		MainRunner.create().run(someClass, junitListener);
		
		return junitListener.getTestSuite();
	}
}
