package picounit.suite;

import picounit.Suite;
import picounit.runner.Scope;
import picounit.runner.ScopeImpl;

import java.lang.reflect.Method;

public class SuiteScopeFactoryImpl implements SuiteScopeFactory {
	public Scope createClassScope(Object scope) {
		return new ScopeImpl(Suite.class, scope);
	}

	public Scope createMethodScope(Object scope) {
		return new ScopeImpl(Method.class, scope);
	}
}
