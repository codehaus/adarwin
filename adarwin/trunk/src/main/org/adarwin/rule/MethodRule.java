/*****************************************************************************
 * Copyright (C) aDarwin Organisation. All rights reserved.                  *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Idea and Original Code by Stacy Curl                                      *
 *****************************************************************************/

package org.adarwin.rule;

import org.adarwin.ClassSummary;
import org.adarwin.CodeElement;
import org.adarwin.Filter;
import org.adarwin.Method;
import org.adarwin.Util;

import java.util.Arrays;
import java.util.regex.Pattern;

public class MethodRule implements Rule, Filter {
	private final String returnType;
	private final String methodName;
	private final String[] parameterTypes;
	
	public static Rule create(String returnType, String methodName, String[] parameterTypes) {
		return new MethodRule(returnType, methodName, parameterTypes);
	}

	public MethodRule(String signature) {
		this(getReturnType(signature), getMethodName(signature), getParameterTypes(signature));
	}

	private MethodRule(String returnType, String methodName, String[] parameterTypes) {
		this.returnType = returnType;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
	}
	
	public ClassSummary inspect(ClassSummary classSummary) {
		return classSummary.filter(this); 
	}

	public boolean matches(CodeElement codeElement) {
		return codeElement instanceof Method &&
			matchesMethod((Method) codeElement);
	}

	private static String getReturnType(String signature) {
		return Util.getToken(0, signature, " (,)");
	}

	private static String getMethodName(String signature) {
		return Util.getToken(1, signature, " (,)");
	}

	private static String[] getParameterTypes(String signature) {
		return Util.getTokens(2, signature, " (,)");
	}

	private boolean matchesMethod(Method method) {
		return Pattern.matches(returnType, method.getReturnType()) &&
			Pattern.matches(methodName, method.getMethodName()) &&
			Util.matchesPatterns(parameterTypes, method.getParameterTypes());
	}

	public int hashCode() {
		return getClass().hashCode();
	}

	public boolean equals(Object object) {
		return object != null &&
			getClass().equals(object.getClass()) &&
			returnType.equals(((MethodRule) object).returnType) &&
			methodName.equals(((MethodRule) object).methodName) &&
			Arrays.equals(parameterTypes, ((MethodRule) object).parameterTypes);
	}
}
