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

public class RuleClassVisitorTestCase extends TestCase{
	public void testSource() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(new ClassFile(InPackageA.class));

		assertTrue(classSummary.getDependancies().contains(
			new CodeElement(InPackageA.class.getName(), ElementType.SOURCE)));
	}
	
	public void testConstructor() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(
			new ClassFile(HasZeroArgConstructor.class));
		
		assertTrue(classSummary.getConstructors().contains(Constructor.EMPTY_CONSTRUCTOR));
	}
	
	public void testVoidReturnMethod() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(new ClassFile(Simple.class));
		Method method = new Method(Simple.VOID_RETURN_METHOD, Void.TYPE, new Class[0]);
		assertTrue(classSummary.getMethods().contains(method));
	}
	
	public void testNoArgMethod() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(new ClassFile(Simple.class));
		Method method = new Method(Simple.NO_ARG_METHOD, Integer.class, new Class[0]);
		assertTrue(classSummary.getMethods().contains(method));
	}

	public void testSingleArgMethod() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(new ClassFile(Simple.class));
		Method method = new Method(Simple.SINGLE_ARG_METHOD, Integer.class, new Class[] {String.class});
		assertTrue(classSummary.getMethods().contains(method));
	}
	
	public void testTwoArgMethod() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(new ClassFile(Simple.class));
		Method method = new Method(Simple.TWO_ARG_METHOD, Integer.class,
			new Class[] {String.class, String.class});
		assertTrue(classSummary.getMethods().contains(method));
	}
	
	public void testThrowsClause() throws IOException {
		ClassSummary classSummary = new RuleClassVisitor().visit(
			new ClassFile(UsesClassInPackageBInMethodThrowsClause.class));
			
		assertTrue(classSummary.getDependancies().contains(
			new CodeElement(ExceptionInPackageB.class.getName(), ElementType.USES)));
	}
}

