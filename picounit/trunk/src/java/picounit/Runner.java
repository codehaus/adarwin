package picounit;

import picounit.test.Filter;

public interface Runner {
	Runner registerFixture(Class interfaceClass, Class implementationClass);
	
	Runner registerFixture(Object implementation);

	Runner applyFilter(Filter filter);

	void run(Class testClass);
}
