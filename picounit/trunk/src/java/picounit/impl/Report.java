package picounit.impl;


public interface Report extends ResultListener, RegistryListener {
	Scope[] matching(ScopeFilter scopeFilter);

	int visitedCount();
	int visitedCount(Class type);
	
	int successesCount();
	int successesCount(Class type);

	int failuresCount();
	int failuresCount(Class type);
}
