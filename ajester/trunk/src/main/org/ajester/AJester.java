package org.ajester;

public class AJester {
	private String codeClassName;
	private String testClassName;
	private MutatingClassAdapter classAdapter;

	public AJester(String codeClassName, String testClassName, MutatingClassAdapter classAdapter) {
		this.codeClassName = codeClassName;
		this.testClassName = testClassName;
		this.classAdapter = classAdapter;
	}

	public Report run() throws Exception {
		TestRunnerWrapper runnerWrapper = new TestRunnerWrapper();
		TestResults results = runnerWrapper.run(testClassName, classAdapter);
		return new Report(codeClassName, results);
	}
}
