package org.ajester;

public class AJester {
	private String codeClassName;
	private String testClassName;
	private MutatingCodeAdapter codeAdapter;

	public AJester(String codeClassName, String testClassName, MutatingCodeAdapter codeAdapter) {
		this.codeClassName = codeClassName;
		this.testClassName = testClassName;
		this.codeAdapter = codeAdapter;
	}

	public Report run() throws Exception {
		TestRunnerWrapper runnerWrapper = new TestRunnerWrapper();
		TestResults results = runnerWrapper.run(testClassName, codeAdapter);
		return new Report(codeClassName, results);
	}
}
