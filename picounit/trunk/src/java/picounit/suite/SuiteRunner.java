package picounit.suite;

import picounit.runner.RegistryListener;

public class SuiteRunner implements RegistryListener {
	private final SuiteMatcher suiteMatcher;
	private final SuiteOperator suiteOperator;

	public SuiteRunner(SuiteMatcher suiteMatcher, SuiteOperator suiteOperator) {
		this.suiteMatcher = suiteMatcher;
		this.suiteOperator = suiteOperator;
	}

	public void registryEvent(Class someClass) {
		if (suiteMatcher.matches(someClass)) {
			suiteOperator.operate(someClass);
		}
	}
}
