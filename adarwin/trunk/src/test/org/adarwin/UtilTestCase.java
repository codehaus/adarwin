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

public class UtilTestCase extends TestCase {
	public void testPackageParsing() {
		assertEquals("abc.def", Util.packageName("abc.def.Class"));
	}

	public void testPackageParsingForDefaultPackage() {
		assertEquals("", Util.packageName("Class"));
	}
}
