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

import org.adarwin.rule.OrRule;
import org.adarwin.rule.Rule;
import org.adarwin.testmodel.a.InPackageA;

public class OrRuleTestCase extends RuleTestCase {
    private final RuleClassBindings ruleClassBindings = new RuleClassBindings(
    	new String[] {"or", "true", "false"},
    	new Class[] {OrRule.class, TrueRule.class, FalseRule.class});
    
    public void testFalseOrFalse() throws ADarwinException {
        String expression = "or(false, false)";

        Rule rule = new RuleBuilder(ruleClassBindings).buildRule(expression);

        assertEquals(expression, rule.toString(ruleClassBindings));

        assertNumMatches(0, rule, InPackageA.class);
    }

    public void testTrueOrFalse() throws ADarwinException {
        String expression = "or(true, false)";

        Rule rule = new RuleBuilder(ruleClassBindings).buildRule(expression);

        assertEquals(expression, rule.toString(ruleClassBindings));

        assertNumMatches(1, rule, InPackageA.class);
    }

    public void testFalseOrTrue() throws ADarwinException {
        String expression = "or(false, true)";

        Rule rule = new RuleBuilder(ruleClassBindings).buildRule(expression);

        assertEquals(expression, rule.toString(ruleClassBindings));

        assertNumMatches(1, rule, InPackageA.class);
    }
}
