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

import org.adarwin.rule.PackageRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.SourceRule;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.b.InPackageB;

public class InPackageTestCase extends TestCase {
    private Rule rule;

    protected void setUp() throws Exception {
        super.setUp();
        rule = new SourceRule(PackageRule.create(InPackageA.class));
    }

    public void testInPackage() throws IOException {
        assertEquals(1, new ClassFile(InPackageA.class).evaluate(rule).getCount());
    }

    public void testNotInPackage() throws IOException {
        assertEquals(0, new ClassFile(InPackageB.class).evaluate(rule).getCount());
    }
}
