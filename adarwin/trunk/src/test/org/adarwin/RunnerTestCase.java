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
import java.io.OutputStream;

import org.adarwin.Runner.Main;
import org.adarwin.Runner.UsageException;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.a.InPackageAUsesClassFromPackageB;
import org.adarwin.testmodel.a.UsesPackageAAndPackageB;

import junit.framework.TestCase;

import com.mockobjects.dynamic.OrderedMock;

public class RunnerTestCase extends TestCase {
	private static final String CLASSPATH = "target/test-classes";
	private static final String RULE = "src(package(org.adarwin.testmodel.a))";
	private static final String SECOND_RULE = "src(package(org.adarwin.testmodel.x))";
	private static final String COMPOSITE_RULE = RULE + ", " + SECOND_RULE;
	private OrderedMock mockLogger;
	private Runner runner;

	protected void setUp() throws Exception {
		super.setUp();
		mockLogger = new OrderedMock(Logger.class);
		runner = new Runner();
		runner.setLogger((Logger) mockLogger.proxy());
		runner.setBinding("rules.properties");
		runner.setClassPath(CLASSPATH);
		mockLogger.expect("log", "Evaluating rules against: " + CLASSPATH);
	}

	public void testRuleInAntFile() {
		mockLogger.expect("reset", "classes violated: " + RULE);

		runner.setRuleExpression(RULE);

		runAndExpectException();

		mockLogger.verify();
	}

	public void testRuleInFile() throws IOException {
		mockLogger.expect("reset", "classes violated: " + RULE);

		runner.setRuleFileName(createTempRuleFile(RULE));

		runAndExpectException();		

		mockLogger.verify();
	}

	public void testMultipleRulesOneRuleMatches() {
		mockLogger.expect("reset", "classes violated: " + RULE);
		mockLogger.expect("reset", "classes violated: " + SECOND_RULE);

		runner.setRuleExpression(COMPOSITE_RULE);

		runAndExpectException();

		mockLogger.verify();
	}

	public void testMissingBinding() {
		runner.setBinding("");

		runAndExpectException(Runner.BINDING + Runner.MISSING_OR_EMPTY);
	}
	
	public void testMissingClassPath() {
		runner.setClassPath("");

		runAndExpectException(Runner.CLASSPATH + Runner.MISSING_OR_EMPTY);
	}

	public void testMissingRuleExpressionAndRuleFileName() {
		runner.setRuleExpression("");
		runner.setRuleFileName("");

		runAndExpectException(Runner.RULE_EXPRESSION_AND_RULE_FILE_NAME_MISSING);
	}

	public void testPrintClasses() {
		runner.setPrint(true);

		mockLogger.expect("reset", "classes violated: " + RULE);
		mockLogger.expect("log", "  " + InPackageA.class.getName());
		mockLogger.expect("log", "  " + InPackageAUsesClassFromPackageB.class.getName());
		mockLogger.expect("log", "  " + UsesPackageAAndPackageB.class.getName());

		runner.setRuleExpression(RULE);

		runAndExpectException();		

		mockLogger.verify();
	}
	
 	public void testMainNotEnoughArgs() {
		try {
			new Runner.Main(new String[Runner.Main.MIN_ARGS - 1]);
			fail("UsageException expected");
		} catch (UsageException e) {
			assertEquals(Runner.Main.USAGE, e.getMessage());
		}
 	} 

	public void testMainTooManyArgs() {
		try {
			new Main((new String[Runner.Main.MAX_ARGS + 1]));
			fail("UsageException expected");
		} catch (UsageException e) {
			assertEquals(Runner.Main.USAGE, e.getMessage());
		}
	} 

	public void testMainMissingBinding() throws UsageException {
		try {
			new Runner.Main(new String[] {
				"-c", "target/test-classes",
				"-r", "false",
				// Repeating these so that the number of args is correct
				"-r", "false", 
			}).getRunner().run();
			fail("RuleException expected");
		} catch (RuleException e) {
			assertEquals(Runner.BINDING + Runner.MISSING_OR_EMPTY, e.getMessage());
		}
	}

	public void testMainMissingClassPath() throws UsageException {
		try {
			new Runner.Main(new String[] {
				"-b", "rules.properties",
				"-r", "false",
				// Repeating these so that the number of args is correct
				"-r", "false", 
			}).getRunner().run();
			fail("RuleException expected");
		} catch (RuleException e) {
			assertEquals(Runner.CLASSPATH + Runner.MISSING_OR_EMPTY, e.getMessage());
		}
	}

	public void testMainMissingRuleExpressionAndRuleFileName() throws UsageException {
		try {
			new Runner.Main(new String[] {
				"-b", "rules.properties",
				"-c", "target/test-classes",
				// Repeating these so that the number of args is correct
				"-c", "target/test-classes"
			}).getRunner().run();
			fail("RuleException expected");
		} catch (RuleException e) {
			assertEquals(Runner.RULE_EXPRESSION_AND_RULE_FILE_NAME_MISSING, e.getMessage());
		}
	}

	private void runAndExpectException() {
		runAndExpectException(Runner.RULE_VIOLATED);
	}

	private void runAndExpectException(String expectedMessage) {
		try {
			runner.run();
			fail("RuleException expected");
		}		
		catch (RuleException e) {
			assertEquals(expectedMessage, e.getMessage());
		}
	}

	private String createTempRuleFile(String contents) throws IOException {
		File ruleFile = File.createTempFile("test-rule", "txt");
		OutputStream outputStream = new FileOutputStream(ruleFile);

		try {
			outputStream.write(contents.getBytes());
		}
		finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
			}	
		}

		return ruleFile.getAbsolutePath();
	}
}
