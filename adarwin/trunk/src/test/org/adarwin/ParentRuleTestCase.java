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

import org.adarwin.rule.ParentRule;
import org.adarwin.rule.Rule;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.b.IInPackageB;
import org.adarwin.testmodel.b.InPackageB;

public class ParentRuleTestCase extends RuleTestCase {
	public void testNeitherExtendsNorImplements() {
		Rule rule = new ParentRule(InPackageB.class);

		assertNumMatches(0, rule, InPackageA.class);
	}

	public void testExtends() {
		class UsesClassForBaseClass extends InPackageB {
		}
		
		Rule rule = new ParentRule(InPackageB.class);

		assertNumMatches(1, rule, UsesClassForBaseClass.class);
	}

	public void testImplements() {
		class UsesClassForBaseInterface implements IInPackageB {
		}
		
		Rule rule = new ParentRule(IInPackageB.class);

		assertNumMatches(1, rule, UsesClassForBaseInterface.class);
	}

	static interface IUsesClassForBaseInterface extends IInPackageB {
	}

	public void testExtendsInterface() {
		Rule rule = new ParentRule(IInPackageB.class);

		assertNumMatches(1, rule, IUsesClassForBaseInterface.class);
	}
}
