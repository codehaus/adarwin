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
import org.adarwin.Grammar;

public class OrRule implements Rule {
	private Rule[] rules;

	public OrRule(Rule[] rules) {
		this.rules = rules;
	}

	public boolean inspect(ClassSummary classSummary) {
		boolean result = false;
		
		for (int rLoop = 0; rLoop < rules.length; ++rLoop) {
			result |= rules[rLoop].inspect(classSummary);
		}
		
		return result;
	}

	public String getExpression(Grammar grammar) {
		StringBuffer buffer = new StringBuffer(grammar.getRule(getClass()) + '(');
		
		for (int rLoop = 0; rLoop < rules.length; ++rLoop) {
			if (rLoop != 0) {
				buffer.append(", ");
			}
			buffer.append(rules[rLoop].getExpression(grammar));
		}
		buffer.append(')');
            
        return buffer.toString();
	}
}
