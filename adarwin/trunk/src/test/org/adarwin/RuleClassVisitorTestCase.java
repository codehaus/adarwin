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

import org.adarwin.rule.ElementType;
import org.adarwin.testmodel.b.ExceptionInPackageB;

import java.io.IOException;

public class RuleClassVisitorTestCase extends RuleTestCase {
	static class HasZeroArgConstructor {
		public HasZeroArgConstructor() {
		}
	}

	public void testConstructor() throws IOException {
		assertContains(HasZeroArgConstructor.class, new ConstructorDeclaration(
			new ClassName(HasZeroArgConstructor.class.getName()), new String[0]));
	}

	public void testVoidReturnMethod() throws IOException {
		class ClassWithVoidReturnMethod {
			public void voidReturnMethod() {
			}
		}
		
		assertContains(ClassWithVoidReturnMethod.class, createMethodDeclaration(
			"voidReturnMethod", Void.TYPE));
	}

	public void testNoArgMethod() throws IOException {
		class ClassWithNoArgMethod {
			public Integer noArgMethod() {
				return null;
			}
		}

		assertContains(ClassWithNoArgMethod.class, createMethodDeclaration(
			"noArgMethod", Integer.class));
	}

	public void testSingleArgMethod() throws IOException {
		class ClassWithSingleArgMethod {
			public Integer singleArgMethod(String string) {
				return null;
			}
		}

		assertContains(ClassWithSingleArgMethod.class,
			createMethodDeclaration("singleArgMethod", Integer.class, new Class[] {String.class}));
	}

	public void testTwoArgMethod() throws IOException {
		class ClassWithTwoArgMethod {
			public Integer twoArgMethod(Integer integer, String string) {
				return null;
			}
		}

		assertContains(ClassWithTwoArgMethod.class, createMethodDeclaration(
			"twoArgMethod", Integer.class, new Class[] {Integer.class, String.class}));
	}

	public void testThrowsClause() throws IOException {
		class UsesClassInPackageBInMethodThrowsClause {
			public void naughty() throws ExceptionInPackageB {
			}
		}

		assertContains(UsesClassInPackageBInMethodThrowsClause.class,
			create(ExceptionInPackageB.class, ElementType.USES));
	}

	private void assertContains(Class clazz, CodeElement codeElement) throws IOException {
		assertTrue(new RuleClassVisitor().visit(clazz).contains(codeElement));
	}

	private CodeElement create(Class clazz, ElementType codeType) {
		return CodeElement.create(new ClassName(clazz.getName()), codeType);
	}
}

