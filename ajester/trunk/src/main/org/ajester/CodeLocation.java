package org.ajester;

public class CodeLocation implements CodeMatcher {
	private final String className;
	private final String methodName;

	public CodeLocation(Class clazz, String methodName) {
		this(clazz.getName(), methodName);
	}

	public CodeLocation(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
	}
	
	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public boolean matches(String className) {
		return getClassName().equals(className);
	}

	public boolean matches(CodeLocation codeLocation) {
//		System.out.println(this + ".matches(" + codeLocation + ")");
		return equals(codeLocation);
	}

	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(getClass())) {
			return false;
		}

		CodeLocation other = (CodeLocation) obj;

		return (other == this) ||
			(getClassName().equals(other.getClassName()) &&
			 getMethodName().equals(other.getMethodName()));
	}

	public int hashCode() {
		return getClassName().hashCode() ^ getMethodName().hashCode();
	}

	public String toString() {
		return className + "." + methodName;
	}
}
