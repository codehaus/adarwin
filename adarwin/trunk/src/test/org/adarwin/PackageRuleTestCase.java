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

import org.adarwin.rule.PackageRule;

import java.util.Date;

public class PackageRuleTestCase extends RuleTestCase {
	public void testMatchingUsingClass() {
		assertNumMatches(1, new PackageRule(Util.packageName(Date.class)), Date.class);
    }

	public void testMatchingUsingRegularExpression() {
		assertNumMatches(1, new PackageRule(".*util"), Date.class);
	}

	public void testNonMatchingUsingRegularExpression() {
		assertNumMatches(0, new PackageRule(".*flibble"), Date.class);
	}
}
