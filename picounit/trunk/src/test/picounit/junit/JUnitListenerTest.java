package picounit.junit;

import junit.framework.AssertionFailedError;
import picounit.Mocker;
import picounit.Test;

public class JUnitListenerTest implements Test {
	private JUnitListener junitListener = new JUnitListenerImpl();
	
	// Mocks
	private junit.framework.Test test;
	private TestResultProxy testResultProxy;
	
	// Fixture
	private AssertionFailedError assertionFailedError = new AssertionFailedError();
	private Throwable throwable = new Throwable();

	public void mock(junit.framework.Test test, TestResultProxy testResultProxy) {
		this.test = test;
		this.testResultProxy = testResultProxy;
	}
	
	public void testExitWithAssertionFailedErrorAddsFailure(Mocker mocker) {
		testResultProxy.addFailure(test, assertionFailedError);
		
		mocker.replay();
		
		junitListener.setContext(test, testResultProxy);
		junitListener.exit(assertionFailedError);
	}
	
	public void testExitWithNonAssertionFailedErrorAddsError(Mocker mocker) {
		testResultProxy.addError(test, throwable);
		
		mocker.replay();
		
		junitListener.setContext(test, testResultProxy);
		junitListener.exit(throwable);
	}
}
