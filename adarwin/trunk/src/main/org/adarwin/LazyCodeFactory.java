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

import java.io.IOException;

import org.adarwin.rule.Rule;

public class LazyCodeFactory implements ICodeFactory {
	private final ICodeFactory codeFactory;

	public LazyCodeFactory(ICodeFactory codeFactory) {
		this.codeFactory = codeFactory;
	}

	public Code create(final String name) {
		return new LazyCode(name);
	}

	private class LazyCode implements Code {
		private final String name;
		private Code realCode;

		public LazyCode(String name) {
			this.name = name;
		}

		public void evaluate(Rule rule, RuleListener ruleListener) throws IOException {
			if (realCode == null) {
				realCode = codeFactory.create(name);
			}

			realCode.evaluate(rule, ruleListener);
		}
	}
}
