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

public class NameRule implements Rule {
	private final Rule rule;
	private final String name;

	public NameRule(String name, Rule rule) {
		this.name = name;
		this.rule = rule;
	}

	public String getName() {
		return name;
	}

	public ClassSummary inspect(ClassSummary classSummary) {
		return rule.inspect(classSummary);
	}

	public int hashCode() {
		return getClass().hashCode();
	}

	public boolean equals(Object object) {
		return object != null &&
			getClass().equals(object.getClass()) &&
			name.equals(((NameRule) object).name) &&
			rule.equals(((NameRule) object).rule);
	}
}
