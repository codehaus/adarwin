package org.ajester;

public interface CodeMatcher {
	public boolean matches(String className);
	
	public boolean matches(CodeLocation codeLocation);
}
