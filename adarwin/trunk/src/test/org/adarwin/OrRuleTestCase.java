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
    public void testFalseOrFalse() throws ADarwinException {
        assertNumMatches(0, new OrRule(new Rule[] {new FalseRule(), new FalseRule()}),
        	String.class);
    }

    public void testTrueOrFalse() throws ADarwinException {
        assertNumMatches(1, new OrRule(new Rule[] {new TrueRule(), new FalseRule()}),
        	String.class);
    }

    public void testFalseOrTrue() throws ADarwinException {
        assertNumMatches(1, new OrRule(new Rule[] {new FalseRule(), new TrueRule()}),
        	String.class);
    }

    public void testTrueOrTrue() throws ADarwinException {
    	assertNumMatches(1, new OrRule(new Rule[] {new TrueRule(), new TrueRule()}),
    		String.class);
    }
}
