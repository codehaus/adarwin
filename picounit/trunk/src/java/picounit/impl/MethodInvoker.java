package picounit.impl;


public interface MethodInvoker {
	void invokeMatchingMethods(Object object, String pattern, Resolver resolver);
}