package org.ajester;

public class Report {
	private String codeClassName;
	private TestResults results;

	public Report(String codeClassName, TestResults results) {
		this.codeClassName = codeClassName;
		this.results = results;
	}

	public String getReport() {
		return removePackage(codeClassName) + ": " + getProblemString();
	}

	private String removePackage(String className) {
		return className.substring(className.lastIndexOf('.') + 1);
	}

	private String getProblemString() {
		if (results.getErrors().size() == 0 &&
			results.getFailures().size() == 0) {
			return "Some problems";
		}
		else {
			return "No problems";
		}
	}

}
