package picounit;

import picounit.impl.Filter;
import picounit.impl.ResultListener;

public interface Runner {
	Runner registerFixture(Class interfaceClass, Class implementationClass);
	
	Runner registerFixture(Class implementationClass);
	
	Runner registerFixture(Class interfaceClass, Object implementation);
	
	Runner registerFixture(Object implementation);

	Runner applyFilter(Filter filter);

	Runner run(Class testClass);
	
	Runner run(Class testClass, ResultListener resultListener);

	void print();
}
