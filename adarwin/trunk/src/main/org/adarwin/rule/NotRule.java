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


public class NotRule implements Rule {
	private Rule ruleToNegate;

	public NotRule(Rule ruleToNegate) {
		this.ruleToNegate = ruleToNegate;
	}

	public ClassSummary inspect(ClassSummary classSummary) {
		return ruleToNegate.inspect(classSummary).negate(classSummary);
	}

	public int hashCode() {
		return getClass().hashCode();
	}

	public boolean equals(Object object) {
		return object != null &&
			getClass().equals(object.getClass()) &&
			ruleToNegate.equals(((NotRule) object).ruleToNegate);
	}
}
