package picounit.test;

import java.lang.reflect.Method;

import picounit.Mocker;
import picounit.Test;
import picounit.TestInstance;
import picounit.impl.MethodInvoker;
import picounit.impl.ResultListener;
import picounit.impl.Scope;
import picounit.impl.ScopeImpl;

public class TestScopeFactoryTest implements Test {
	// Unit
	private TestScopeFactory testScopeFactory;
	
	// Mocks
	private ResultListener resultListener;
	private MethodInvoker methodInvoker;
	
	// Fixtures
	private Scope testScope = new ScopeImpl(Test.TEST, Test.class, TestInstance.class);
	private Scope testOneScope = new ScopeImpl(Test.TEST_METHOD, Method.class, TestInstance.testOne);

	public void mock(MethodInvoker methodInvoker, ResultListener resultListener) {
		this.methodInvoker = methodInvoker;
		this.resultListener = resultListener;

		this.testScopeFactory = new TestScopeFactoryImpl(methodInvoker, resultListener);
	}
	
	public void testEnterClass(Mocker mocker) {
		resultListener.enter(testScope);

		mocker.replay();

		testScopeFactory.enterClass(TestInstance.class);
	}

	public void testEnterMethod(Mocker mocker) {
		resultListener.enter(testOneScope);
		methodInvoker.invokeMethod(TestInstance.testOne, resultListener);
		resultListener.exit();

		mocker.replay();
		
		testScopeFactory.runMethod(TestInstance.testOne);
	}
	
	public void testExit(Mocker mocker) {
		resultListener.exit();

		mocker.replay();

		testScopeFactory.exit();
	}
}
