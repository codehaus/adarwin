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
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.b.InPackageB;

import java.io.IOException;

public class ClassListTestCase extends RuleTestCase {
	private final Rule rule = createPackageRule(InPackageB.class);

	public void testNoMatch() throws IOException {
		assertNumMatches(0, rule, InPackageA.class);
	}

	public void testOneMatch() throws IOException {
		assertNumMatches(1, rule, InPackageB.class);
	}
}
