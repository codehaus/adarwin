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

import junit.framework.Assert;
import junit.framework.TestCase;

import org.adarwin.rule.AndRule;
import org.adarwin.rule.FalseRule;
import org.adarwin.rule.PackageRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.SourceRule;
import org.adarwin.rule.TrueRule;
import org.adarwin.rule.UsesRule;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.a.InPackageAUsesClassFromPackageB;
import org.adarwin.testmodel.b.InPackageB;

public class AndRuleTestCase extends TestCase {
    public void testFalseAndFalse() throws IOException {
        Rule rule = new AndRule(new Rule[] {new FalseRule(), new FalseRule()});

        Assert.assertEquals(0, new ClassFile(InPackageA.class).evaluate(rule).getCount());
    }

    public void testFalseAndTrue() throws IOException {
        Rule rule = new AndRule(new Rule[] {new FalseRule(), new TrueRule()});

        assertEquals(0, new ClassFile(InPackageA.class).evaluate(rule).getCount());
    }

    public void testTrueAndFalse() throws IOException {
        Rule rule = new AndRule(new Rule[] {new TrueRule(), new FalseRule()});

        assertEquals(0, new ClassFile(InPackageA.class).evaluate(rule).getCount());
    }

    public void testTrueAndTrue() throws IOException {
        Rule rule = new AndRule(new Rule[] {new TrueRule(), new TrueRule()});

        assertTrue(new ClassFile(InPackageAUsesClassFromPackageB.class).evaluate(rule).getCount() > 0);
    }

	public void testRealTrueAndTrue() throws IOException {
		Rule rule = new AndRule(new Rule[] {
			new SourceRule(PackageRule.create(InPackageA.class)),
			new UsesRule(PackageRule.create(InPackageB.class))
		});

		assertTrue(new ClassFile(InPackageAUsesClassFromPackageB.class).evaluate(rule).getCount() > 0);
	}

    public void testSequentialCoincedence() throws IOException {
        Rule rule = new AndRule(new Rule[] {
        	PackageRule.create(InPackageA.class),
			PackageRule.create(InPackageB.class)
		});

		int count = new ClassFile(InPackageA.class).evaluate(rule).getCount();
		count += new ClassFile(InPackageB.class).evaluate(rule).getCount();

		assertEquals(0, count);
    }
}
