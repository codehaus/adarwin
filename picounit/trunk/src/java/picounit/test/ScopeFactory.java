package picounit.test;

import picounit.runner.Scope;

public interface ScopeFactory {
	Scope createClassScope(Object scope);

	Scope createMethodScope(Object scope);
}
