package picounit.impl;

import java.lang.reflect.Method;


public interface MethodInvoker {
	void invokeMethod(Method method, ResultListener resultListener);
}