package picounit.impl;

import picounit.MainRunner;
import picounit.Runner;

public class RunnerImpl implements Runner {
	private final MainRunner mainRunner;

	public RunnerImpl(MainRunner mainRunner) {
		this.mainRunner = mainRunner;
	}

	public Runner registerFixture(Class interfaceClass, Class implementationClass) {
		mainRunner.registerFixture(interfaceClass, implementationClass);

		return this;
	}

	public Runner registerFixture(Class implementationClass) {
		mainRunner.registerFixture(implementationClass);
		
		return this;
	}
	
	public Runner registerFixture(Class interfaceClass, Object implementation) {
		mainRunner.registerFixture(interfaceClass, implementation);
		
		return this;
	}

	public Runner registerFixture(Object implementation) {
		mainRunner.registerFixture(implementation);
		
		return this;
	}

	public Runner applyFilter(Filter filter) {
		mainRunner.applyFilter(filter);

		return this;
	}

	public Runner run(Class testClass) {
		mainRunner.run(testClass);

		return this;
	}

	public Runner run(Class testClass, ResultListener resultListener) {
		mainRunner.run(testClass, resultListener);

		return this;
	}

	public void print() {
		mainRunner.print();
	}
}
