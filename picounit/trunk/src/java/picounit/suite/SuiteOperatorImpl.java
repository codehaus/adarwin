package picounit.suite;

import picounit.Suite;
import picounit.runner.ResultListener;
import picounit.runner.ScopeImpl;
import picounit.test.Equals;

import java.lang.reflect.Method;

public class SuiteOperatorImpl implements SuiteOperator {
	private final ResultListener resultListener;
	private final MethodRunner methodRunner;

	public SuiteOperatorImpl(ResultListener resultListener, MethodRunner methodRunner) {
		this.resultListener = resultListener;
		this.methodRunner = methodRunner;
	}

	public void operate(Class suiteClass) {
		resultListener.enter(new ScopeImpl(Suite.class, suiteClass));
		
		Method[] methods = suiteClass.getMethods();
		
		for (int index = 0; index < methods.length; index++) {
			Method method = methods[index];
			
			if (method.getName().startsWith("suite")) {
				methodRunner.runWrapped(suiteClass, method, createScope(method));
			}
		}
		
		resultListener.exit();
	}

	private ScopeImpl createScope(Object scope) {
		return new ScopeImpl(Method.class, scope);
	}

	public boolean equals(Object object) {
		return new Equals(this) {
			protected boolean equalsImpl(Object object) {
				SuiteOperatorImpl other = (SuiteOperatorImpl) object;

				return resultListener.equals(other.resultListener) &&
					methodRunner.equals(other.methodRunner);
			}
		}.equals(object);
	}
}
