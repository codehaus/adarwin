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

public class ParentRule implements Rule, Filter {
	private final String className;

	public ParentRule(String className) {
		this.className = className;
	}

	public ParentRule(Class clazz) {
		this(clazz.getName());
	}

	public ClassSummary inspect(ClassSummary classSummary) {
		return classSummary.filter(this);
	}

	public boolean matches(CodeElement codeElement) {
		return CodeElement.createExtends(className).equals(codeElement);
	}

	public int hashCode() {
		return getClass().hashCode();
	}

	public boolean equals(Object object) {
		return object != null &&
			getClass().equals(object.getClass()) &&
			className.equals(((ParentRule) object).className);
	}
}
