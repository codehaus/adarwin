package picounit.suite;

import picounit.impl.MethodRunner;
import picounit.impl.RegistryListener;

public class SuiteRunner implements RegistryListener {
	private final SuiteMatcher suiteMatcher;
	private final SuiteScopeFactory suiteScopeFactory;
	private final MethodRunner methodRunner;

	public SuiteRunner(SuiteMatcher suiteMatcher, SuiteScopeFactory suiteScopeFactory,
		MethodRunner methodRunner) {
		
		this.suiteMatcher = suiteMatcher;
		this.suiteScopeFactory = suiteScopeFactory;
		this.methodRunner = methodRunner;
	}

	public void registryEvent(Class someClass) {
		if (suiteMatcher.matches(someClass)) {
			methodRunner.invokeMatchingMethods(someClass, "suite", suiteScopeFactory);
		}
	}
}
