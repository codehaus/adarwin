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

import org.adarwin.rule.FalseRule;
import org.adarwin.rule.OrRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.TrueRule;
import org.adarwin.testmodel.a.InPackageA;

public class OrRuleTestCase extends TestCase {
    private RuleClassBindings ruleClassBindings;
    private ClassFile code;

    protected void setUp() throws Exception {
        super.setUp();

		ruleClassBindings = new RuleClassBindings(new String[] {"or", "true", "false"},
                    new Class[] {OrRule.class, TrueRule.class, FalseRule.class});

        code = new ClassFile(InPackageA.class);
    }

    public void testFalseOrFalse() throws BuilderException, IOException {
        String expression = "or(false, false)";

        Rule rule = new RuleBuilder(ruleClassBindings).buildRule(expression);

        assertEquals(expression, rule.toString(ruleClassBindings));

        assertEquals(0, code.evaluate(rule).getCount());
    }

    public void testTrueOrFalse() throws IOException, BuilderException {
        String expression = "or(true, false)";

        Rule rule = new RuleBuilder(ruleClassBindings).buildRule(expression);

        assertEquals(expression, rule.toString(ruleClassBindings));

        assertTrue(code.evaluate(rule).getCount() > 0);
    }

    public void testFalseOrTrue() throws BuilderException, IOException {
        String expression = "or(false, true)";

        Rule rule = new RuleBuilder(ruleClassBindings).buildRule(expression);

        assertEquals(expression, rule.toString(ruleClassBindings));

        assertTrue(code.evaluate(rule).getCount() > 0);
    }
}
