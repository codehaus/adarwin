package picounit.junit;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

public interface TestResultProxy {
	void addFailure(Test test, AssertionFailedError assertionFailedError);
	
	void addError(Test test, Throwable throwable);
}
