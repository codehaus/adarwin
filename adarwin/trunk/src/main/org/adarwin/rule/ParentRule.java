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
import org.adarwin.RuleClassBindings;
import org.adarwin.Util;

public class ParentRule implements Rule {
	private String packageName;
	private String className;

	public ParentRule(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
	}

	public ParentRule(Class clazz) {
		this(Util.packageName(clazz), Util.className(clazz));
	}

	public boolean inspect(ClassSummary classSummary) {
		return classSummary.getDependancies().contains(new CodeElement(packageName + "." + className,
			ElementType.EXTENDS_OR_IMPLEMENTS));
	}

	public String getExpression(RuleClassBindings ruleClassBindings) {
		return ruleClassBindings.getRule(getClass()) + '(' + packageName + ", " + className + ')';
	}
}
