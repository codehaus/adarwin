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
import org.adarwin.Grammar;


public class AndRule implements Rule {
	private Rule leftRule;
    private Rule rightRule;

    public AndRule(Rule leftRule, Rule rightRule) {
        super();
		if (leftRule == null || rightRule == null) {
			throw new NullPointerException();
		}
        this.leftRule = leftRule;
        this.rightRule = rightRule;
    }

	public boolean inspect(ClassSummary classSummary) {
		return leftRule.inspect(classSummary) && rightRule.inspect(classSummary);
	}

	public String getExpression(Grammar grammar) {
        return grammar.getRule(getClass()) + '(' + leftRule.getExpression(grammar) + ", " +
            rightRule.getExpression(grammar) + ')';
    }
}
