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
import org.adarwin.rule.MethodRule;
import org.adarwin.rule.PackageRule;
import org.adarwin.rule.Rule;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public abstract class RuleTestCase extends TestCase {
	public Collection assertNumMatches(int expected, Rule rule, Class clazz) {
		return assertNumMatches(expected, rule, new ClassFile(RuleTestCase.getInputStream(clazz)));
	}

	public Collection assertNumMatches(int expected, Rule rule, Code code) {
		final Set matches = new HashSet();

		code.evaluate(rule, new RuleListener() {
			public boolean matchesEvent(ClassSummary classSummary) {
				if (!classSummary.isEmpty()) {
					matches.add(classSummary);
				}

				return true;
			}
		});

		assertEquals(expected, matches.size());

		return matches;
	}

	public Rule createPackageRule(Class clazz) {
		return new PackageRule(RuleTestCase.packageName(clazz).replaceAll("\\.", "\\."));
	}

	public Rule createClassRule(Class clazz) {
		return new ClassRule(className(clazz).replaceAll("\\.", "\\."));
	}

	public Rule createMethodRule(Method method) {
		return MethodRule.create(method.getReturnType(), method.getMethodName(),
			method.getParameterTypes());
	}

	public MethodDeclaration createMethodDeclaration(String methodName, Class returnType, Class[] parameterTypes) {
		return new MethodDeclaration(Integer.class.getName(), methodName,
			returnType.getName(), RuleTestCase.convertClassArrayToStringArray(parameterTypes));
	}

	public MethodDeclaration createMethodDeclaration(String methodName, Class returnType) {
		return new MethodDeclaration(Integer.class.getName(), methodName,
			returnType.getName(), new String[0]);
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
