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

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

import java.io.IOException;

import junit.framework.TestCase;

public class ClassPathTestCase extends TestCase {
	public void testStringPath() throws IOException {
		Mock codeFactory = new Mock(ICodeFactory.class);
		Mock code = new Mock(Code.class);
		RuleListener listener = (RuleListener) new Mock(RuleListener.class).proxy();
		
		Rule rule = new TrueRule();

		codeFactory.expectAndReturn("create", "first", code.proxy());
		codeFactory.expectAndReturn("create", "second", code.proxy());
		code.expectAndReturn("evaluate", C.eq(rule, listener), new Boolean(true));
		code.expectAndReturn("evaluate", C.eq(rule, listener), new Boolean(true));

		new ClassPath("first;second", (ICodeFactory) codeFactory.proxy()).evaluate(rule, listener);

		codeFactory.verify();
	}
}
