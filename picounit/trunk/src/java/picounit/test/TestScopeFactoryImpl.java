package picounit.test;

import picounit.Test;
import picounit.runner.Scope;
import picounit.runner.ScopeImpl;

import java.lang.reflect.Method;

public class TestScopeFactoryImpl implements TestScopeFactory {
	public Scope createClassScope(Object scope) {
		return new ScopeImpl(Test.class, scope);
	}

	public Scope createMethodScope(Object scope) {
		return new ScopeImpl(Method.class, scope);
	}
}
