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


import org.adarwin.IRunner;
import org.adarwin.IRunnerFactory;
import org.adarwin.ADarwinException;
import org.adarwin.Logger;
import org.apache.tools.ant.BuildException;
import org.easymock.MockControl;

import junit.framework.TestCase;

// TODO: This should become an integration test and then IRunnerFactory can go
public class ADarwinTaskTestCase extends TestCase {
	private ADarwinTask aDarwinTask;
	private MockControl runnerFactoryControl;
	private IRunnerFactory runnerFactory;
	private MockControl runnerControl;
	private IRunner runner;

	private boolean printDetail = false;
	private boolean failFast = false;
	private boolean failOnMatch = false;
	private String binding = "binding";
	private String classPath = "classPath";
	private String ruleExpression = "ruleExpression";
	private Logger logger;

	protected void setUp() throws Exception {
		runnerFactoryControl = MockControl.createControl(IRunnerFactory.class);
		runnerFactory = (IRunnerFactory) runnerFactoryControl.getMock();

		runnerControl = MockControl.createControl(IRunner.class);
		runner = (IRunner) runnerControl.getMock();

		aDarwinTask = new ADarwinTask(runnerFactory);
		
		logger = aDarwinTask.getLogger();
	}
	
	public void testRun() throws ADarwinException {
		expectFactory();

		runner.run();

		replay();

		setProperties();

		aDarwinTask.execute();

		verify();
	}
	
	public void testExecuteTranslatesRuleExceptionIntoBuildException() throws ADarwinException {
		expectFactory();

		runner.run();
		String message = "message";
		Throwable throwable = new Throwable();
		runnerControl.setThrowable(new ADarwinException(message, throwable));

		replay();

		setProperties();

		try {
			aDarwinTask.execute();
			fail("BuildException expected");
		}
		catch (BuildException buildException) {
			assertEquals(message, buildException.getMessage());
			assertEquals(throwable, buildException.getCause());
		}

		verify();
	}

	private void expectFactory() throws ADarwinException {
		runnerFactory.create(printDetail, binding, classPath, failFast, failOnMatch,
			ruleExpression, logger);
		runnerFactoryControl.setReturnValue(runner);
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
		runnerFactoryControl.replay();
		runnerControl.replay();
	}

	private void verify() {
		runnerFactoryControl.verify();
		runnerControl.verify();
	}
}
