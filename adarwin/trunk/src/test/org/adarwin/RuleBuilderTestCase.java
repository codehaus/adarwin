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
import org.easymock.MockControl;

public class RuleBuilderTestCase extends RuleTestCase {
	private final MockControl ruleBuilderListenerControl =
		MockControl.createNiceControl(RuleConsumer.class);
	private final RuleConsumer ruleBuilderListener =
		(RuleConsumer) ruleBuilderListenerControl.getMock();
	
	private final RuleClassBindings ruleClassBindings = new RuleClassBindings(
		new String[] {
			"true", "and", "parent", "constructor", "src", "package", "method", "uses", "not"
		},
		new Class[] {
			TrueRule.class, AndRule.class, ParentRule.class, ConstructorRule.class,
			SourceRule.class, PackageRule.class, MethodRule.class, UsesRule.class,
			NotRule.class
		});
	private final RuleBuilder ruleBuilder = new RuleBuilder(ruleClassBindings);

    public void testTrueRuleGrammer() throws ADarwinException {
    	assertEquals(new TrueRule(), "true");
    }

	public void testInPackageRuleGrammer() throws ADarwinException {
		assertEquals(new SourceRule(new PackageRule("x")), "src(package(x))");
    }

    public void testInPackageAndUsesPackage() throws ADarwinException {
    	assertEquals(new AndRule(new Rule[] {
        	new SourceRule(new PackageRule("package-a")),
			new UsesRule(new PackageRule("package-b"))
        }), "and(src(package(package-a)), uses(package(package-b)))");
    }

    public void testTwoLevelsOfNesting() throws ADarwinException {
    	assertEquals(new NotRule(new AndRule(new Rule[] {new TrueRule(), new TrueRule()})),
    		"not(and(true, true))");
    }

    public void testInPackageRule() throws ADarwinException, ADarwinException {
    	assertEquals(new SourceRule(new PackageRule("package-a")), "src(package(package-a))");
    }

    public void testAndRule() throws ADarwinException, ADarwinException {
    	assertEquals(new AndRule(new Rule[] {new TrueRule(), new TrueRule(), new TrueRule()}),
    		"and(true, true, true)");
    }

    public void testConstructorRule() throws ADarwinException {
    	assertEquals(new ConstructorRule("className(param1, param2)"), 
    		"constructor(className(param1, param2))");
	}

    public void testMethodRule() throws ADarwinException {
    	assertEquals(new MethodRule("returnType methodName(param1, param2, param3)"),
    		"method(returnType methodName(param1, param2, param3))");
    }

	public void testParentRule() throws ADarwinException {
		assertEquals(new ParentRule("someClass"), "parent(someClass)");
	}

	public void testWhiteSpaceInRuleIsIrrelevant() throws ADarwinException {
		assertEquals(new AndRule(new Rule[] {new TrueRule(), new TrueRule()}),
			"  \t\nand \n\t(\n\n\t  true  \t\n\t , \t\t\n true  \t\t\n)  \n\n\t");
	}

    public void testUnknownRule() {
		ruleBuilderListenerControl.replay();

		try {
        	ruleBuilder.buildRule("gobbledygook", ruleBuilderListener);

			fail();
		}
		catch (ADarwinException be) {
			assertEquals("No such rule: " + "gobbledygook", be.getMessage());
		}

		ruleBuilderListenerControl.verify();
    }

	public void testUnbalancedBrackets() {
		ruleBuilderListenerControl.replay();

		String expression = "(";
		try {
			new RuleBuilder(null).buildRule(expression, ruleBuilderListener);
			fail();
		}
		catch (ADarwinException be) {
			assertEquals("Unbalanced expression: \"" + expression + "\"", be.getMessage());
		}
		ruleBuilderListenerControl.verify();
	}

	public void testMultipleRules() throws ADarwinException {
		expectRule(new TrueRule());
		expectRule(new TrueRule());

		ruleBuilderListenerControl.replay();

		ruleBuilder.produce("true, true", ruleBuilderListener);

		ruleBuilderListenerControl.verify();
	}

	public void testMultipleComplexRules() throws ADarwinException {
		expectRule(new SourceRule(new PackageRule("package-a")));
		expectRule(new SourceRule(new PackageRule("package-b")));

		ruleBuilderListenerControl.replay();

		ruleBuilder.produce("src(package(package-a)),src(package(package-b))",
			ruleBuilderListener);

		ruleBuilderListenerControl.verify();
	}

	public void testWhiteSpaceBetweenRulesIsIrrelevant() throws ADarwinException {
		AndRule expectedRule = new AndRule(new Rule[] {new TrueRule(), new TrueRule()});
		expectRule(expectedRule);
		expectRule(expectedRule);

		ruleBuilderListenerControl.replay();

		ruleBuilder.produce("and(true, true) \t\n, \n\n\t and(true, true)",
			ruleBuilderListener);

		ruleBuilderListenerControl.verify();
	}

	public void testWhiteSpaceBetweenVariableAssignmentIsIrrelevant() throws ADarwinException {
		RuleClassBindings ruleClassBindings = new RuleClassBindings(new String[] {"and", "true"},
			new Class[] {AndRule.class, TrueRule.class});

		RuleBuilder ruleBuilder = new RuleBuilder(ruleClassBindings);

		String variable = "and(true, true)";
		String expression = "var = " + variable;

		Rule rule = ruleBuilder.buildRule(expression);

		assertEquals(Rule.NULL, rule);

		Rule var = ruleBuilder.getVariable("var");

		assertEquals(variable, var.toString(ruleClassBindings));
	}
//	
//	public void testVariableAssignment() {
//		
//	}
//	
//	public void testUseVariable() {
//		
//	}
	
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

	private void assertEquals(Rule expectedRule, String expression) throws ADarwinException {
		expectRule(expectedRule);

		ruleBuilderListenerControl.replay();

		ruleBuilder.buildRule(expression, ruleBuilderListener);

		ruleBuilderListenerControl.verify();
	}

	private void expectRule(Rule rule) throws ADarwinException {
		ruleBuilderListener.consume(rule, ruleClassBindings);
		ruleBuilderListenerControl.setReturnValue(true);
	}
}
