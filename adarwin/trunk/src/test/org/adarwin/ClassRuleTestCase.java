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

import org.adarwin.rule.ClassRule;

public class ClassRuleTestCase extends RuleTestCase {
	public void testNonMatchingClass() throws ADarwinException {
		assertNumMatches(0, new ClassRule("Fred"), String.class);
    }

    public void testMatchingClass() throws ADarwinException {
        assertNumMatches(1, new ClassRule("St.*"), String.class);
    }
}
