package picounit.test;

import picounit.impl.Equals;
import picounit.impl.MethodRunner;
import picounit.impl.ResultListener;

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
		return equals.equals(this, object);
	}

	private static final Equals equals = new Equals() {
		protected boolean equalsImpl(Object lhs, Object rhs) {
			TestOperatorImpl left = (TestOperatorImpl) lhs;
			TestOperatorImpl right = (TestOperatorImpl) rhs;

			return left.resultListener.equals(right.resultListener) &&
				left.methodRunner.equals(right.methodRunner) &&
				left.scopeFactory.equals(right.scopeFactory);
		}
	};
}

