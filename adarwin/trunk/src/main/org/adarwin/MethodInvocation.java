package org.adarwin;

import org.adarwin.rule.ElementType;

import java.util.Arrays;

public class MethodInvocation extends UsesCodeElement implements Method {
	private final String returnType;
	private final String methodName;
	private final String[] parameterTypes;

	public static MethodInvocation create(ClassName usesClassName, String returnType,
		String methodName, String[] parameterTypes) {

		return new MethodInvocation(usesClassName, returnType, methodName, parameterTypes);
	}

	private MethodInvocation(ClassName usesClassName, String returnType,
		String methodName, String[] parameterTypes) {

		super(usesClassName, ElementType.USES);

		this.returnType = returnType;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
	}

	public String getReturnType() {
		return returnType;
	}

	public String getMethodName() {
		return methodName;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}

	public boolean equalsMethod(Method other) {
		return getReturnType().equals(other.getReturnType()) &&
			getMethodName().equals(other.getMethodName()) &&
			Arrays.equals(getParameterTypes(), other.getParameterTypes());
	}

	public String toString() {
		return "MethodInvocation(" + getReturnType() + " " + getMethodName() + "...)";
	}
}
