package picounit.impl;


public interface MethodRunner {
	void invokeMatchingMethods(Class someClass, String prefix, ScopeFactory scopeFactory);

	void invokeMethod(Object object, String prefix, Resolver resolver);
}