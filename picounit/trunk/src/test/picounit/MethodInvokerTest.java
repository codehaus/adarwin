package picounit;

import picounit.impl.MethodInvokerImpl;
import picounit.impl.Resolver;

public class MethodInvokerTest implements Test {
	// Mocks
	private TestInstance testInstance;
	private Resolver resolver;
	private Dependancy dependancy;

	public void mock(TestInstance testInstance, Resolver resolver, Dependancy dependancy) {
		this.testInstance = testInstance;
		this.resolver = resolver;
		this.dependancy = dependancy;
	}

	public void testInvokesAllMatchingMethods(Mocker mocker) {
		mocker.expectAndReturn(
			resolver.getComponents(new Class[0]), new Object[0]).useArrayMatcher();
		testInstance.testOne();

		mocker.expectAndReturn(
			resolver.getComponents(new Class[] {Dependancy.class}), new Object[] {dependancy});
		testInstance.testTwo(dependancy);

		mocker.replay();

		new MethodInvokerImpl().invokeMatchingMethods(testInstance, "test", resolver);
	}
}
