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
import org.adarwin.RuleClassBindings;


public class NotRule implements Rule {
	private Rule ruleToNegate;

	public NotRule(Rule ruleToNegate) {
		this.ruleToNegate = ruleToNegate;
	}

	public boolean inspect(ClassSummary classSummary) {
		return !ruleToNegate.inspect(classSummary);
	}

	public String toString(RuleClassBindings ruleClassBindings) {
		return ruleClassBindings.getRule(getClass()) + '(' + ruleToNegate.toString(ruleClassBindings) + ')';
	}
}
