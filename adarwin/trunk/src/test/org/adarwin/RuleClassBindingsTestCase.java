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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.adarwin.rule.NotRule;
import org.adarwin.rule.Rule;

public class RuleClassBindingsTestCase extends TestCase {
    public void testSingleMapping() {
        String rule = "rule";
		RuleClassBindings ruleClassBindings = new RuleClassBindings(rule, TrueRule.class);

        assertEquals(TrueRule.class, ruleClassBindings.getClass(rule));
    }

    public void testTwoMappingsToDifferentClasses() {
        String firstRule = "firstRule";
        String secondRule = "secondRule";

		RuleClassBindings ruleClassBindings = new RuleClassBindings(
			new String[] {firstRule, secondRule},
			new Class[] {TrueRule.class, FalseRule.class});

        assertEquals(TrueRule.class, ruleClassBindings.getClass(firstRule));
        assertEquals(FalseRule.class, ruleClassBindings.getClass(secondRule));
    }

    public void testReverseMapping() {
        String rule = "rule";
		RuleClassBindings ruleClassBindings = new RuleClassBindings(rule, TrueRule.class);

        assertEquals(rule, ruleClassBindings.getRule(TrueRule.class));
    }

	public void testAddSynonymForNegate() throws BuilderException {
		RuleClassBindings ruleClassBindings = new RuleClassBindings(
			new String[] {"not", "true"},
			new Class[] {NotRule.class, TrueRule.class});

		Rule rule = new RuleBuilder(ruleClassBindings).buildRule("not(true)");

		assertEquals("not(true)", rule.toString(ruleClassBindings));
	}

	public void testAddSynonymFromPropertiesFile() throws IOException, ClassNotFoundException, BuilderException {
		RuleClassBindings ruleClassBindings = new RuleClassBindings(createPropertiesFile());
		String expression = "not(true)";
		RuleBuilder ruleBuilder = new RuleBuilder(ruleClassBindings);
		Rule rule = ruleBuilder.buildRule(expression);

		assertEquals(expression, rule.toString(ruleClassBindings));
	}

	private String createPropertiesFile() throws IOException {
		File propertiesFile = File.createTempFile("rule", ".properties");
		FileOutputStream fileOutputStream = new FileOutputStream(propertiesFile);
		String notLine = "not=" + NotRule.class.getName();
		fileOutputStream.write(notLine.getBytes());
		fileOutputStream.write('\n');
		String trueLine = "true=" + TrueRule.class.getName();
		fileOutputStream.write(trueLine.getBytes());
		fileOutputStream.close();

		return propertiesFile.getAbsolutePath();
	}	
}
