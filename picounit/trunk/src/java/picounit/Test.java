package picounit;

import picounit.impl.Scope;

public interface Test {
	public static final String TEST = "Test";
	public static final String TEST_METHOD = "Test-Method";
	
	public static final Scope SCOPE = new Scope() {
		public boolean matches(String typeNameFilter) {
			return false;
		}

		public boolean matches(Class filter) {
			return false;
		}

		public Object value() {
			return null;
		}

		public Class getType() {
			return null;
		}

		public void setFailure(Throwable reason) {
		}

		public Throwable getFailure() {
			return null;
		}
	};
}
