package picounit.suite;

import picounit.MainRunner;
import picounit.Mocker;
import picounit.Runner;
import picounit.Suite;
import picounit.Test;
import picounit.Verify;

public class SuiteRunnerTest implements Test {
	// Mocks
	private SuiteOperator suiteOperator;
	private SuiteMatcher suiteMatcher;
	
	// Fixtures
	private MainRunner picoSuiteRunnerImpl;
	private StringBuffer stringBuffer;

	// Unit
	private SuiteRunner suiteRunnerImpl;

	public void mock(SuiteMatcher suiteMatcher, SuiteOperator suiteOperator) {
		this.suiteMatcher = suiteMatcher;
		this.suiteOperator = suiteOperator;
		
		this.stringBuffer = new StringBuffer();
		this.picoSuiteRunnerImpl = new MainRunner(false);
		this.picoSuiteRunnerImpl.registerFixture(stringBuffer);

		this.suiteRunnerImpl = new SuiteRunner(suiteMatcher, suiteOperator);
	}
	
	public void testRunVisitsRegisteredTestClasses(Mocker mocker) {
		mocker.expectAndReturn(suiteMatcher.matches(SuiteInstance.class), true);
		suiteOperator.operate(SuiteInstance.class);

		mocker.replay();

		suiteRunnerImpl.registryEvent(SuiteInstance.class);
	}
	
	public void testSuiteInvoked(Verify verify) {
		picoSuiteRunnerImpl.run(SuiteInstance.class);

		verify.equal("SuiteInstance.suite ", stringBuffer.toString());
	}
	
	public void testTestsAndSuitesReferedToInSuiteInvoked(Verify verify) {
		picoSuiteRunnerImpl.run(SuiteWithTests.class);
		
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
