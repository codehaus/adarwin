package picounit.suite;

import picounit.Suite;
import picounit.impl.MethodInvoker;
import picounit.impl.ResultListener;
import picounit.impl.ScopeImpl;

import java.lang.reflect.Method;

public class SuiteScopeFactoryImpl implements OrdinarySuiteScopeFactory {
	private final ResultListener resultListener;
	private final MethodInvoker methodInvoker;

	public SuiteScopeFactoryImpl(MethodInvoker methodInvoker, ResultListener resultListener) {
		this.methodInvoker = methodInvoker;
		this.resultListener = resultListener;
	}

	public void enterClass(Class testClass) {
		resultListener.enter(new ScopeImpl(Suite.SUITE, Suite.class, testClass));
	}

	public void runMethod(Method method) {
		resultListener.enter(new ScopeImpl(Suite.SUITE_METHOD, Method.class, method));

		methodInvoker.invokeMethod(method, resultListener);

		resultListener.exit();
	}

	public void exit() {
		resultListener.exit();
	}
}
