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

import junit.framework.TestCase;
import org.adarwin.rule.PackageRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.SourceRule;
import org.adarwin.testmodel.a.InPackageA;

import java.io.IOException;

public class JarFileTestCase extends TestCase {
    private Rule rule;

    protected void setUp() throws Exception {
        super.setUp();

        rule = new SourceRule(PackageRule.create(InPackageA.class));
    }

    public void testEmptyJar() throws IOException {
        assertEquals(0, new JarFile(new Class[0]).evaluate(rule).getCount());
    }

    public void testJarWithOneClass() throws IOException {
        assertEquals(1, new JarFile(InPackageA.class).evaluate(rule).getCount());
    }
}

