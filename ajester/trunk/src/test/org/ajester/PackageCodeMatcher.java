package org.ajester;

public class PackageCodeMatcher implements CodeMatcher {
	private final String packageName;
	
	public PackageCodeMatcher(String packageName) {
		this.packageName = packageName;
	}

	public boolean matches(String className) {
		return (className.indexOf(packageName) != -1);
	}

	public boolean matches(CodeLocation codeLocation) {
		return (codeLocation.getClassName().indexOf(packageName) != -1);
	}
}