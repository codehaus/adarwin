/*****************************************************************************
 * Copyright (C) aDarwin Organisation. All rights reserved.                  *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Idea and Original Code by Stacy Curl                                      *
 *****************************************************************************/

package org.adarwin.ant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import junit.framework.TestCase;

import org.adarwin.Logger;
import org.adarwin.Runner;
import org.adarwin.testmodel.a.InPackageA;
import org.adarwin.testmodel.a.InPackageAUsesClassFromPackageB;
import org.adarwin.testmodel.a.UsesPackageAAndPackageB;
import org.apache.tools.ant.BuildException;

import com.mockobjects.dynamic.OrderedMock;

public class ADarwinTaskTestCase extends TestCase {
	private static final String CLASSPATH = "target/test-classes";
	private static final String RULE = "package(org.adarwin.testmodel.a)";
	private static final String SECOND_RULE = "package(org.adarwin.testmodel.x)";
	private static final String COMPOSITE_RULE = RULE + ", " + SECOND_RULE;
	private OrderedMock mockLogger;
	private ADarwinTask task;

	protected void setUp() throws Exception {
		super.setUp();
		mockLogger = new OrderedMock(Logger.class);
		task = new ADarwinTask();
		task.setLogger(((Logger) mockLogger.proxy()));
		task.setBinding("rules.properties");
		task.setClassPath(CLASSPATH);
		task.setFailOnMatch(true);
		mockLogger.expect("log", "Evaluating rules against: " + CLASSPATH);
	}

	public void testRuleInAntFile() {
		mockLogger.expect("log", "3 classes violated: " + RULE);

		task.setRuleExpression(RULE);
		task.setFailOnMatch(true);
		
		executeTask();

		mockLogger.verify();
	}
	
	public void testRuleInFile() throws IOException {
		task.setPrint(true);
		mockLogger.expect("log", "3 classes violated: " + RULE);
		mockLogger.expect("log", "  " + UsesPackageAAndPackageB.class.getName());
		mockLogger.expect("log", "  " + InPackageA.class.getName());
		mockLogger.expect("log", "  " + InPackageAUsesClassFromPackageB.class.getName());
		
		task.setRuleFileName(createTempRuleFile(RULE));
		
		executeTask();		

		mockLogger.verify();
	}

	public void testMultipleRulesOneRuleMatches() {
		mockLogger.expect("log", "3 classes violated: " + RULE);
				
		task.setRuleExpression(COMPOSITE_RULE);
		
		executeTask();
		
		mockLogger.verify();
	}
	
	public void testMissingBinding() {
		task.setBinding("");
		
		try {
			task.execute();
			fail("BuildException expected");
		}
		catch (BuildException be) {
			assertEquals(Runner.BINDING + Runner.MISSING_OR_EMPTY, be.getMessage());
		}
	}
	
	public void testMissingClassPath() {
		task.setClassPath("");
		
		try {
			task.execute();
			fail("BuildException expected");
		}
		catch (BuildException be) {
			assertEquals(Runner.CLASSPATH + Runner.MISSING_OR_EMPTY, be.getMessage());
		}
	}

	public void testMissingRuleExpressionAndRuleFileName() {
		task.setRuleExpression("");
		task.setRuleFileName("");
		
		try {
			task.execute();
			fail("BuildException expected");
		}
		catch (BuildException be) {
			assertEquals("both ruleExpression and ruleFileName parameters are missing or empty", be.getMessage());
		}
	}

	private void executeTask() {
		try {
			task.execute();
			fail("BuildException expected");
		}		
		catch (BuildException be) {
			assertEquals(Runner.RULE_VIOLATED, be.getMessage());
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
