package picounit.impl;


public interface Scope {
	boolean matches(Class filter);

	Object value();

	Class getType();

	void setFailure(Throwable reason);
	
	Throwable getFailure();
}