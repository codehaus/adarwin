package org.adarwin;

public interface Method {
	String getReturnType();

	String getMethodName();

	String[] getParameterTypes();

	boolean equalsMethod(Method other);
}
