package picounit.impl;


public interface ScopeFactory {
	Scope createClassScope(Object scope);

	Scope createMethodScope(Object scope);
}
