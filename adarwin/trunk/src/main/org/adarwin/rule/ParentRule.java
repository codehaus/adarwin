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

public class ParentRule implements Rule {
	private String fullyQualifiedClassName;

	public ParentRule(String fullyQualifiedClassName) {
		this.fullyQualifiedClassName = fullyQualifiedClassName;
	}

	public ParentRule(Class clazz) {
		this(clazz.getName());
	}

	public boolean inspect(ClassSummary classSummary) {
		return classSummary.getDependancies().contains(new CodeElement(fullyQualifiedClassName,
			ElementType.EXTENDS_OR_IMPLEMENTS));
	}

	public String toString(RuleClassBindings ruleClassBindings) {
		return ruleClassBindings.getRule(getClass()) + '(' + fullyQualifiedClassName + ')';
	}
}
