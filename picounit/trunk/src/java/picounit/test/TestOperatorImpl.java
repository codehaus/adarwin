package picounit.test;


import picounit.runner.ResultListener;
import picounit.suite.MethodRunner;

import java.lang.reflect.Method;

public class TestOperatorImpl implements TestOperator {
	private final ResultListener resultListener;
	private final MethodRunner methodRunner;
	private final TestScopeFactory scopeFactory;

	public TestOperatorImpl(ResultListener resultListener, MethodRunner methodRunner,
		TestScopeFactory testScopeFactory) {

		this.resultListener = resultListener;
		this.methodRunner = methodRunner;
		this.scopeFactory = testScopeFactory;
	}

	public void operate(Class testClass) {
		resultListener.enter(scopeFactory.createClassScope(testClass));

		Method[] methods = testClass.getMethods();

		for (int index = 0; index < methods.length; index++) {
			Method method = methods[index];

			if (method.getName().startsWith("test")) {
				methodRunner.runWrapped(testClass, method, scopeFactory.createMethodScope(method));
			}
		}

		resultListener.exit();
	}

	public boolean equals(Object object) {
		return new Equals(this) {
			public boolean equalsImpl(Object object) {
				TestOperatorImpl other = (TestOperatorImpl) object;

				return other.resultListener.equals(resultListener) &&
					other.methodRunner.equals(methodRunner) &&
					other.scopeFactory.equals(scopeFactory);
			}
		}.equals(object);
	}
}

