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

import org.adarwin.rule.ConstructorRule;
import org.adarwin.rule.Rule;
import org.adarwin.testmodel.HasTwoArgConstructor;
import org.adarwin.testmodel.HasSingleArgConstructor;
import org.adarwin.testmodel.HasZeroArgConstructor;

import junit.framework.TestCase;

public class ConstructorRuleTestCase extends TestCase {
	TestUtil testUtil = new TestUtil();
	
	public void testZeroArgMatchingConstructor() throws IOException {
		Rule rule = new ConstructorRule();
		
		testUtil.assertRuleMatchingCount(1, rule, HasZeroArgConstructor.class);
	}
	
	public void testZeroArgNonMatchingConstructor() throws IOException {
		Rule rule = new ConstructorRule();
		
		testUtil.assertRuleMatchingCount(0, rule, HasSingleArgConstructor.class);
	}
	
	public void testSingleArgMatchingConstructor() throws IOException {
		Rule rule = new ConstructorRule(new Class[] {Integer.class});
		
		testUtil.assertRuleMatchingCount(1, rule, HasSingleArgConstructor.class);
	}
	
	public void testSingleArgNonMatchingConstructor() throws IOException {
		Rule rule = new ConstructorRule(new Class[] {Integer.class});
		
		testUtil.assertRuleMatchingCount(0, rule, HasTwoArgConstructor.class);
	}

	public void testTwoArgMatchingConstructor() throws IOException {
		Rule rule = new ConstructorRule(new Class[] {Integer.class, String.class});

		testUtil.assertRuleMatchingCount(1, rule, HasTwoArgConstructor.class);		
	}
	
	public void testTwoArgNonMatchingConstructor() throws IOException {
		Rule rule = new ConstructorRule(new Class[] {Integer.class, String.class});

		testUtil.assertRuleMatchingCount(0, rule, HasSingleArgConstructor.class);		
	}
}
