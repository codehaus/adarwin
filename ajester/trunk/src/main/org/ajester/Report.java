package org.ajester;

public class Report {
	public static final String NO_PROBLEMS = "No problems";
	public static final String SOME_PROBLEMS = "Some problems";
	
	private String codeClassName;
	private TestResults results;

	public Report(String codeClassName, TestResults results) {
		this.codeClassName = codeClassName;
		this.results = results;
	}

	public String getReport() {
		return getProblemString() + ": " + removePackage(codeClassName);
	}

	private String removePackage(String className) {
		return className.substring(className.lastIndexOf('.') + 1);
	}

	private String getProblemString() {
		if (results.getErrors().size() == 0 &&
			results.getFailures().size() == 0) {
			
			return SOME_PROBLEMS;
		}
		else {
			return NO_PROBLEMS;
		}
	}
}
