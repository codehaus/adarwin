package picounit.impl;

import java.lang.reflect.Method;

public interface ScopeFactory {
	void enterClass(Class testClass);

	void runMethod(Method method);

	void exit();
}
