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

import junit.framework.TestCase;

import org.adarwin.rule.NameRule;
import org.adarwin.rule.Rule;
import org.easymock.MockControl;

import java.util.Collections;

public class NameRuleTestCase extends TestCase {
	public void testDelegatesToWrappedRule() {
		MockControl ruleControl = MockControl.createNiceControl(Rule.class);
		Rule rule = (Rule) ruleControl.getMock();

		ClassSummary classSummary = new ClassSummary("className", Collections.EMPTY_SET);
		ClassSummary inspectedClassSummary = new ClassSummary("inspectedClassName",
			Collections.EMPTY_SET);

		rule.inspect(classSummary);
		ruleControl.setReturnValue(inspectedClassSummary);

		ruleControl.replay();

		assertEquals(inspectedClassSummary, new NameRule("name", rule).inspect(classSummary));
		
		ruleControl.verify();
	}
}
