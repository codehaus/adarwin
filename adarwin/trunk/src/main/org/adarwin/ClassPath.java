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

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

public class ClassPath implements Code {
	private final String path;
	private final ICodeFactory codeFactory;

	public ClassPath(String path, ICodeFactory codeFactory) {
		this.path = path;
		this.codeFactory = codeFactory;
	}

	public void evaluate(Rule rule, RuleListener ruleListener) throws IOException {
		StringTokenizer tokenizer = new StringTokenizer(path, File.pathSeparator);

		while(tokenizer.hasMoreTokens()) {
			codeFactory.create(tokenizer.nextToken()).evaluate(rule, ruleListener);
		}
	}
}
