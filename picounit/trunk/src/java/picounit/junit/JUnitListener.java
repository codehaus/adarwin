package picounit.junit;

import picounit.impl.ResultListener;
import junit.framework.Test;

public interface JUnitListener extends ResultListener {
	void setContext(Test test, TestResultProxy testResultProxy);
}
