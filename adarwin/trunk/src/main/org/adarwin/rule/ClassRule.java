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

import java.util.regex.Pattern;

public class ClassRule implements Rule, Filter {
	private final String pattern;

	public ClassRule(String pattern) {
        this.pattern = pattern;
    }

	public ClassSummary inspect(ClassSummary classSummary) {
		if (Pattern.matches(pattern, classSummary.getClassName().getClassName())) {
			return classSummary;
		}
		else {
			return classSummary.filter(this);
		}
	}
	
	public boolean matches(CodeElement codeElement) {
		return codeElement instanceof UsesCodeElement && 
			Pattern.matches(pattern, codeElement.getClassName().getClassName());
	}
	
	public String toString(RuleClassBindings ruleClassBindings) {
        return ruleClassBindings.getRule(getClass()) + '(' + pattern + ')';
    }
}
