package picounit.impl;


public interface ResultListener {
	void enter(Scope scope);

	void exit();

	void exit(Throwable throwable);
}
