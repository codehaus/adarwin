package picounit.impl;


public class PassingTypeFilter implements ScopeFilter {
	private final ScopeFilter scopeTypeFilter;
	
	public PassingTypeFilter(Class type) {
		this.scopeTypeFilter = new ScopeTypeFilter(type);
	}
	
	public boolean matches(Scope scope) {
		return scopeTypeFilter.matches(scope) &&
			ScopeFilter.PASSING_FILTER.matches(scope);
	}
}