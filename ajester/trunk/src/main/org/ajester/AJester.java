package org.ajester;

public class AJester {
	private String testClassName;
	private Mutator mutator;
	private Class codeClass;

	public AJester(Class codeClass, Class testCaseClass, Mutator mutator) {
		this.codeClass = codeClass;
		this.testClassName = testCaseClass.getName();
		this.mutator = mutator;
	}

	public Report run() throws Exception {
		TestRunnerWrapper runnerWrapper = new TestRunnerWrapper();
		TestResults results = runnerWrapper.run(testClassName, mutator);
		return new Report(codeClass.getName(), results);
	}
}
