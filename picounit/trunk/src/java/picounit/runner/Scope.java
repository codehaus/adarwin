package picounit.runner;


public interface Scope {
	boolean matches(Class filter);

	Object value();

	Class getType();

	void setFailure(Throwable reason);
	
	Throwable getFailure();
}