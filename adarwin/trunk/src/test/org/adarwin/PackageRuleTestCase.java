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

import org.adarwin.rule.PackageRule;
import org.adarwin.rule.Rule;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.b.InPackageB;

public class PackageRuleTestCase extends TestCase {
	private RuleBuilder ruleBuilder;

	protected void setUp() throws Exception {
		super.setUp();

		ruleBuilder = new RuleBuilder(new RuleClassBindings("package", PackageRule.class));
	}

	public void testMatchingUsingClass() throws BuilderException, IOException {
		String expression = "package(" + Util.packageName(InPackageB.class) + ')';

		Rule rule = ruleBuilder.buildRule(expression);

        assertEquals(1, new ClassFile(InPackageB.class).evaluate(rule).getCount());
    }

	public void testMatchingUsingRegularExpression() throws BuilderException, IOException {
		String expression = "package(.*a)";

		Rule rule = ruleBuilder.buildRule(expression);

		assertEquals(1, new ClassFile(InPackageA.class).evaluate(rule).getCount());
	}

	public void testNonMatchingUsingRegularExpression() throws BuilderException, IOException {
		String expression = "package(.*b)";

		Rule rule = ruleBuilder.buildRule(expression);

		assertEquals(0, new ClassFile(InPackageA.class).evaluate(rule).getCount());
	}
}
