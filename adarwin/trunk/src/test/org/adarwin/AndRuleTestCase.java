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

import org.adarwin.rule.AndRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.SourceRule;
import org.adarwin.rule.UsesRule;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.a.InPackageAUsesClassFromPackageB;
import org.adarwin.testmodel.b.InPackageB;

public class AndRuleTestCase extends RuleTestCase {
    public void testFalseAndFalse() throws ADarwinException {
        Rule rule = new AndRule(new Rule[] {new FalseRule(), new FalseRule()});

        assertNumMatches(0, rule, InPackageA.class);
    }

    public void testFalseAndTrue() throws ADarwinException {
        Rule rule = new AndRule(new Rule[] {new FalseRule(), new TrueRule()});

        assertNumMatches(0, rule, InPackageA.class);
    }

    public void testTrueAndFalse() throws ADarwinException {
        Rule rule = new AndRule(new Rule[] {new TrueRule(), new FalseRule()});

        assertNumMatches(0, rule, InPackageA.class);
    }

    public void testTrueAndTrue() throws ADarwinException {
        Rule rule = new AndRule(new Rule[] {new TrueRule(), new TrueRule()});

        assertNumMatches(1, rule, InPackageAUsesClassFromPackageB.class);
    }

	public void testRealTrueAndTrue() throws ADarwinException {
		Rule rule = new AndRule(new Rule[] {
			new SourceRule(createPackageRule(InPackageA.class)),
			new UsesRule(createPackageRule(InPackageB.class))
		});

		assertNumMatches(1, rule, InPackageAUsesClassFromPackageB.class);
	}

    public void testSequentialCoincedence() throws ADarwinException {
        Rule rule = new AndRule(new Rule[] {
        	createPackageRule(InPackageA.class),
			createPackageRule(InPackageB.class)
		});

        assertNumMatches(0, rule, InPackageA.class);
        assertNumMatches(0, rule, InPackageB.class);
    }
}
