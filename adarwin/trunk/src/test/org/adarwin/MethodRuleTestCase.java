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

import org.adarwin.rule.MethodRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.SourceRule;
import org.adarwin.rule.UsesRule;
import org.adarwin.testmodel.CallsSimple;
import org.adarwin.testmodel.Simple;

import java.io.IOException;

public class MethodRuleTestCase extends RuleTestCase {
	public void testVoidReturnMethod() throws IOException {
		assertNumMatches(1, createMethodRule(Simple.VOID_RETURN_METHOD), Simple.class);
	}

	public void testNoArgMethod() throws IOException {
		assertNumMatches(1, createMethodRule(Simple.NO_ARG_METHOD), Simple.class);
	}

	public void testSingleArgMethod() throws IOException {
		assertNumMatches(1, createMethodRule(Simple.SINGLE_ARG_METHOD), Simple.class);
	}

	public void testTwoArgMethod() throws IOException {
		assertNumMatches(1, createMethodRule(Simple.TWO_ARG_METHOD), Simple.class);
	}

	public void testMethodReturningPrimitive() throws IOException {
		assertNumMatches(1, createMethodRule(Simple.METHOD_RETURNING_PRIMITIVE), Simple.class);
	}

	public void testMethodDeclaration() throws IOException {
		Rule rule = new SourceRule(createMethodRule(Simple.NO_ARG_METHOD));

		assertNumMatches(1, rule, Simple.class);
	}

	public void testSelf() throws IOException {
		Rule rule = MethodRule.create(Void.TYPE.getName(), "testSelf", new String[0]); 

		assertNumMatches(1, rule, MethodRuleTestCase.class);
	}

	public void testMethodInvocation() throws IOException {
		Rule rule = new UsesRule(createMethodRule(Simple.NO_ARG_METHOD));

		assertNumMatches(1, rule, CallsSimple.class);
	}

	public void testUsingAMethodNotRegardedAsHavingSaidMethod() throws IOException {
		Rule rule = new SourceRule(createMethodRule(Simple.NO_ARG_METHOD));

		assertNumMatches(0, rule, CallsSimple.class);
	}
}
