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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.adarwin.rule.PackageRule;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.b.InPackageB;

public class ClassListTestCase extends TestCase {
	public void testNoMatch() throws IOException {
		PackageRule rule = PackageRule.create(InPackageB.class);

		Code code = new ClassFile(InPackageA.class);

		assertFalse(code.evaluate(rule).iterator().hasNext());
	}

	public void testOneMatch() throws IOException {
		PackageRule rule = PackageRule.create(InPackageB.class);

		Code code = new ClassFile(InPackageB.class);

		assertEquals(InPackageB.class.getName(), code.evaluate(rule).iterator().next());
	}

	private Set createSet(Class[] classes) {
		Set set = new HashSet();
		for (int nLoop = 0; nLoop < classes.length; nLoop++) {
			set.add(classes[nLoop].getName());
		}
		return set;
	}

	private Set createSet(Class name) {
		return createSet(new Class[] {name});
	}
}
