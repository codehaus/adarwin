package picounit.suite;


import picounit.runner.Scope;

import java.lang.reflect.Method;

public interface MethodRunner {
	void runWrapped(Class someClass, Method method, Scope scope);
}