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

import org.adarwin.rule.ConstructorRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.SourceRule;
import org.adarwin.rule.UsesRule;
import org.adarwin.testmodel.CallsSimple;
import org.adarwin.testmodel.HasSingleArgConstructor;
import org.adarwin.testmodel.HasTwoArgConstructor;
import org.adarwin.testmodel.HasZeroArgConstructor;
import org.adarwin.testmodel.Simple;

import java.io.IOException;

public class ConstructorRuleTestCase extends RuleTestCase {
	public void testZeroArgMatchingConstructor() throws IOException {
		Rule rule = createConstructorRule(new String[0]);
		
		assertNumMatches(1, rule, HasZeroArgConstructor.class);
	}
	
	public void testZeroArgNonMatchingConstructor() throws IOException {
		Rule rule = createConstructorRule(new String[0]);
		
		assertNumMatches(0, rule, HasSingleArgConstructor.class);
	}
	
	public void testSingleArgMatchingConstructor() throws IOException {
		Rule rule = createConstructorRule(new String[] {Integer.class.getName()});
		
		assertNumMatches(1, rule, HasSingleArgConstructor.class);
	}
	
	public void testSingleArgNonMatchingConstructor() throws IOException {
		Rule rule = createConstructorRule(new String[] {Integer.class.getName()});
		
		assertNumMatches(0, rule, HasTwoArgConstructor.class);
	}

	public void testTwoArgMatchingConstructor() throws IOException {
		Rule rule = createConstructorRule(new String[] {
			Integer.class.getName(), String.class.getName()});

		assertNumMatches(1, rule, HasTwoArgConstructor.class);		
	}
	
	public void testTwoArgNonMatchingConstructor() throws IOException {
		Rule rule = createConstructorRule(new String[] {
			Integer.class.getName(), String.class.getName()});

		assertNumMatches(0, rule, HasSingleArgConstructor.class);		
	}

	public void testInvokingConstructor() throws IOException {
		Rule rule = new UsesRule(ConstructorRule.create(Simple.class.getName(), (new String[0])));

		assertNumMatches(1, rule, CallsSimple.class);
	}

	public void testInvokingConstructorNotRegardedAsHavingConstructor() throws IOException {
		Rule rule = new SourceRule(createConstructorRule(new String[] {String.class.getName()}));
		
		assertNumMatches(0, rule, CallsSimple.class);
	}

	public void testHavingConstructorNotRegardedAsInvokingConstructor() throws IOException {
		Rule rule = new UsesRule(ConstructorRule.create(
			Simple.class.getName(), (new String[] {String.class.getName()})));
		
		assertNumMatches(0, rule, Simple.class);
	}

	private Rule createConstructorRule(String[] parameterTypes) {
		return ConstructorRule.create(".*", parameterTypes);
	}
}
