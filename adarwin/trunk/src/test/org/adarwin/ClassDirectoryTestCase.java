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

public class ClassDirectoryTestCase extends TestCase {
	public void testInPackageA() throws IOException {
		//Rule rule = new SourceRule(PackageRule.create(InPackageA.class));

		//assertEquals(3, new ClassDirectory("c:\\work\\misc\\adarwin\\target\\idea").evaluate(rule).getCount());
	}
}
