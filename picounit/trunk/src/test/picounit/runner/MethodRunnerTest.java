package picounit.runner;

import picounit.Mocker;
import picounit.Test;
import picounit.TestInstance;
import picounit.impl.MethodRunner;
import picounit.impl.MethodRunnerImpl;
import picounit.impl.ScopeFactory;


public class MethodRunnerTest implements Test {
	// Mocks
	private ScopeFactory scopeFactory;

	// Unit
	private MethodRunner methodRunner;

	public void mock(ScopeFactory scopeFactory) {
		this.scopeFactory = scopeFactory;
		
		this.methodRunner = new MethodRunnerImpl();
	}
	
	public void testInvokeMatchingMethods(Mocker mocker) {
		scopeFactory.enterClass(TestInstance.class);

		scopeFactory.runMethod(TestInstance.testOne);
		scopeFactory.runMethod(TestInstance.testTwo);

		scopeFactory.exit();

		mocker.replay();

		methodRunner.invokeMatchingMethods(TestInstance.class, "test", scopeFactory);
	}
	
	public void testInvokeMethod() {
		//void invokeMethod(Object object, String prefix, Resolver resolver);
	}
	
	public void testInvokeMethod2() {
		//void invokeMethod(Class someClass, Method method, Scope scope);	
	}
}
