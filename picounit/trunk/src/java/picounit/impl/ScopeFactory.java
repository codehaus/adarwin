package picounit.impl;

import java.lang.reflect.Method;

public interface ScopeFactory {
	void enterClass(Class someClass);

	void runMethod(Method method);

	void exit();
}
