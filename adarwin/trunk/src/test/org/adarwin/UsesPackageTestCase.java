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
import org.adarwin.testmodel.UsesClassInPackageBInConstructorParameters;
import org.adarwin.testmodel.UsesClassInPackageBInMethodParameters;
import org.adarwin.testmodel.UsesClassInPackageBInMethodReturn;
import org.adarwin.testmodel.UsesClassInPackageBInMethodThrowsClause;
import org.adarwin.testmodel.UsesClassInPackageBInThrow;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.b.InPackageB;

import java.io.IOException;

public class UsesPackageTestCase extends RuleTestCase {
    private final Rule rule = new UsesRule(createPackageRule(InPackageB.class));
    
    public void testNotUsingPackage() throws IOException {
		assertNumMatches(0, rule, InPackageA.class);
    }    

	public void testUsesAsField() throws IOException {
		assertNumMatches(1, rule, UsesClassInPackageBAsField.class);
    }

    public void testUsesAsLocalVariable() throws IOException {
    	assertNumMatches(1, rule, UsesClassInPackageBAsLocalVariable.class);
    }

    public void testUsesAsAnonymouseLocalVariable() throws IOException {
    	assertNumMatches(1, rule, UsesClassInPackageBAsAnonymousLocalVariable.class);
    }

    public void testUsesForInvocation() throws IOException {
    	assertNumMatches(1, rule, UsesClassInPackageBForInvocation.class);
    }

    public void testUsesForStaticInvocation() throws IOException {
    	assertNumMatches(1, rule, UsesClassInPackageBForStaticInvocation.class);
    }

//    public void testUsesForClassInstanceField() throws IOException {
//        assertEquals(1, new ClassFile(UsesClassInPackageBForClassInstanceField.class).evaluate(rule));
//    }
//
//    public void testUsesForClassInstanceInMethod() throws IOException {
//        assertEquals(1, new ClassFile(UsesClassInPackageBForClassInstanceInMethod.class).evaluate(rule));
//    }

	public void testUsesForBaseClass() throws IOException {
		assertNumMatches(1, rule, UsesClassForBaseClass.class);
	}

	public void testUsesForBaseInterface() throws IOException {
		assertNumMatches(1, rule, IUsesClassForBaseInterface.class);
	}

	public void testUsesForInterfaceImplementation() throws IOException {
		assertNumMatches(1, rule, UsesClassForBaseInterface.class);
	}
	
	public void testUsesInMethodReturn() throws IOException {
		assertNumMatches(1, rule, UsesClassInPackageBInMethodReturn.class);
	}

	public void testUsesInMethodParameters() throws IOException {
		assertNumMatches(1, rule, UsesClassInPackageBInMethodParameters.class);
	}
	
	public void testUsesInConstructorParameters() throws IOException {
		assertNumMatches(1, rule, UsesClassInPackageBInConstructorParameters.class);
	}
	
	public void testUsesInMethodThrowsClause() throws IOException {
		assertNumMatches(1, rule, UsesClassInPackageBInMethodThrowsClause.class);
	}
	
	public void testUsesInThrow() throws IOException {
		assertNumMatches(1, rule, UsesClassInPackageBInThrow.class);
	}

	public void testTwoClassesUsing() throws IOException {
		assertNumMatches(2, rule, JarFileTestCase.createJarFile(new Class[] {
			UsesClassForBaseInterface.class, IUsesClassForBaseInterface.class}
		));
	}
}
