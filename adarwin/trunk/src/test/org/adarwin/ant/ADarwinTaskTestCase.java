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


import org.adarwin.ADarwinException;
import org.adarwin.Logger;
import org.apache.tools.ant.BuildException;
import org.easymock.MockControl;

import junit.framework.TestCase;

// TODO: This should become an integration test and then IRunnerFactory can go
public class ADarwinTaskTestCase extends TestCase {
	private ADarwinTask aDarwinTask;

	private boolean printDetail = false;
	private boolean failFast = false;
	private boolean failOnMatch = false;
	private String binding = "rules.properties";
	private String classPath = "classPath";
	private String ruleExpression = "class(Rule)";
	private Logger logger;

	protected void setUp() throws Exception {
		logger = (Logger) MockControl.createNiceControl(Logger.class).getMock();

		aDarwinTask = new ADarwinTask(logger);
	}
	
	public void testRun() {
		replay();

		setProperties();

		aDarwinTask.execute();

		verify();
	}
	
	public void testExecuteTranslatesRuleExceptionIntoBuildException() {
		ruleExpression = "blah blah";

		replay();

		setProperties();

		try {
			aDarwinTask.execute();
			fail("BuildException expected");
		}
		catch (BuildException buildException) {
			assertEquals("No such rule, or variable: \"blah blah\"", buildException.getMessage());
		}

		verify();
	}

	private void setProperties() {
		aDarwinTask.setPrintDetail(printDetail);
		aDarwinTask.setBinding(binding);
		aDarwinTask.setClassPath(classPath);
		aDarwinTask.setFailFast(failFast);
		aDarwinTask.setFailOnMatch(failOnMatch);
		aDarwinTask.setRuleExpression(ruleExpression);
	}

	private void replay() {
	}

	private void verify() {
	}
}
