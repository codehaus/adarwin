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
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import junit.framework.TestCase;

import org.adarwin.rule.AndRule;
import org.adarwin.rule.NotRule;
import org.adarwin.rule.PackageRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.SourceRule;
import org.adarwin.rule.TrueRule;
import org.adarwin.rule.UsesRule;
import org.adarwin.testmodel.a.InPackageA;

public class RuleBuilderTestCase extends TestCase {
    private final String INCORRECT_RULE_BUILT = "Incorrect rule built";
    private final String INCORRECT_NUMBER_OF_RULE_MATCHES = "Incorrect number of rule matches";

    public void testTrueRuleGrammer() throws BuilderException, ClassNotFoundException {
        Grammar grammar = createGrammar("true", TrueRule.class);

        RuleBuilder ruleBuilder = new RuleBuilder(grammar);

        String expression = "true";

        Rule rule = ruleBuilder.buildRule(expression);
        
        assertEquals(expression, rule.getExpression(grammar));
    }

    private Grammar createGrammar(String name, Class clazz) {
        Grammar grammar = new Grammar();
        grammar.addMapping(name, clazz);
        return grammar;
    }

    public void testInPackageRuleGrammer() throws BuilderException, ClassNotFoundException {
        Grammar grammar = new Grammar(new String[] {"src", "package"},
            new Class[] {SourceRule.class, PackageRule.class});

        RuleBuilder ruleBuilder = new RuleBuilder(grammar);

        String expression = "src(package(x))";

        Rule rule = ruleBuilder.buildRule(expression);

        assertEquals(expression, rule.getExpression(grammar));
    }

    public void testInPackageAndUsesPackage() throws BuilderException, ClassNotFoundException {
        Grammar grammar = new Grammar(
            new String[] {"and", "src", "uses", "package"},
            new Class[] {AndRule.class, SourceRule.class, UsesRule.class, PackageRule.class});

        RuleBuilder ruleBuilder = new RuleBuilder(grammar);

        String expression = "and(src(package(org.adarwin.testmodel.a)), uses(package(org.adarwin.testmodel.b)))";

        Rule rule = ruleBuilder.buildRule(expression);

        assertEquals(expression, rule.getExpression(grammar));
    }

    public void testTwoLevelsOfGrammar() throws BuilderException, ClassNotFoundException {
        Grammar grammar = new Grammar(new String[] {"not", "and", "true"},
            new Class[] {NotRule.class, AndRule.class, TrueRule.class});

        RuleBuilder ruleBuilder = new RuleBuilder(grammar);

        String expression = "not(and(true, true))";

        Rule rule = ruleBuilder.buildRule(expression);

        assertEquals(expression, rule.getExpression(grammar));
    }

    public void testInPackageRule() throws BuilderException, IOException, ClassNotFoundException {
        RuleBuilder ruleBuilder = new RuleBuilder(new Grammar(
            new String[] {"src", "package"},
            new Class[] {SourceRule.class, PackageRule.class}));

        String expression = "src(package(org.adarwin.testmodel.a))";

        Rule rule = ruleBuilder.buildRule(expression);

        assertEquals("Incorrect number of rule matches", 1, new ClassFile(InPackageA.class).evaluate(rule).getCount());
    }

    public void testAndRule() throws BuilderException, IOException, ClassNotFoundException {
        Grammar grammar = new Grammar(new String[] {"and", "true"},
            new Class[] {AndRule.class, TrueRule.class});

        RuleBuilder ruleBuilder = new RuleBuilder(grammar);

        String expression = "and(true, true, true)";

        Rule rule = ruleBuilder.buildRule(expression);

        assertEquals(INCORRECT_RULE_BUILT, expression, rule.getExpression(grammar));

        assertTrue(INCORRECT_NUMBER_OF_RULE_MATCHES, new ClassFile(InPackageA.class).evaluate(rule).getCount() > 0);
    }

    public void testUnknownRule() throws BuilderException, ClassNotFoundException {
        RuleBuilder ruleBuilder = new RuleBuilder(new Grammar());

		try {
        	ruleBuilder.buildRule("gobbledygook");
			fail();
		}
		catch (BuilderException be) {
			assertEquals("No such rule: " + "gobbledygook", be.getMessage());
		}
    }

	public void testUnbalancedBrackets() {
		String expression = "(";
		try {
			new RuleBuilder(new Grammar()).buildRule(expression);
			fail();
		}
		catch (BuilderException be) {
			assertEquals("Unbalanced expression: \"" + expression + "\"", be.getMessage());
		}
	}

	public void testMultipleRules() throws BuilderException, ClassNotFoundException {
		Grammar grammar = createGrammar("true", TrueRule.class);

		RuleBuilder ruleBuilder = new RuleBuilder(grammar);

		String expression = "true, true";

		Rule[] rules = ruleBuilder.buildRules(expression);
	    
		assertNotNull(rules);
		assertEquals(2, rules.length);
        
        for (int rLoop = 0; rLoop < rules.length; ++rLoop) {
			Rule rule = rules[rLoop];

			assertEquals("true", rule.getExpression(grammar));
        }
	}
	
	public void testParseSimpleExpression() {
		String first = "a(b(c()))";
		String second = "d()";
		String expression = first + ", " + second;
	
		String[] parsed = RuleBuilder.parse(expression);
		
		assertEquals(2, parsed.length);
		assertEquals(first, parsed[0]);
		assertEquals(second, parsed[1]);
	}
	
	public void testParseComplexExpression() {
		String first = "a(,b,(c,,()))";
		String second = "d(,)";
		String expression = first + ", " + second;
	
		String[] parsed = RuleBuilder.parse(expression);
		
		assertEquals(2, parsed.length);
		assertEquals(first, parsed[0]);
		assertEquals(second, parsed[1]);
	}
	
}
