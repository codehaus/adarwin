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

public class PackageRule implements Rule, Filter {
	private final String pattern;

	public PackageRule(String pattern) {
		this.pattern = pattern;
	}

	public ClassSummary inspect(ClassSummary classSummary) {
		if (classSummary.packageMatches(pattern)) {
			return classSummary;
		}
		else {
			return classSummary.filter(this);
		}
	}

	public boolean matches(CodeElement codeElement) {
		return codeElement.isUses() && codeElement.packageMatches(pattern);
	}

	public int hashCode() {
		return getClass().hashCode();
	}

	public boolean equals(Object object) {
		return object != null &&
			getClass().equals(object.getClass()) &&
			pattern.equals(((PackageRule) object).pattern);
	}
}
