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


public class AndRule implements Rule {
	private Rule[] rules;

	public AndRule(Rule[] rules) {
		this.rules = rules;
	}

	public boolean inspect(ClassSummary classSummary) {
		boolean result = true;
		
		for (int rLoop = 0; rLoop < rules.length; ++rLoop) {
			result &= rules[rLoop].inspect(classSummary);
		}
		
		return result;
		
		//return leftRule.inspect(classSummary) && rightRule.inspect(classSummary);
	}

	public String getExpression(RuleClassBindings ruleClassBindings) {
		StringBuffer buffer = new StringBuffer(ruleClassBindings.getRule(getClass()) + '(');
		
		for (int rLoop = 0; rLoop < rules.length; ++rLoop) {
			if (rLoop != 0) {
				buffer.append(", ");
			}
			buffer.append(rules[rLoop].getExpression(ruleClassBindings));
		}
		buffer.append(')');
		
		return buffer.toString();
    }
}
