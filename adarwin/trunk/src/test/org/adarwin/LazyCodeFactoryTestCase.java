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

import junit.framework.TestCase;

import org.adarwin.rule.Rule;

import com.mockobjects.dynamic.C;
import com.mockobjects.dynamic.Mock;

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
		Mock mockResultOperator = new Mock(RuleListener.class);
		RuleListener operator = (RuleListener) mockResultOperator.proxy();
		
		mockCodeFactory.expectAndReturn("create", path, mockCode.proxy());
		mockCode.expectAndReturn("evaluate", C.eq(rule, operator), new Boolean(false));
		Code code = lazyCodeFactory.create(path);

		code.evaluate(rule, operator);

		mockCodeFactory.verify();
	}
}
