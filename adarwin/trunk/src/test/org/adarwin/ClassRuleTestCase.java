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

import org.adarwin.rule.ClassRule;
import org.adarwin.rule.Rule;
import org.adarwin.testmodel.a.InPackageA;

public class ClassRuleTestCase extends TestCase {
    public void testNonMatchingClass() throws BuilderException, IOException {
        String expression = "class(Fred)";

        Rule rule = new RuleBuilder(new Grammar("class", ClassRule.class)).buildRule(expression);

        assertEquals(0, new ClassFile(InPackageA.class).evaluate(rule).getCount());
    }

    public void testMatchingClass() throws BuilderException, IOException {
        String expression = "class(In.*)";

        Rule rule = new RuleBuilder(new Grammar("class", ClassRule.class)).buildRule(expression);

        assertEquals(1, new ClassFile(InPackageA.class).evaluate(rule).getCount());
    }
}
