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

public class OrRuleTestCase extends RuleTestCase {
    public void testFalseOrFalse() {
        assertNumMatches(0, new OrRule(new Rule[] {new FalseRule(), new FalseRule()}), Void.class);
    }

    public void testTrueOrFalse() {
        assertNumMatches(1, new OrRule(new Rule[] {new TrueRule(), new FalseRule()}), Void.class);
    }

    public void testFalseOrTrue() {
        assertNumMatches(1, new OrRule(new Rule[] {new FalseRule(), new TrueRule()}), Void.class);
    }

    public void testTrueOrTrue() {
    	assertNumMatches(1, new OrRule(new Rule[] {new TrueRule(), new TrueRule()}), Void.class);
    }
}
