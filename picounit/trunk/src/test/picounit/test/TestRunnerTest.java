package picounit.test;

import picounit.Mocker;
import picounit.Test;
import picounit.TestInstance;
import picounit.impl.MethodRunner;

public class TestRunnerTest implements Test {
	// Mocks
	private TestMatcher testMatcher;
	private TestScopeFactory testScopeFactory;
	private MethodRunner methodRunner;

	// Unit
	private TestRunner testRunnerImpl;

	public void mock(TestMatcher testMatcher, TestScopeFactory testScopeFactory,
		MethodRunner methodRunner) {
		
		this.testMatcher = testMatcher;
		this.testScopeFactory = testScopeFactory;
		this.methodRunner = methodRunner;

		this.testRunnerImpl = new TestRunner(testMatcher, testScopeFactory, methodRunner );
	}

	public void testRunVisitsRegisteredTestClasses(Mocker mocker) {
		mocker.expectAndReturn(testMatcher.matches(TestInstance.class), true);
		methodRunner.invokeMatchingMethods(TestInstance.class, "test", testScopeFactory);

		mocker.replay();

		testRunnerImpl.registryEvent(TestInstance.class);
	}
}
