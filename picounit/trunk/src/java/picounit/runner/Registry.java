package picounit.runner;

import picounit.test.Filter;

public interface Registry {
	void registerInfrastructure(Class component);
	void registerInfrastructure(Class interfaceClass, Class implementationClass);

	void registerFixture(Class interfaceClass, Class implementation);
	void registerFixture(Class implementationClass);
	void registerFixture(Class interfaceClass, Object implementation);
	void registerFixture(Object implementation);

	void registerTest(Class testClass);

	void registerOverrideable(Class interfaceClass, Class implementationClass);

	void applyFilter(Filter filter);
	
	void push();
	void pop();
}
