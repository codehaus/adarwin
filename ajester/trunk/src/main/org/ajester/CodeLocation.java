package org.ajester;

public class CodeLocation {
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
