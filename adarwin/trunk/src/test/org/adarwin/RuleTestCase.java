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

import org.adarwin.rule.ClassRule;
import org.adarwin.rule.PackageRule;
import org.adarwin.rule.Rule;

import java.io.InputStream;

import junit.framework.TestCase;

public abstract class RuleTestCase extends TestCase {
	public boolean matches(Rule rule, Class clazz) {
		ClassSummary summary = RuleClassVisitor.visit(RuleTestCase.getInputStream(clazz));

		return !rule.inspect(summary).isEmpty();
	}
	
	public Rule createPackageRule(Class clazz) {
		return new PackageRule(RuleTestCase.packageName(clazz).replaceAll("\\.", "\\."));
	}

	public Rule createClassRule(Class clazz) {
		return new ClassRule(className(clazz).replaceAll("\\.", "\\."));
	}

	public CodeElement createMethodDeclaration(String methodName, Class returnType, Class[] parameterTypes) {
		return Method.createDeclaration(Integer.class.getName(), returnType.getName(),
			methodName, RuleTestCase.convertClassArrayToStringArray(parameterTypes));
	}

	public CodeElement createMethodDeclaration(String methodName, Class returnType) {
		return Method.createDeclaration(Integer.class.getName(), returnType.getName(),
			methodName, new String[0]);
	}

	public static InputStream getInputStream(Class clazz) {
		return clazz.getResourceAsStream(className(clazz) + ".class");
	}

	public static String className(Class aClass) {
		return Util.className(aClass.getName());
	}

	public static String packageName(Class clazz) {
		return Util.packageName(clazz.getName());
	}

	public static String[] convertClassArrayToStringArray(Class[] classParameterTypes) {
		String[] parameterTypes = new String[classParameterTypes.length];
		
		for (int cLoop = 0; cLoop < classParameterTypes.length; ++cLoop) {
			parameterTypes[cLoop] = classParameterTypes[cLoop].getName();
		}
		
		return parameterTypes;
	}
}
