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
import org.adarwin.rule.UsesRule;
import org.adarwin.testmodel.a.InPackageAUsesClassFromPackageB;

public class UsesTestCase extends RuleTestCase {
    public void testMinimal() throws ADarwinException {
        String expression = "uses(true)";

        Rule rule = new RuleBuilder(new RuleClassBindings(new String[] {"uses", "true"},
            new Class[] {UsesRule.class, TrueRule.class})).buildRule(expression);

        assertNumMatches(1, rule, InPackageAUsesClassFromPackageB.class);
    }
}
