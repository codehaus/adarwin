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

import org.adarwin.rule.PackageRule;
import org.adarwin.rule.Rule;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.b.InPackageB;

import java.io.IOException;

public class PackageRuleTestCase extends RuleTestCase {
	private final RuleBuilder ruleBuilder =
		new RuleBuilder(new RuleClassBindings("package", PackageRule.class));

	public void testMatchingUsingClass() throws BuilderException, IOException {
		Rule rule = ruleBuilder.buildRule("package(" + Util.packageName(InPackageB.class) + ')');

		assertNumMatches(1, rule, InPackageB.class);
    }

	public void testMatchingUsingRegularExpression() throws BuilderException, IOException {
		Rule rule = ruleBuilder.buildRule("package(.*a)");

		assertNumMatches(1, rule, InPackageA.class);
	}

	public void testNonMatchingUsingRegularExpression() throws BuilderException, IOException {
		Rule rule = ruleBuilder.buildRule("package(.*b)");

		assertNumMatches(0, rule, InPackageA.class);
	}
}
