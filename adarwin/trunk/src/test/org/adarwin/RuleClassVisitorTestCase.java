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

import org.adarwin.rule.ElementType;
import org.adarwin.testmodel.HasZeroArgConstructor;
import org.adarwin.testmodel.Simple;
import org.adarwin.testmodel.UsesClassInPackageBInMethodThrowsClause;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.b.ExceptionInPackageB;

public class RuleClassVisitorTestCase extends TestCase {
	public void testSource() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(InPackageA.class);

		assertTrue(classSummary.contains(create(InPackageA.class, ElementType.SOURCE)));
	}

	public void testConstructor() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(HasZeroArgConstructor.class);

		assertTrue(classSummary.contains(new ConstructorDeclaration(null, new String[0])));
	}

	public void testVoidReturnMethod() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(Simple.class);

		assertTrue(classSummary.contains(Simple.VOID_RETURN_METHOD));
	}

	public void testNoArgMethod() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(Simple.class);

		assertTrue(classSummary.contains(Simple.NO_ARG_METHOD));
	}

	public void testSingleArgMethod() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(Simple.class);

		assertTrue(classSummary.contains(Simple.SINGLE_ARG_METHOD));
	}

	public void testTwoArgMethod() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(Simple.class);

		assertTrue(classSummary.contains(Simple.TWO_ARG_METHOD));
	}

	public void testThrowsClause() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(
			UsesClassInPackageBInMethodThrowsClause.class);

		assertTrue(classSummary.contains(create(ExceptionInPackageB.class, ElementType.USES)));
	}

	private static CodeElement create(Class clazz, ElementType codeType) {
		return CodeElement.create(new ClassName(clazz.getName()), codeType);
	}
}

