package picounit.impl;

import picounit.Runner;

public class RunnerImpl implements Runner {
	private final Registry registry;
	private final DelegatingResultListener delegatingResultListener;

	public RunnerImpl(Registry registry, DelegatingResultListener delegatingResultListener) {
		this.registry = registry;
		this.delegatingResultListener = delegatingResultListener;
	}

	public Runner registerFixture(Class interfaceClass, Class implementationClass) {
		registry.registerFixture(interfaceClass, implementationClass);

		return this;
	}

	public Runner registerFixture(Class implementationClass) {
		registry.registerFixture(implementationClass);

		return this;
	}
	
	public Runner registerFixture(Class interfaceClass, Object implementation) {
		registry.registerFixture(interfaceClass, implementation);
		
		return this;
	}

	public Runner registerFixture(Object implementation) {
		registry.registerFixture(implementation);
		
		return this;
	}

	public Runner applyFilter(Filter filter) {
		registry.applyFilter(filter);

		return this;
	}

	public Runner run(Class testClass) {
		runImpl(testClass);

		return this;
	}

	public Runner run(Class testClass, ResultListener resultListener) {
		ResultListener previous = delegatingResultListener.setDelegate(resultListener);

		runImpl(testClass);

		delegatingResultListener.setDelegate(previous);

		return this;
	}

	public void print() {
		System.out.println(delegatingResultListener.getDelegate());
	}

	private void runImpl(Class someClass) {
		registry.registerTest(someClass);
	}
}
