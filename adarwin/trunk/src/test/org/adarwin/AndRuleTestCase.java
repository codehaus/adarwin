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
    public void testFalseAndFalse() {
        Rule rule = new AndRule(new Rule[] {new FalseRule(), new FalseRule()});

        assertFalse(matches(rule, InPackageA.class));
    }

    public void testFalseAndTrue() {
        Rule rule = new AndRule(new Rule[] {new FalseRule(), new TrueRule()});

        assertFalse(matches(rule, InPackageA.class));
    }

    public void testTrueAndFalse() {
        Rule rule = new AndRule(new Rule[] {new TrueRule(), new FalseRule()});

        assertFalse(matches(rule, InPackageA.class));
    }

    public void testTrueAndTrue() {
        Rule rule = new AndRule(new Rule[] {new TrueRule(), new TrueRule()});

        assertTrue(matches(rule, InPackageAUsesClassFromPackageB.class));
    }

	public void testRealTrueAndTrue() {
		Rule rule = new AndRule(new Rule[] {
			new SourceRule(createPackageRule(InPackageA.class)),
			new UsesRule(createPackageRule(InPackageB.class))
		});

		assertTrue(matches(rule, InPackageAUsesClassFromPackageB.class));
	}

    public void testSequentialCoincedence() {
        Rule rule = new AndRule(new Rule[] {
        	createPackageRule(InPackageA.class),
			createPackageRule(InPackageB.class)
		});

        assertFalse(matches(rule, InPackageA.class));
        assertFalse(matches(rule, InPackageB.class));
    }
}
