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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;

import org.adarwin.rule.Rule;

public class ClassPath implements Code {
	private Code[] path;

	public ClassPath(Code[] path) {
		this.path = path;
	}

	public Result evaluate(Rule rule) throws IOException {
		AggregateResult aggregateResult = new AggregateResult();

		for (int pLoop = 0; pLoop < path.length; ++pLoop) {
			Result result = path[pLoop].evaluate(rule);
			aggregateResult.append(result);
        }

		return aggregateResult;
    }

	public static Code[] getCodePath(ICodeFactory codeFactory, String path) throws FileNotFoundException {
		StringTokenizer tokenizer = new StringTokenizer(path, File.pathSeparator);
		Code[] code = new Code[tokenizer.countTokens()];

		for (int cLoop = 0; tokenizer.hasMoreTokens(); ++cLoop) {
			code[cLoop] = codeFactory.create(tokenizer.nextToken());
		}

		return code;
	}
}
