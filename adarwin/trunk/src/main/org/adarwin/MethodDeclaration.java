/*****************************************************************************
 * Copyright (C) aDarwin Organisation. All rights reserved.                  *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Idea and Original Code by Stacy Curl                                      *
 *****************************************************************************/

package org.adarwin;


import java.util.Arrays;

public class MethodDeclaration extends CodeElement implements Method {
	private final String methodName;
	private final String returnType;
	private final String[] parameterTypes;

	public MethodDeclaration(String className, String methodName, String returnType,
		String[] parameterTypes)  {

		super(className);
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.returnType = returnType;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getReturnType() {
		return returnType;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public boolean equals(Object object) {
		if (object == null ||
			!object.getClass().equals(getClass())) {
			return false;
		}

		if (object == this) {
			return true;
		}

		return equalsMethod((MethodDeclaration) object);
	}

	public String toString() {
		StringBuffer buffer =
			new StringBuffer(getReturnType() + " " + getMethodName() + '(');

		Util.appendArray(buffer, parameterTypes);

		buffer.append(')');

		return buffer.toString();
	}

	public boolean equalsMethod(Method other) {
		return getReturnType().equals(other.getReturnType()) &&
			getMethodName().equals(other.getMethodName()) &&
			Arrays.equals(getParameterTypes(), other.getParameterTypes());
	}
}
