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

import org.adarwin.rule.ElementType;
import org.adarwin.testmodel.HasZeroArgConstructor;
import org.adarwin.testmodel.UsesClassInPackageBInMethodThrowsClause;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.b.ExceptionInPackageB;

public class RuleClassVisitorTestCase extends TestCase{
	public void testSendsSummaryToRule() throws IOException {
        ClassSummary expected = new ClassSummary(InPackageA.class.getName(),
        	Constructor.EMPTY_CONSTRUCTOR, null,
        	new CodeElement(InPackageA.class.getName(), ElementType.SOURCE));

		assertEquals(expected, new RuleClassVisitor().visit(new ClassFile(InPackageA.class)));
	}
	
	public void testConstructor() throws IOException {
		ClassSummary expected = new ClassSummary(HasZeroArgConstructor.class.getName(),
			Constructor.EMPTY_CONSTRUCTOR, null,
			new CodeElement(HasZeroArgConstructor.class.getName(), ElementType.SOURCE));
			
		assertEquals(expected,
			new RuleClassVisitor().visit(new ClassFile(HasZeroArgConstructor.class)));
	}
	
	public void testThrowsClause() throws IOException {
		Set dependancies = new HashSet();

		dependancies.add(new CodeElement(ExceptionInPackageB.class.getName(), ElementType.USES));
		dependancies.add(new CodeElement(
			UsesClassInPackageBInMethodThrowsClause.class.getName(), ElementType.SOURCE));
		
		ClassSummary expected = new ClassSummary(
			UsesClassInPackageBInMethodThrowsClause.class.getName(),
			ClassSummary.createSet(Constructor.EMPTY_CONSTRUCTOR),
				Collections.EMPTY_SET, dependancies);

		assertEquals(expected, new RuleClassVisitor().visit(
			new ClassFile(UsesClassInPackageBInMethodThrowsClause.class)));
	}
}

