package org.ajester;

public class CodeLocationMatcher implements CodeMatcher {
	private final CodeLocation codeLocation;
	
	public CodeLocationMatcher(Class className, String methodName) {
		this(new CodeLocation(className, methodName));
	}

	public CodeLocationMatcher(CodeLocation codeLocation) {
		this.codeLocation = codeLocation;
	}
	
	public boolean matches(String className) {
		return codeLocation.getClassName().equals(className);
	}

	public boolean matches(CodeLocation codeLocation) {
		return this.codeLocation.equals(codeLocation);
	}
}
