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
import java.util.regex.Pattern;

public class Method extends CodeElement {
	private final String methodName;
	private final String returnType;
	private final String[] parameterTypes;

	public static CodeElement createDeclaration(String className, String returnType, 
		String methodName, String[] parameterTypes) {

		return new Method(className, methodName, returnType, parameterTypes, SOURCE);
	}

	public static CodeElement createInvocation(String className, String returnType, 
		String methodName, String[] parameterTypes) {

		return new Method(className, methodName, returnType, parameterTypes, USES);
	}

	private Method(String className, String methodName, String returnType, String[] parameterTypes,
		int codeType)  {

		super(className, codeType);
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.returnType = returnType;
	}

	public int hashCode() {
		return getClass().hashCode();
	}

	public boolean equals(Object object) {
		if (object == null ||
			!object.getClass().equals(getClass())) {
			return false;
		}

		if (object == this) {
			return true;
		}
		
		Method other = (Method) object;

		return returnType.equals(other.returnType) &&
			methodName.equals(other.methodName) &&
			Arrays.equals(parameterTypes, other.parameterTypes);
	}

	public boolean matches(String returnType, String methodName, String[] parameterTypes) {
		return Pattern.matches(this.returnType, returnType) &&
			Pattern.matches(this.methodName, methodName) &&
			Util.matchesPatterns(this.parameterTypes, parameterTypes);
	}
}
