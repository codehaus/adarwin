package picounit;


public interface Mocker {
	Object mock(Class interfaceClass);

	Refinement expectAndReturn(Object ignore, Object returnValue);
	
	Refinement expectAndReturn(boolean ignore, boolean returnValue);

	Refinement ignoreReturn(Object ignore);

	void replay();

	void verify();

	void reset();

	Object instanceOf(Class interfaceClass);

	Object stub(Class interfaceClass);

	void useArrayMatcher();
}