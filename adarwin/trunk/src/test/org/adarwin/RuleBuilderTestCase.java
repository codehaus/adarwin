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

import org.adarwin.rule.AndRule;
import org.adarwin.rule.ConstructorRule;
import org.adarwin.rule.MethodRule;
import org.adarwin.rule.NotRule;
import org.adarwin.rule.PackageRule;
import org.adarwin.rule.ParentRule;
import org.adarwin.rule.Rule;
import org.adarwin.rule.SourceRule;
import org.adarwin.rule.UsesRule;
import org.adarwin.testmodel.a.InPackageA;

import java.io.IOException;

public class RuleBuilderTestCase extends RuleTestCase {
    private final String INCORRECT_RULE_BUILT = "Incorrect rule built";

    public void testTrueRuleGrammer() throws BuilderException {
		RuleClassBindings ruleClassBindings = new RuleClassBindings("true", TrueRule.class);

        RuleBuilder ruleBuilder = new RuleBuilder(ruleClassBindings);

        String expression = "true";

        Rule rule = ruleBuilder.buildRule(expression);
        
        assertEquals(expression, rule.toString(ruleClassBindings));
    }

    public void testInPackageRuleGrammer() throws BuilderException {
		RuleClassBindings ruleClassBindings = new RuleClassBindings(new String[] {"src", "package"},
            new Class[] {SourceRule.class, PackageRule.class});

        RuleBuilder ruleBuilder = new RuleBuilder(ruleClassBindings);

        String expression = "src(package(x))";

        Rule rule = ruleBuilder.buildRule(expression);

        assertEquals(expression, rule.toString(ruleClassBindings));
    }

    public void testInPackageAndUsesPackage() throws BuilderException {
		RuleClassBindings ruleClassBindings = new RuleClassBindings(
            new String[] {"and", "src", "uses", "package"},
            new Class[] {AndRule.class, SourceRule.class, UsesRule.class, PackageRule.class});

        RuleBuilder ruleBuilder = new RuleBuilder(ruleClassBindings);

        String expression = "and(src(package(org.adarwin.testmodel.a)), uses(package(org.adarwin.testmodel.b)))";

        Rule rule = ruleBuilder.buildRule(expression);

        assertEquals(expression, rule.toString(ruleClassBindings));
    }

    public void testTwoLevelsOfNesting() throws BuilderException {
		RuleClassBindings ruleClassBindings = new RuleClassBindings(new String[] {"not", "and", "true"},
            new Class[] {NotRule.class, AndRule.class, TrueRule.class});

        RuleBuilder ruleBuilder = new RuleBuilder(ruleClassBindings);

        String expression = "not(and(true, true))";

        Rule rule = ruleBuilder.buildRule(expression);

        assertEquals(expression, rule.toString(ruleClassBindings));
    }

    public void testInPackageRule() throws BuilderException, IOException {
        RuleBuilder ruleBuilder = new RuleBuilder(new RuleClassBindings(
            new String[] {"src", "package"},
            new Class[] {SourceRule.class, PackageRule.class}));

        String expression = "src(package(org.adarwin.testmodel.a))";

        Rule rule = ruleBuilder.buildRule(expression);

        assertNumMatches(1, rule, InPackageA.class);
    }

    public void testAndRule() throws BuilderException, IOException {
		RuleClassBindings ruleClassBindings = new RuleClassBindings(new String[] {"and", "true"},
            new Class[] {AndRule.class, TrueRule.class});

        RuleBuilder ruleBuilder = new RuleBuilder(ruleClassBindings);

        String expression = "and(true, true, true)";

        Rule rule = ruleBuilder.buildRule(expression);

        assertEquals(INCORRECT_RULE_BUILT, expression, rule.toString(ruleClassBindings));

        assertNumMatches(1, rule, InPackageA.class);
    }

    public void testConstructorRule() throws BuilderException {
    	RuleClassBindings bindings = new RuleClassBindings("constructor", ConstructorRule.class);
		RuleBuilder ruleBuilder = new RuleBuilder(bindings);
		
		String expression = "constructor(className(param1, param2))";
		Rule rule = ruleBuilder.buildRule(expression);
		
		assertEquals(expression, rule.toString(bindings));
	}
    
    public void testMethodRule() throws BuilderException {
    	RuleClassBindings bindings = new RuleClassBindings("method", MethodRule.class);
    	RuleBuilder ruleBuilder = new RuleBuilder(bindings);

    	String expression = "method(returnType methodName(param1, param2, param3))";
    	Rule rule = ruleBuilder.buildRule(expression);

    	assertEquals(expression, rule.toString(bindings));
    }

	public void testParentRule() throws BuilderException {
		RuleClassBindings bindings = new RuleClassBindings("parent", ParentRule.class);
		RuleBuilder ruleBuilder = new RuleBuilder(bindings);
		
		String expression = "parent(someClass)";
		Rule rule = ruleBuilder.buildRule(expression);
		
		assertEquals(expression, rule.toString(bindings));
	}

    public void testUnknownRule() {
        RuleBuilder ruleBuilder = new RuleBuilder(new RuleClassBindings("true", TrueRule.class));

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
			new RuleBuilder(null).buildRule(expression);
			fail();
		}
		catch (BuilderException be) {
			assertEquals("Unbalanced expression: \"" + expression + "\"", be.getMessage());
		}
	}

	public void testMultipleRules() throws BuilderException {
		RuleClassBindings ruleClassBindings = new RuleClassBindings("true", TrueRule.class);

		RuleBuilder ruleBuilder = new RuleBuilder(ruleClassBindings);

		String expression = "true, true";

		Rule[] rules = ruleBuilder.buildRules(expression);
	    
		assertNotNull(rules);
		assertEquals(2, rules.length);
        
        for (int rLoop = 0; rLoop < rules.length; ++rLoop) {
			Rule rule = rules[rLoop];

			assertEquals("true", rule.toString(ruleClassBindings));
        }
	}
	
	public void testWhiteSpaceInRuleIsIrrelevant() throws BuilderException {
		RuleClassBindings ruleClassBindings = new RuleClassBindings(new String[] {"and", "true"},
			new Class[] {AndRule.class, TrueRule.class});

		RuleBuilder ruleBuilder = new RuleBuilder(ruleClassBindings);

		String expression = "  \t\nand \n\t(\n\n\t  true  \t\n\t , \t\t\n true  \t\t\n)  \n\n\t";
		String SimplyfiedExpression = "and(true, true)";

		Rule rule = ruleBuilder.buildRule(expression);

		assertEquals(INCORRECT_RULE_BUILT, SimplyfiedExpression, rule.toString(ruleClassBindings));
	}
	
	public void testWhiteSpaceBetweenRulesIsIrrelevant() throws BuilderException {
		RuleClassBindings ruleClassBindings = new RuleClassBindings(new String[] {"and", "true"},
			new Class[] {AndRule.class, TrueRule.class});

		RuleBuilder ruleBuilder = new RuleBuilder(ruleClassBindings);

		String subExpression = "and(true, true)";
		String expression = subExpression + " \t\n, \n\n\t " + subExpression;

		Rule[] rules = ruleBuilder.buildRules(expression);
		
		assertEquals(2, rules.length);
		assertEquals(subExpression, rules[0].toString(ruleClassBindings));
		assertEquals(subExpression, rules[1].toString(ruleClassBindings));
	}
	
	public void testWhiteSpaceBetweenVariableAssignmentIsIrrelevant() throws BuilderException {
		RuleClassBindings ruleClassBindings = new RuleClassBindings(new String[] {"and", "true"},
			new Class[] {AndRule.class, TrueRule.class});

		RuleBuilder ruleBuilder = new RuleBuilder(ruleClassBindings);

		String variable = "and(true, true)";
		String expression = "var = " + variable;

		Rule rule = ruleBuilder.buildRule(expression);
		
		assertNull(rule);
		
		Rule var = ruleBuilder.getVariable("var");
		
		assertEquals(variable, var.toString(ruleClassBindings));
	}
	
	public void testVariableAssignment() {
		
	}
	
	public void testUseVariable() {
		
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
