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

import java.util.Arrays;


public class AndRule implements Rule {
	private final Rule[] rules;

	public AndRule(Rule[] rules) {
		this.rules = rules;
	}

	public ClassSummary inspect(ClassSummary classSummary) {
		ClassSummary[] summaries = new ClassSummary[rules.length];

		for (int rLoop = 0; rLoop < rules.length; ++rLoop) {
			summaries[rLoop] = rules[rLoop].inspect(classSummary);
		}

		return ClassSummary.and(summaries);
	}

	public int hashCode() {
		return getClass().hashCode();
	}

	public boolean equals(Object object) {
		return object != null &&
			getClass().equals(object.getClass()) &&
				Arrays.equals(rules, ((AndRule) object).rules);
	}
}
