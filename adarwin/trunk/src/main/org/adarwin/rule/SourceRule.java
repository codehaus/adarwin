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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.adarwin.ClassSummary;
import org.adarwin.CodeElement;
import org.adarwin.RuleClassBindings;

public class SourceRule implements Rule {
	private Rule wrappedRule;

	public SourceRule(Rule wrappedRule) {
		this.wrappedRule = wrappedRule;
    }

	public boolean inspect(ClassSummary classSummary) {
		Set sourceSet = new HashSet();
		for (Iterator iterator = classSummary.getDependancies().iterator(); iterator.hasNext();) {
			CodeElement codeElement = (CodeElement) iterator.next();
			if (ElementType.SOURCE.equals(codeElement.getType())) {
				sourceSet.add(codeElement);
			}
		}
		ClassSummary newSummary = classSummary.updateDependancies(sourceSet);
		
		return wrappedRule.inspect(newSummary);
	}

	public String toString(RuleClassBindings ruleClassBindings) {
        return ruleClassBindings.getRule(getClass()) + '(' + wrappedRule.toString(ruleClassBindings) + ')';
    }
}
