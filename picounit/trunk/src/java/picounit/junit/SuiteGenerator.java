package picounit.junit;

import junit.framework.TestSuite;

public class SuiteGenerator {
	public TestSuite generate(Class someClass) {
		return new TestSuite(someClass);
	}
}
