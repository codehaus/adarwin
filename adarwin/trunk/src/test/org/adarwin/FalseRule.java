/*****************************************************************************
 * Copyright (C) aDarwin Organisation. All rights reserved.                  *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Idea and Original Code by Stacy Curl                                      *
 *****************************************************************************/

package org.adarwin;

import org.adarwin.ClassSummary;
import org.adarwin.RuleClassBindings;
import org.adarwin.rule.Rule;

import java.util.HashSet;


public class FalseRule implements Rule {
	public String toString(RuleClassBindings ruleClassBindings) {
        return ruleClassBindings.getRule(getClass());
    }

	public ClassSummary inspect(ClassSummary classSummary) {
		return new ClassSummary(classSummary.getClassName(), new HashSet());
	}
}
