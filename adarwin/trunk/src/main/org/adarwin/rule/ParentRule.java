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

import org.adarwin.ClassName;
import org.adarwin.ClassSummary;
import org.adarwin.CodeElement;
import org.adarwin.ElementType;
import org.adarwin.Filter;
import org.adarwin.UsesCodeElement;

public class ParentRule implements Rule, Filter {
	private final ClassName className;

	public ParentRule(String fullClassName) {
		this.className = new ClassName(fullClassName);
	}

	public ParentRule(Class clazz) {
		this(clazz.getName());
	}

	public ClassSummary inspect(ClassSummary classSummary) {
		return classSummary.filter(this);
	}

	public boolean matches(CodeElement codeElement) {
		return UsesCodeElement.create(className, ElementType.EXTENDS_OR_IMPLEMENTS).equals(
			codeElement);
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
