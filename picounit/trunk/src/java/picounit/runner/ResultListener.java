package picounit.runner;



public interface ResultListener {
	void enter(Scope scope);

	void exit();

	void exit(Throwable throwable);

	void error(Scope scope, Throwable throwable);
}
