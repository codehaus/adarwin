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

import junit.framework.TestCase;
import com.mockobjects.dynamic.Mock;
import org.adarwin.rule.TrueRule;
import org.adarwin.rule.Rule;

import java.io.IOException;

public class LazyCodeFactoryTestCase extends TestCase {
	private Mock mockCodeFactory;
	private LazyCodeFactory lazyCodeFactory;
	private String path;

	protected void setUp() throws Exception {
		super.setUp();
		mockCodeFactory = new Mock(ICodeFactory.class);
		lazyCodeFactory = new LazyCodeFactory((ICodeFactory) mockCodeFactory.proxy());
		path = "test.class";
	}

	public void testWithoutInvokingEvaluateRealCodeNotCreated() {
		lazyCodeFactory.create(path);

		mockCodeFactory.verify();
	}

	public void testRealCodeCreatedOnEvaluate() throws IOException {
		Rule rule = new TrueRule();
		Mock mockCode = new Mock(Code.class);
		mockCodeFactory.expectAndReturn("create", path, mockCode.proxy());
		mockCode.expectAndReturn("evaluate", rule, new Result(false, ""));

		Code code = lazyCodeFactory.create(path);
		code.evaluate(rule);

		mockCodeFactory.verify();
	}
}
