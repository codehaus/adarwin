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
import org.adarwin.RuleClassBindings;
import org.adarwin.UsesCodeElement;

public class UsesRule implements Rule, Filter {
    private final Rule wrappedRule;

	public UsesRule(Rule wrappedRule) {
        this.wrappedRule = wrappedRule;
    }

	public ClassSummary inspect(ClassSummary classSummary) {
		return wrappedRule.inspect(classSummary).filter(this);
	}

	public boolean matches(CodeElement dependancy) {
		return (dependancy instanceof UsesCodeElement);
	}

	public String toString(RuleClassBindings ruleClassBindings) {
        return ruleClassBindings.getRule(getClass()) + '(' + wrappedRule.toString(ruleClassBindings) + ')';
    }
}
