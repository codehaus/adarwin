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
import org.adarwin.rule.UsesRule;
import org.adarwin.testmodel.IUsesClassForBaseInterface;
import org.adarwin.testmodel.UsesClassForBaseClass;
import org.adarwin.testmodel.UsesClassForBaseInterface;
import org.adarwin.testmodel.UsesClassInPackageBAsAnonymousLocalVariable;
import org.adarwin.testmodel.UsesClassInPackageBAsField;
import org.adarwin.testmodel.UsesClassInPackageBAsLocalVariable;
import org.adarwin.testmodel.UsesClassInPackageBForInvocation;
import org.adarwin.testmodel.UsesClassInPackageBForStaticInvocation;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.b.InPackageB;

public class UsesPackageTestCase extends TestCase {
    private Rule rule;

    protected void setUp() throws Exception {
        super.setUp();
        rule = new UsesRule(PackageRule.create(InPackageB.class));
    }

    public void testNotUsingPackage() throws IOException {
        assertEquals(0, new ClassFile(InPackageA.class).evaluate(rule).getCount());
    }

    public void testUsesAsField() throws IOException {
        assertEquals(1, new ClassFile(UsesClassInPackageBAsField.class).evaluate(rule).getCount());
    }

    public void testUsesAsLocalVariable() throws IOException {
        assertEquals(1, new ClassFile(UsesClassInPackageBAsLocalVariable.class).evaluate(rule).getCount());
    }

    public void testUsesAsAnonymouseLocalVariable() throws IOException {
        assertEquals(1, new ClassFile(UsesClassInPackageBAsAnonymousLocalVariable.class).evaluate(rule).getCount());
    }

    public void testUsesForInvocation() throws IOException {
        assertEquals(1, new ClassFile(UsesClassInPackageBForInvocation.class).evaluate(rule).getCount());
    }

    public void testUsesForStaticInvocation() throws IOException {
        assertEquals(1, new ClassFile(UsesClassInPackageBForStaticInvocation.class).evaluate(rule).getCount());
    }

//    public void testUsesForClassInstanceField() throws IOException {
//        assertEquals(1, new ClassFile(UsesClassInPackageBForClassInstanceField.class).evaluate(rule).getCount());
//    }
//
//    public void testUsesForClassInstanceInMethod() throws IOException {
//        assertEquals(1, new ClassFile(UsesClassInPackageBForClassInstanceInMethod.class).evaluate(rule).getCount());
//    }

	public void testUsesForBaseClass() throws IOException {
		assertEquals(1, new ClassFile(UsesClassForBaseClass.class).evaluate(rule).getCount());
	}

	public void testUsesForBaseInterface() throws IOException {
		assertEquals(1, new ClassFile(IUsesClassForBaseInterface.class).evaluate(rule).getCount());
	}

	public void testUsesForInterfaceImplementation() throws IOException {
		assertEquals(1, new ClassFile(UsesClassForBaseInterface.class).evaluate(rule).getCount());
	}

	public void testTwoClassesUsing() throws IOException {
		assertEquals(2, new JarFile(new Class[] {UsesClassForBaseInterface.class, IUsesClassForBaseInterface.class})
			.evaluate(rule).getCount());
	}
}
