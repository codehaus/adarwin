package picounit.test;

import picounit.Test;
import picounit.impl.MethodInvoker;
import picounit.impl.ResultListener;
import picounit.impl.ScopeImpl;

import java.lang.reflect.Method;

public class TestScopeFactoryImpl implements TestScopeFactory {
	private final ResultListener resultListener;
	private final MethodInvoker methodInvoker;

	public TestScopeFactoryImpl(MethodInvoker methodInvoker, ResultListener resultListener) {
		this.methodInvoker = methodInvoker;
		this.resultListener = resultListener;
	}

	public void enterClass(Class testClass) {
		resultListener.enter(new ScopeImpl(Test.TEST, Test.class, testClass));
	}

	public void runMethod(Method method) {
		resultListener.enter(new ScopeImpl(Test.TEST_METHOD, Method.class, method));

		System.out.print(".");
		methodInvoker.invokeMethod(method, resultListener);

		resultListener.exit();
	}

	public void exit() {
		resultListener.exit();
	}
}
