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

import org.adarwin.rule.NotRule;
import org.adarwin.rule.PackageRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.TrueRule;
import org.adarwin.rule.UsesRule;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.a.UsesPackageBAndPackageC;
import org.adarwin.testmodel.b.InPackageB;

public class NotRuleTestCase extends TestCase {
    public void testNegatation() throws IOException {
        Rule rule = new NotRule(new TrueRule());

        assertEquals(0, new ClassFile(InPackageA.class).evaluate(rule).getCount());
    }

    public void testDoubleNegation() throws IOException {
        Rule rule = new NotRule(new NotRule(new TrueRule()));

        assertTrue(new ClassFile(InPackageA.class).evaluate(rule).getCount() > 0);
    }

	public void testComplexNegation() throws IOException {
		Rule doesNotUseRule = new NotRule(new UsesRule(PackageRule.create(InPackageB.class)));

		assertEquals(0, new ClassFile(UsesPackageBAndPackageC.class).evaluate(doesNotUseRule).getCount());
	}
}
