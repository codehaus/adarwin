package picounit.test;


import picounit.runner.RegistryListener;

public class TestRunner implements RegistryListener {
	private final TestMatcher testMatcher;
	private final TestOperator testOperator;

	public TestRunner(TestMatcher testMatcher, TestOperator testOperator) {
		this.testMatcher = testMatcher;
		this.testOperator = testOperator;
	}

	public void registryEvent(Class someClass) {
		if (testMatcher.matches(someClass)) {
			testOperator.operate(someClass);
		}
	}
}
