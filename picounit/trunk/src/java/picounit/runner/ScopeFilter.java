package picounit.runner;

interface ScopeFilter {
	public static final ScopeFilter PASSING_FILTER = new ScopeFilter() {
		public boolean matches(Scope scope) {
			return scope.getFailure() == null;
		}
	};
	public static final ScopeFilter FAILING_FILTER = new ScopeFilter() {
		public boolean matches(Scope scope) {
			return !PASSING_FILTER.matches(scope); 
		}
	};

	boolean matches(Scope scope);
}