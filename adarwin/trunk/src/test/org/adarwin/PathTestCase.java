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
import org.adarwin.testmodel.a.InPackageAUsesClassFromPackageB;

public class PathTestCase extends TestCase {
    public void testPath() throws IOException {
        Rule rule = new SourceRule(PackageRule.create(InPackageA.class));

		Code classPath = new ClassPath(new Code[] {
			new JarFile(JarFile.createJarFile(InPackageA.class)),
			new ClassFile(InPackageAUsesClassFromPackageB.class)
		});

        assertEquals(2, classPath.evaluate(rule).getCount());
    }
}
