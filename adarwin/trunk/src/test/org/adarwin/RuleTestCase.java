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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public abstract class RuleTestCase extends TestCase {
	public Collection assertNumMatches(int expected, Rule rule, Class clazz) throws ADarwinException {
		return assertNumMatches(expected, rule, new ClassFile(Util.getInputStream(clazz)));
	}

	public Collection assertNumMatches(int expected, Rule rule, Code code) throws ADarwinException {
		final Set matches = new HashSet();

		code.evaluate(rule, new RuleListener() {
			public boolean matchesEvent(ClassSummary classSummary, Rule rule, Code code) {
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
		return new PackageRule(Util.packageName(clazz).replaceAll("\\.", "\\."));
	}

	public Rule createClassRule(Class clazz) {
		return new ClassRule(Util.className(clazz).replaceAll("\\.", "\\."));
	}

	public Rule createMethodRule(Method method) {
		return MethodRule.create(method.getReturnType(), method.getMethodName(),
			method.getParameterTypes());
	}

	public MethodDeclaration createMethodDeclaration(String methodName, Class returnType, Class[] parameterTypes) {
		return new MethodDeclaration(new ClassName(Integer.class.getName()), methodName,
			returnType.getName(), Util.convertClassArrayToStringArray(parameterTypes));
	}

	public MethodDeclaration createMethodDeclaration(String methodName, Class returnType) {
		return new MethodDeclaration(new ClassName(Integer.class.getName()), methodName,
			returnType.getName(), new String[0]);
	}
}
