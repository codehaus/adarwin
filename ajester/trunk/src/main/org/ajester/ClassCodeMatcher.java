package org.ajester;

public class ClassCodeMatcher implements CodeMatcher {
	private final String className;

	public ClassCodeMatcher(final String className) {
		this.className = className;
	}

	public boolean matches(String className) {
		return getClassName().equals(className);
	}

	public boolean matches(CodeLocation codeLocation) {
		return getClassName().equals(className);
	}
	
	private String getClassName() {
		return className;
	}
}
