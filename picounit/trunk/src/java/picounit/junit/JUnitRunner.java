package picounit.junit;

import java.lang.reflect.Method;
import java.util.Collection;

import junit.framework.Test;

public interface JUnitRunner {
	void enterSuite(Method suiteMethod);
	
	void runTest(Method method, Test test, TestResultProxy testResultProxy);
	
	void exitSuite();
}
