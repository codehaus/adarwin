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

import java.util.Properties;

public class RuleBuilderTestCase extends RuleTestCase {
	private final MockControl loggerControl = MockControl.createNiceControl(Logger.class);
	private final Logger logger = (Logger) loggerControl.getMock();

    public void testTrueRuleGrammer() {
    	assertEquals(new TrueRule(), "true()");
    }

	public void testInPackageRuleGrammer() {
		assertEquals(new SourceRule(new PackageRule("x")), "src(package(x))");
    }

    public void testInPackageAndUsesPackage() {
    	assertEquals(new AndRule(new Rule[] {
        	new SourceRule(new PackageRule("package-a")),
			new UsesRule(new PackageRule("package-b"))
        }), "and(src(package(package-a)), uses(package(package-b)))");
    }

    public void testTwoLevelsOfNesting() {
    	assertEquals(new NotRule(new AndRule(new Rule[] {new TrueRule(), new TrueRule()})),
    		"not(and(true(), true()))");
    }

    public void testInPackageRule() {
    	assertEquals(new SourceRule(new PackageRule("package-a")), "src(package(package-a))");
    }

    public void testAndRule() {
    	assertEquals(new AndRule(new Rule[] {new TrueRule(), new TrueRule(), new TrueRule()}),
    		"and(true(), true(), true())");
    }

    public void testConstructorRule() {
    	assertEquals(new ConstructorRule("className(param1, param2)"), 
    		"constructor(className(param1, param2))");
	}

    public void testMethodRule() {
    	assertEquals(new MethodRule("returnType methodName(param1, param2, param3)"),
    		"method(returnType methodName(param1, param2, param3))");
    }

	public void testParentRule() {
		assertEquals(new ParentRule("someClass"), "parent(someClass)");
	}

	public void testWhiteSpaceInRuleIsIrrelevant() {
		assertEquals(new AndRule(new Rule[] {new TrueRule(), new TrueRule()}),
			"  \t\nand \n\t(\n\n\t  true()  \t\n\t , \t\t\n true()  \t\t\n)  \n\n\t");
	}
	
	public void testSimpleVariable() {
		assertEquals(new TrueRule(), "var = true(), var");
	}

	public void testComplexVariable() {
		assertEquals(new AndRule(new Rule[] {new TrueRule()}), "var = true(), and(var)");
	}
	
	public void testReassignVariable() {
		assertEquals(new FalseRule(), "var = true(), var = false(), var");
	}

    public void testUnknownRule() {
		try {
			produceRules("gobbledygook()", new Rule[0]);

			fail();
		}
		catch (ADarwinException be) {
			assertEquals("No such rule, or variable: " + "\"gobbledygook\"", be.getMessage());
		}
    }

	public void testUnbalancedBrackets() {
		String expression = "(";
		try {
			produceRules("(", new Rule[0]);
			fail();
		}
		catch (ADarwinException be) {
			assertEquals("Unbalanced expression: \"" + expression + "\"", be.getMessage());
		}
	}

	public void testMultipleRules() {
		expectRule(new TrueRule());
		expectRule(new TrueRule());

		produceRules("true(), true()", new Rule[] {new TrueRule(), new TrueRule()});
	}

	public void testMultipleComplexRules() {
		expectRule(new SourceRule(new PackageRule("package-a")));
		expectRule(new SourceRule(new PackageRule("package-b")));

		produceRules("src(package(package-a)), src(package(package-b))",
			new Rule[] {
				new SourceRule(new PackageRule("package-a")),
				new SourceRule(new PackageRule("package-b"))
		});
	}

	public void testWhiteSpaceBetweenRulesIsIrrelevant() {
		AndRule expectedRule = new AndRule(new Rule[] {new TrueRule(), new TrueRule()});
		expectRule(expectedRule);
		expectRule(expectedRule);

		produceRules("and(true(), true()) \t\n, \n\n\t and(true(), true())", new Rule[] {
			expectedRule, expectedRule
		});
	}

	public void testWhiteSpaceBetweenVariableAssignmentIsIrrelevant() {
		produceRules("var = true(), and(true)", new Rule[] {
			new AndRule(new Rule[] {new TrueRule()})
		});
	}

//	public void testVariableAssignment() {
//		
//	}

//	public void testUseVariable() {
//		
//	}

	private void assertEquals(Rule expectedRule, String expression) {
		expectRule(expectedRule);

		loggerControl.replay();

		produceRules(expression, new Rule[] {expectedRule});

		loggerControl.verify();
	}

	private void produceRules(String expression, Rule[] rules) {
		int index = 0;

		RuleIterator iterator = iterator(expression);
		for (int rLoop = 0; rLoop < rules.length && iterator.hasNext(); rLoop++) {
			Rule expected = rules[index++];
			Rule actual = iterator.next();
			
			if (!expected.equals(actual)) {
				System.out.println(expected.getClass());
				System.out.println(actual.getClass());
			}
			
			assertEquals(expected, actual);

			//ruleConsumer.consume(iterator.next(), logger);
		}
	}
	
	private RuleIterator iterator(String expression) {
		Properties ruleMapping = buildMapping(
			new String[] {
				"true", "and", "parent", "constructor",
				"src", "package", "method", "uses",
				"not", "false"
			},
			new Class[] {
				TrueRule.class, AndRule.class, ParentRule.class, ConstructorRule.class,
				SourceRule.class, PackageRule.class, MethodRule.class, UsesRule.class,
				NotRule.class, FalseRule.class
			});

		return new RuleBuilder(expression, logger, ruleMapping).iterator();
	}

	private Properties buildMapping(String[] ruleNames, Class[] ruleClasses) {
		Properties properties = new Properties();

		for (int rLoop = 0; rLoop < ruleClasses.length; rLoop++) {
			properties.put(ruleNames[rLoop], ruleClasses[rLoop].getName());
		}

		return properties;
	}

	private void expectRule(Rule rule) {
//		ruleConsumer.consume(rule, logger);
//		ruleConsumerControl.setReturnValue(true);
	}
}
