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

import org.adarwin.rule.NotRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.UsesRule;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.b.InPackageB;

public class NotRuleTestCase extends RuleTestCase {
    public void testNegatation() throws ADarwinException {
        Rule rule = new NotRule(new TrueRule());

        assertNumMatches(0, rule, InPackageA.class);
    }

    public void testDoubleNegation() throws ADarwinException {
        Rule rule = new NotRule(new NotRule(new TrueRule()));

        assertNumMatches(1, rule, InPackageA.class);
    }

	public void testComplexNegation() throws ADarwinException {
		class UsesPackageAAndPackageB {
			public void uses() {
				new InPackageA();
				new InPackageB();
			}
		}
		
		Rule rule = new NotRule(new UsesRule(createPackageRule(InPackageB.class)));

		assertNumMatches(0, rule, UsesPackageAAndPackageB.class);
	}
}
