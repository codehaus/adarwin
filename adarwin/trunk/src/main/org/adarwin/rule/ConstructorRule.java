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
import org.adarwin.ConstructorInvocation;
import org.adarwin.Filter;
import org.adarwin.Util;

import java.util.Arrays;

public class ConstructorRule implements Rule, Filter {
	private final String[] parameterTypes;
	private final String className;

	// constructor(className(param1, param2))
	public ConstructorRule(String signature) {
		this(getClassName(signature), getParameterTypes(signature));
	}
	
	public static Rule create(String className, String[] parameterTypes) {
		return new ConstructorRule(className, parameterTypes);
	}
	
	private ConstructorRule(String className, String[] parameterTypes) {
		this.className = className;
		this.parameterTypes = parameterTypes;
	}

	public ClassSummary inspect(ClassSummary classSummary) {
		return classSummary.filter(this); 
	}

	public boolean matches(CodeElement codeElement) {
		return codeElement instanceof ConstructorInvocation &&
			((ConstructorInvocation) codeElement).matches(className, parameterTypes);
	}

	private static String getClassName(String signature) {
		return Util.getToken(0, signature, " (,)");
	}

	private static String[] getParameterTypes(String signature) {
		return Util.getTokens(1, signature, " (,)");
	}

	public int hashCode() {
		return getClass().hashCode();
	}

	public boolean equals(Object object) {
		return object != null &&
			getClass().equals(object.getClass()) &&
			className.equals(((ConstructorRule) object).className) &&
			Arrays.equals(parameterTypes, ((ConstructorRule) object).parameterTypes);
	}
}
