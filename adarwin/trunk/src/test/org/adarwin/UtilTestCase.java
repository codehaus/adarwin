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

import org.adarwin.testmodel.a.InPackageA;

public class UtilTestCase extends TestCase {
	public void testPackageParsing() {
		assertEquals("org.adarwin.testmodel.a", Util.packageName(InPackageA.class));
	}

	public void testPackageParsingForDefaultPackage() {
		assertEquals("", Util.packageName("ClassName"));
	}
}
