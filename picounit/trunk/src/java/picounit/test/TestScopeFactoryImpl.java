package picounit.test;

import picounit.Test;
import picounit.impl.Scope;
import picounit.impl.ScopeImpl;

import java.lang.reflect.Method;

public class TestScopeFactoryImpl implements TestScopeFactory {
	public Scope createClassScope(Object scope) {
		return new ScopeImpl(Test.class, scope);
	}

	public Scope createMethodScope(Object scope) {
		return new ScopeImpl(Method.class, scope);
	}
}
