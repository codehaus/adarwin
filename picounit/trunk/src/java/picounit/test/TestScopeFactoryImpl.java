package picounit.test;

import picounit.Test;
import picounit.impl.ResultListener;
import picounit.impl.ScopeImpl;

import java.lang.reflect.Method;

public class TestScopeFactoryImpl implements TestScopeFactory {
	private final ResultListener resultListener;

	public TestScopeFactoryImpl(ResultListener resultListener) {
		this.resultListener = resultListener;
	}

	public void enterClass(Class someClass) {
		resultListener.enter(new ScopeImpl(Test.TEST, Test.class, someClass));
	}

	public void runMethod(Method method) {
		resultListener.enter(new ScopeImpl(Test.TEST_METHOD, Method.class, method));

		resultListener.exit();
	}

	public void exit() {
		resultListener.exit();
	}
}
