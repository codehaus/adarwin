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

		super(usesClassName);

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
