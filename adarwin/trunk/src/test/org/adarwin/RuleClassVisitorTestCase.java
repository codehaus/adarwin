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

import org.adarwin.testmodel.b.ExceptionInPackageB;

public class RuleClassVisitorTestCase extends RuleTestCase {
	static class HasZeroArgConstructor {
		public HasZeroArgConstructor() {
		}
	}

	public void testConstructor() {
		assertContains(HasZeroArgConstructor.class, Constructor.createDeclaration(
			HasZeroArgConstructor.class.getName(), new String[0]));
	}

	public void testVoidReturnMethod() {
		class ClassWithVoidReturnMethod {
			public void voidReturnMethod() {
			}
		}
		
		assertContains(ClassWithVoidReturnMethod.class, createMethodDeclaration(
			"voidReturnMethod", Void.TYPE));
	}

	public void testNoArgMethod() {
		class ClassWithNoArgMethod {
			public Integer noArgMethod() {
				return null;
			}
		}

		assertContains(ClassWithNoArgMethod.class, createMethodDeclaration(
			"noArgMethod", Integer.class));
	}

	public void testSingleArgMethod() {
		class ClassWithSingleArgMethod {
			public Integer singleArgMethod(String string) {
				return null;
			}
		}

		assertContains(ClassWithSingleArgMethod.class,
			createMethodDeclaration("singleArgMethod", Integer.class, new Class[] {String.class}));
	}

	public void testTwoArgMethod() {
		class ClassWithTwoArgMethod {
			public Integer twoArgMethod(Integer integer, String string) {
				return null;
			}
		}

		assertContains(ClassWithTwoArgMethod.class, createMethodDeclaration(
			"twoArgMethod", Integer.class, new Class[] {Integer.class, String.class}));
	}

	public void testThrowsClause() {
		class UsesClassInPackageBInMethodThrowsClause {
			public void naughty() throws ExceptionInPackageB {
			}
		}

		assertContains(UsesClassInPackageBInMethodThrowsClause.class,
			CodeElement.createUses(ExceptionInPackageB.class.getName()));
	}

	private void assertContains(Class clazz, CodeElement codeElement) {
		assertTrue(RuleClassVisitor.visit(getInputStream(clazz)).contains(codeElement));
	}
}

