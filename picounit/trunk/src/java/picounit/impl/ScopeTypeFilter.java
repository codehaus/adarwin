package picounit.impl;


public class ScopeTypeFilter implements ScopeFilter {
	private final Class type;

	public ScopeTypeFilter(Class type) {
		this.type = type;
	}

	public boolean matches(Scope scope) {
		return type.equals(scope.getType());
	}
}