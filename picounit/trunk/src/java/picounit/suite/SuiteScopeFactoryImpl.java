package picounit.suite;

import picounit.Suite;
import picounit.impl.ResultListener;
import picounit.impl.ScopeImpl;

import java.lang.reflect.Method;


public class SuiteScopeFactoryImpl implements SuiteScopeFactory {
	private final ResultListener resultListener;

	public SuiteScopeFactoryImpl(ResultListener resultListener) {
		this.resultListener = resultListener;
	}

	public void enterClass(Class someClass) {
		resultListener.enter(new ScopeImpl(Suite.SUITE, Suite.class, someClass));
	}

	public void runMethod(Method method) {
		resultListener.enter(new ScopeImpl(Suite.SUITE_METHOD, Method.class, method));
		
		resultListener.exit();
	}

	public void exit() {
		resultListener.exit();
	}
}
