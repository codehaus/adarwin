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
import org.adarwin.rule.FalseRule;
import org.adarwin.rule.NotRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.TrueRule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GrammarTestCase extends TestCase {
    public void testAddMapping() {
        String rule = "rule";
        Grammar grammar = new Grammar();
        grammar.addMapping(rule, TrueRule.class);

        assertEquals(TrueRule.class, grammar.getClass(rule));
    }

    public void testTwoMappingsToDifferentClasses() {
        String firstRule = "firstRule";
        String secondRule = "secondRule";

        Grammar grammar = new Grammar();
        grammar.addMapping(firstRule, TrueRule.class);
        grammar.addMapping(secondRule, FalseRule.class);

        assertEquals(TrueRule.class, grammar.getClass(firstRule));
        assertEquals(FalseRule.class, grammar.getClass(secondRule));
    }

    public void testReverseMapping() {
        String rule = "rule";
        Grammar grammar = new Grammar();
        grammar.addMapping(rule, TrueRule.class);

        assertEquals(rule, grammar.getRule(TrueRule.class));
    }

	public void testAddSynonymForNegate() throws BuilderException, ClassNotFoundException {
		Grammar grammar = new Grammar();
		grammar.addMapping("not", NotRule.class);
		grammar.addMapping("true", TrueRule.class);

		Rule rule = new RuleBuilder(grammar).buildRule("not(true)");

		assertEquals("not(true)", rule.getExpression(grammar));
	}

	public void testAddSynonymFromPropertiesFile() throws IOException, ClassNotFoundException, BuilderException {
		Grammar grammar = new Grammar(createPropertiesFile());
		String expression = "not(true)";
		RuleBuilder ruleBuilder = new RuleBuilder(grammar);
		Rule rule = ruleBuilder.buildRule(expression);

		assertEquals(expression, rule.getExpression(grammar));
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
