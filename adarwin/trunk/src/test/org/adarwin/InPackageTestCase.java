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
import org.adarwin.rule.SourceRule;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.b.InPackageB;

public class InPackageTestCase extends RuleTestCase {
    private final Rule rule = new SourceRule(createPackageRule(InPackageA.class));
    
    public void testInPackage() {
    	assertNumMatches(1, rule, InPackageA.class);
    }

    public void testNotInPackage() {
    	assertNumMatches(0, rule, InPackageB.class);
    }
}
