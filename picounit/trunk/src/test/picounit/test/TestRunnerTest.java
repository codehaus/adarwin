package picounit.test;

import picounit.Mocker;
import picounit.Test;
import picounit.TestInstance;

public class TestRunnerTest implements Test {
	// Mocks
	private TestOperator testOperator;
	private TestMatcher testMatcher;

	// Unit
	private TestRunner testRunnerImpl;

	public void mock(TestMatcher testMatcher, TestOperator testOperator) {
		this.testMatcher = testMatcher;
		this.testOperator = testOperator;

		this.testRunnerImpl = new TestRunner(testMatcher, testOperator);
	}

	public void testRunVisitsRegisteredTestClasses(Mocker mocker) {
		mocker.expectAndReturn(testMatcher.matches(TestInstance.class), true);
		testOperator.operate(TestInstance.class);

		mocker.replay();

		testRunnerImpl.registryEvent(TestInstance.class);
	}
}
