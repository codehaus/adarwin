package picounit.suite;

import picounit.MainRunner;
import picounit.Mocker;
import picounit.Runner;
import picounit.Suite;
import picounit.Test;
import picounit.Verify;
import picounit.impl.MethodRunner;

public class SuiteRunnerTest implements Test {
	// Mocks
	private SuiteMatcher suiteMatcher;
	private SuiteScopeFactory suiteScopeFactory;
	private MethodRunner methodRunner;

	// Fixtures
	private Runner runner;
	private StringBuffer stringBuffer;

	// Unit
	private SuiteRunner suiteRunnerImpl;

	public void mock(SuiteMatcher suiteMatcher, SuiteScopeFactory suiteScopeFactory,
		MethodRunner methodRunner) {
		
		this.suiteMatcher = suiteMatcher;
		this.suiteScopeFactory = suiteScopeFactory;
		this.methodRunner = methodRunner;

		this.stringBuffer = new StringBuffer();
		this.runner = MainRunner.create();
		this.runner.registerFixture(stringBuffer);

		this.suiteRunnerImpl = new SuiteRunner(suiteMatcher, suiteScopeFactory, methodRunner);
	}

	public void testRunVisitsRegisteredTestClasses(Mocker mocker) {
		mocker.expectAndReturn(suiteMatcher.matches(SuiteInstance.class), true);
		methodRunner.invokeMatchingMethods(SuiteInstance.class, "suite", suiteScopeFactory);

		mocker.replay();

		suiteRunnerImpl.registryEvent(SuiteInstance.class);
	}
	
	public void testSuiteInvoked(Verify verify) {
		runner.run(SuiteInstance.class);

		verify.equal("SuiteInstance.suite ", stringBuffer.toString());
	}
	
	public void testTestsAndSuitesReferedToInSuiteInvoked(Verify verify) {
		runner.run(SuiteWithTests.class);
		
		verify.equal("TestInstance.test SuiteInstance.suite ", stringBuffer.toString());
	}

	public static class SuiteInstance implements Suite {
		public void suite(StringBuffer stringBuffer) {
			stringBuffer.append("SuiteInstance.suite ");
		}
	}

	public static class SuiteWithTests implements Suite {
		public void suite(Runner runner) {
			runner.run(TestInstance.class);
			runner.run(SuiteInstance.class);
		}
	}

	public static class TestInstance implements Test {
		public void test(StringBuffer stringBuffer) {
			stringBuffer.append("TestInstance.test ");
		}
	}
}
