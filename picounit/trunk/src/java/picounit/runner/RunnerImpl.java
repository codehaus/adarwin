package picounit.runner;

import picounit.Runner;
import picounit.test.Filter;

public class RunnerImpl implements Runner {
	private final Registry registry;

	public RunnerImpl(Registry registry) {
		this.registry = registry;
	}

	public Runner registerFixture(Class interfaceClass, Class implementationClass) {
		registry.registerFixture(interfaceClass, implementationClass);
		
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

	public void run(Class testClass) {
		registry.registerTest(testClass);
	}
}
