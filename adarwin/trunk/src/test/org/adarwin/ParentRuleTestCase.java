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

import org.adarwin.rule.ParentRule;
import org.adarwin.rule.Rule;
import org.adarwin.testmodel.IUsesClassForBaseInterface;
import org.adarwin.testmodel.UsesClassForBaseClass;
import org.adarwin.testmodel.UsesClassForBaseInterface;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.b.IInPackageB;
import org.adarwin.testmodel.b.InPackageB;

public class ParentRuleTestCase extends TestCase {
	public void testNeitherExtendsNorImplements() throws IOException {
		Rule rule = new ParentRule(InPackageB.class);

		assertEquals(0, new ClassFile(InPackageA.class).evaluate(rule).getCount());
	}

	public void testExtends() throws IOException {
		Rule rule = new ParentRule(InPackageB.class);

		assertEquals(1, new ClassFile(UsesClassForBaseClass.class).evaluate(rule).getCount());
	}

	public void testImplements() throws IOException {
		Rule rule = new ParentRule(IInPackageB.class);

		assertEquals(1, new ClassFile(UsesClassForBaseInterface.class).evaluate(rule).getCount());
	}

	public void testExtendsInterface() throws IOException {
		Rule rule = new ParentRule(IInPackageB.class);

		assertEquals(1, new ClassFile(IUsesClassForBaseInterface.class).evaluate(rule).getCount());
	}
}
