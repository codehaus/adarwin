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

public class OrRule implements Rule {
	private Rule left;
	private Rule right;

	public OrRule(Rule left, Rule right) {
		this.left = left;
		this.right = right;
	}

	public boolean inspect(ClassSummary classSummary) {
		return left.inspect(classSummary) || right.inspect(classSummary);
	}

	public String getExpression(Grammar grammar) {
		return grammar.getRule(getClass()) + '(' + left.getExpression(grammar) + ", " + right.getExpression(grammar)
            + ')';
	}
}
