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

public class ClassDirectory implements Code {
    private final String directory;
	private final ICodeFactory codeFactory;

    public ClassDirectory(String directory, ICodeFactory codeFactory) {
        this.directory = directory;
		this.codeFactory = codeFactory;
    }

	public void evaluate(Rule rule, RuleListener ruleListener) throws IOException {
		File[] files = new File(directory).listFiles();

		for (int fLoop = 0; files != null && fLoop < files.length; ++fLoop) {
			Code code = codeFactory.create(files[fLoop].getAbsolutePath());

			code.evaluate(rule, ruleListener);
		}
	}
}
