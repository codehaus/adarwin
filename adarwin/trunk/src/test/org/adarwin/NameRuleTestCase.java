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

import org.adarwin.rule.AndRule;
import org.adarwin.rule.NameRule;
import org.adarwin.rule.Rule;

public class NameRuleTestCase extends TestCase {
	private static final String SOME_NAME = "some name";
	
	private RuleClassBindings ruleClassBindings;

	protected void setUp() throws Exception {
		super.setUp();

		ruleClassBindings = new RuleClassBindings(
			new String[] {"name", "and", "true"},
			new Class[] {NameRule.class, AndRule.class, TrueRule.class}
		);
	}
	
	public void testExpression() {
		Rule rule = new NameRule(SOME_NAME, new AndRule(new Rule[] {new TrueRule(), new TrueRule()}));
		
		assertEquals(SOME_NAME, rule.toString(ruleClassBindings));
	}
	
	public void testBuild() throws ADarwinException {
		Rule rule = new RuleBuilder(ruleClassBindings).buildRule(
			"name(" + SOME_NAME + ", and(true, true))");
		
		assertEquals(SOME_NAME, rule.toString(ruleClassBindings));
	}
}
