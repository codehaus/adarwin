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

import org.easymock.MockControl;

import java.io.IOException;

import junit.framework.TestCase;

public class RunnerTestCase extends TestCase {
	private static final String CLASSPATH = "target/test-classes";
	private static final String RULE = "src(package(org.adarwin.testmodel.a))";
	private static final String SECOND_RULE = "src(package(org.adarwin.testmodel.x))";
	private static final String COMPOSITE_RULE = RULE + ", " + SECOND_RULE;
	private static final String CONSTRUCTOR_RULE = 
		"constructor(.*RunnerTestCase\\$HasTwoArgConstructor(java.lang.Integer, java.lang.String))";

	private Runner runner;

	private MockControl loggerControl = MockControl.createNiceControl(Logger.class);
	private Logger logger = (Logger) loggerControl.getMock();

	private MockControl codeControl = MockControl.createNiceControl(Code.class);
	private Code code = (Code) codeControl.getMock();

	private MockControl ruleBuilderControl = MockControl.createNiceControl(RuleProducer.class);
	private RuleProducer ruleBuilder = (RuleProducer) ruleBuilderControl.getMock();

	private boolean printDetail = false;
	private String binding = "rules.properties";
	private String classPath = CLASSPATH;
	private boolean failFast = false;
	private boolean failOnMatch = true;
	private String ruleExpression = null;

	private Runner createRunner() {
		return runner = new Runner(printDetail, failOnMatch, failFast, binding, classPath,
			logger, ruleBuilder, new CodeProducer(classPath, new FileAccessor()));
	}

	public static void expectRuleViolated(Logger logger, String rule, Class[] classes) {
		logger.reset(RuleBuilder.CLASSES_VIOLATED + rule);

		for (int cLoop = 0; cLoop < classes.length; cLoop++) {
			logger.log("  " + classes[cLoop].getName());
		}
	}

//	public void testRuleExpression() {
//		ruleExpression = RULE;
//
//		logger.log("Evaluating rules against: " + CLASSPATH);
//
//		expectRuleViolated(logger, RULE, new Class[] {InPackageA.class,
//			InPackageAUsesClassFromPackageB.class, UsesPackageAAndPackageB.class});
//
//		codeFactory.create(CLASSPATH);
//		codeFactoryControl.setReturnValue(code);
//
//		code.evaluate(new SourceRule(new PackageRule("org.adarwin.testmodel.a")), null);
//		codeControl.setReturnValue(true);
//
//		replay();
//
//		createRunner();
//		
//		ruleBuilder.buildRules(RULE, runner.ruleBuilderListener);
//		ruleBuilderControl.setReturnValue(null);
//
////		assertEquals(RULE, runner.getRules()[0].toString(runner.getBindings()));
//
//		runAndExpectException(Runner.RULE_VIOLATED);
//
//		verify();
//	}

//	public void testMultipleRulesOneRuleMatches() {
//		ruleExpression = COMPOSITE_RULE;
//
//		logger.log("Evaluating rules against: " + CLASSPATH);
//
//		expectRuleViolated(logger, RULE, new Class[] {InPackageA.class,
//			InPackageAUsesClassFromPackageB.class, UsesPackageAAndPackageB.class});
//		
//		logger.reset(Runner.CLASSES_VIOLATED + SECOND_RULE);
//
//		replay();
//
//		createRunner();
//
//		runAndExpectException(Runner.RULE_VIOLATED);
//
//		verify();
//	}
//
//	public void testFailFast() {
//		failFast = true;
//		ruleExpression = RULE;
//
//		logger.log("Evaluating rules against: " + CLASSPATH);
//
//		expectRuleViolated(logger, RULE, new Class[] {InPackageA.class});
//
//		logger.log("Rule evaluation stopped early");
//
//		replay();
//
//		createRunner();
//
//		runAndExpectException(Runner.RULE_VIOLATED);
//
//		verify();
//	}

	static class HasTwoArgConstructor {
		public HasTwoArgConstructor(Integer integer, String string) {
		}
	}

//	public void testPrintDetail() {
//		ruleExpression = CONSTRUCTOR_RULE;
//		printDetail = true;
//		failFast = true;
//		
//		logger.log("Evaluating rules against: " + CLASSPATH);
//
//		expectRuleViolated(logger, CONSTRUCTOR_RULE, HasTwoArgConstructor.class, new CodeElement[] {
//			new ConstructorDeclaration(new ClassName(HasTwoArgConstructor.class.getName()),
//				new String[] {Integer.class.getName(), String.class.getName()})
//		});
//
//		logger.log("Rule evaluation stopped early");
//
//		replay();
//
//		createRunner();
//
//		runAndExpectException(Runner.RULE_VIOLATED);		
//
//		verify();
//	}
	
	public void testMainNotEnoughArgs() throws IOException {
		try {
			Runner.main(new String[Runner.MIN_ARGS - 1]);
			fail("UsageException expected");
		} catch (UsageException e) {
			assertEquals(Runner.USAGE, e.getMessage());
		}
 	} 

	public void testMainTooManyArgs() throws IOException {
		try {
			Runner.main((new String[Runner.MAX_ARGS + 1]));
			fail("UsageException expected");
		} catch (UsageException e) {
			assertEquals(Runner.USAGE, e.getMessage());
		}
	} 

	public void testMainMissingBinding() throws UsageException, IOException {
		try {
			Runner.main(new String[] {
				"-c", "target/test-classes",
				"-r", "false",
				// Repeating these so that the number of args is correct
				"-r", "false", 
			});
			fail("RuleException expected");
		} catch (ADarwinException e) {
			assertEquals(Runner.BINDING + Runner.MISSING_OR_EMPTY, e.getMessage());
		}
	}

	public void testMainMissingClassPath() throws UsageException, IOException {
		try {
			Runner.main(new String[] {
				"-b", "rules.properties",
				"-r", "false",
				// Repeating these so that the number of args is correct
				"-r", "false", 
			});
			fail("RuleException expected");
		} catch (ADarwinException e) {
			assertEquals(Runner.CLASSPATH + Runner.MISSING_OR_EMPTY, e.getMessage());
		}
	}

	public void testMainMissingRuleExpression() throws UsageException, IOException {
		try {
			Runner.main(new String[] {
				"-b", "rules.properties",
				"-c", "target/test-classes",
				// Repeating these so that the number of args is correct
				"-c", "target/test-classes"
			});
			fail("RuleException expected");
		} catch (ADarwinException e) {
			assertEquals(Runner.RULE_EXPRESSION + Runner.MISSING_OR_EMPTY, e.getMessage());
		}
	}

	private void runAndExpectException(String expectedMessage)  {
		try {
			runner.run();
			fail("ADarwinException expected");
		}		
		catch (ADarwinException e) {
			if (!expectedMessage.equals(e.getMessage())) {
				throw e;
			}
			//assertEquals(expectedMessage, e.getMessage());
		}
	}

	private void replay() {
		loggerControl.replay();
	}

	private void verify() {
		loggerControl.verify();
	}
	
	public static void expectRuleViolated(Logger logger, String rule, Class clazz,
		CodeElement[] elements) {

		expectRuleViolated(logger, rule, new Class[] {clazz});
		for (int eLoop = 0; eLoop < elements.length; ++eLoop) {
			logger.log("    " + elements[eLoop]);
		}
	}
}
