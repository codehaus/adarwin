package picounit.suite;

import picounit.Mocker;
import picounit.Suite;
import picounit.Test;
import picounit.impl.MethodInvoker;
import picounit.impl.ResultListener;
import picounit.impl.Scope;
import picounit.impl.ScopeImpl;

import java.lang.reflect.Method;

public class SuiteScopeFactoryTest implements Test {
	// Unit
	private SuiteScopeFactory suiteScopeFactory;
	
	// Mocks
	private ResultListener resultListener;
	private MethodInvoker methodInvoker;
	
	// Fixtures
	private Scope suiteScope = new ScopeImpl(Suite.SUITE, Suite.class, SuiteInstance.class);
	private Scope suiteOneScope = new ScopeImpl(Suite.SUITE_METHOD, Method.class, SuiteInstance.suiteOne);

	public void mock(MethodInvoker methodInvoker, ResultListener resultListener) {
		this.suiteScopeFactory = new SuiteScopeFactoryImpl(methodInvoker, resultListener);

		this.methodInvoker = methodInvoker;
		this.resultListener = resultListener;
	}
	
	public void testEnterClass(Mocker mocker) {
		resultListener.enter(suiteScope);

		mocker.replay();

		suiteScopeFactory.enterClass(SuiteInstance.class);
	}

	public void testEnterMethod(Mocker mocker) {
		resultListener.enter(suiteOneScope);
		methodInvoker.invokeMethod(SuiteInstance.suiteOne, resultListener);
		resultListener.exit();

		mocker.replay();

		suiteScopeFactory.runMethod(SuiteInstance.suiteOne);
	}
	
	public void testExit(Mocker mocker) {
		resultListener.exit();
		
		mocker.replay();
		
		suiteScopeFactory.exit();
	}
}
