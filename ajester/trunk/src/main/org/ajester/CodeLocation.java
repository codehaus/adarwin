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
	
	public boolean shouldMutate(String className) {
		return this.className.equals(className);
	}

	public boolean shouldMutate(String className, String methodName) {
		return this.className.equals(className) &&
			this.methodName.equals(methodName); 
	}

	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}
}
