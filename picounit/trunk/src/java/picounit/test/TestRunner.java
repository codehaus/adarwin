package picounit.test;


import picounit.impl.MethodRunner;
import picounit.impl.RegistryListener;

public class TestRunner implements RegistryListener {
	private final TestMatcher testMatcher;
	private final MethodRunner methodRunner;
	private final TestScopeFactory testScopeFactory;

	public TestRunner(TestMatcher testMatcher, TestScopeFactory testScopeFactory,
		MethodRunner methodRunner) {

		this.testMatcher = testMatcher;
		this.testScopeFactory = testScopeFactory;
		this.methodRunner = methodRunner;
	}

	public void registryEvent(Class someClass) {
		if (testMatcher.matches(someClass)) {
			methodRunner.invokeMatchingMethods(someClass, "test", testScopeFactory);
		}
	}
}
