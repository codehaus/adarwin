package picounit.junit;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestResult;

public class TestResultProxyImpl implements TestResultProxy {
	private final TestResult testResult;

	public TestResultProxyImpl(TestResult testResult) {
		this.testResult = testResult;
	}

	public void addFailure(Test test, AssertionFailedError assertionFailedError) {
		testResult.addFailure(test, assertionFailedError);
	}

	public void addError(Test test, Throwable throwable) {
		testResult.addError(test, throwable);
	}
}
