package picounit.junit;

import picounit.impl.Scope;
import junit.framework.AssertionFailedError;
import junit.framework.Test;

public class JUnitListenerImpl implements JUnitListener {
	private Test test;
	private TestResultProxy testResultProxy;

	public void setContext(Test test, TestResultProxy testResultProxy) {
		this.test = test;
		this.testResultProxy = testResultProxy;
	}

	public void enter(Scope scope) {
	}

	public void exit() {
	}

	public void exit(Throwable throwable) {
		if (throwable instanceof AssertionFailedError) {
			AssertionFailedError assertionFailedError = (AssertionFailedError) throwable;

			testResultProxy.addFailure(test, assertionFailedError);			
		}
		else {
			testResultProxy.addError(test, throwable);
		}
	}
}
