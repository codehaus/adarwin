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

import org.adarwin.rule.Rule;

import java.io.InputStream;

public class ClassFile implements Code {
	private final InputStream inputStream;

	public ClassFile(InputStream inputStream) {
		this.inputStream = inputStream;
    }

	public boolean evaluate(Rule rule, RuleListener ruleListener) throws ADarwinException {
		return ruleListener.matchesEvent(rule.inspect(RuleClassVisitor.visit(inputStream)), rule, this);
	}
}
