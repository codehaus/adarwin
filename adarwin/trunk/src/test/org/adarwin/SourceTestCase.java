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

import org.adarwin.rule.Rule;
import org.adarwin.rule.SourceRule;
import org.adarwin.testmodel.a.InPackageA;

import java.io.IOException;

public class SourceTestCase extends RuleTestCase {
    public void testMatchesMinimalClass() throws BuilderException, IOException {
        String expression = "src(true)";

        Rule rule = new RuleBuilder(new RuleClassBindings(new String[] {"src", "true"},
            new Class[] {SourceRule.class, TrueRule.class})).buildRule(expression);

        assertNumMatches(1, rule, InPackageA.class);
    }
}
