package picounit.runner;

public class FailingTypeFilter implements ScopeFilter {
	private ScopeFilter scopeTypeFilter;

	public FailingTypeFilter(Class type) {
		this.scopeTypeFilter = new ScopeTypeFilter(type);
	}
	
	public boolean matches(Scope scope) {
		return scopeTypeFilter.matches(scope) &&
			ScopeFilter.FAILING_FILTER.matches(scope);
	}
}