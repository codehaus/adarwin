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

import com.mockobjects.dynamic.OrderedMock;
import junit.framework.TestCase;
import org.adarwin.rule.ElementType;
import org.adarwin.rule.Rule;
import org.adarwin.testmodel.a.InPackageA;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RuleClassVisitorTestCase extends TestCase{
	public void testSendsSummaryToRule() throws IOException {
		OrderedMock mockRule = new OrderedMock(Rule.class);
		mockRule.expectAndReturn("inspect", new ClassSummary(
			createSet(new CodeElement[] {
					new CodeElement(InPackageA.class.getName(), ElementType.SOURCE),
					new CodeElement(Object.class.getName(), ElementType.EXTENDS_OR_IMPLEMENTS),
					new CodeElement(Object.class.getName(), ElementType.USES)
				})), new Boolean(false));

		Rule rule = (Rule) mockRule.proxy();
		RuleClassVisitor ruleClassVisitor = new RuleClassVisitor(rule);
		ruleClassVisitor.visit(new ClassFile(InPackageA.class));

		mockRule.verify();
	}

	private Set createSet(CodeElement[] codeElements) {
		Set set = new HashSet();

		set.addAll(Arrays.asList(codeElements));

		return set;
	}
}
