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

import org.adarwin.IRunnerFactory;
import org.adarwin.Logger;
import org.adarwin.ADarwinException;
import org.adarwin.RuleConsumer;
import org.adarwin.RunnerFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class ADarwinTask extends Task {
	private String classPath;
	private String rule;
	private String binding;
	private boolean failOnMatch;
	private boolean failFast;
	private boolean printDetail;

	private IRunnerFactory runnerFactory;
	
	private final Logger logger = new Logger() {
		private String prefix = null;

		public void reset(String prefix) {
			this.prefix = prefix;
		}

		public void log(String toLog) {
			if (prefix != null) {
				ADarwinTask.super.log(prefix);
				prefix = null;
			}
			ADarwinTask.super.log(toLog);
		}
	};

	public ADarwinTask() {
		this(new RunnerFactory());
	}

	public ADarwinTask(IRunnerFactory runnerFactory) {
		this.runnerFactory = runnerFactory;
	}

	public void setPrintDetail(boolean printDetail) {
		this.printDetail = printDetail;
	}

    public void setClassPath(String classPath) {
    	this.classPath = classPath;
    }

	public void setRuleExpression(String rule) {
		this.rule = rule;
    }

    public void setBinding(String binding) {
    	this.binding = binding;
    }
    
	public void setFailOnMatch(boolean failOnMatch) {
		this.failOnMatch = failOnMatch;
	}

	public void setFailFast(boolean failFast) {
		this.failFast = failFast;
	}

	public void execute() throws BuildException {
		try {
			runnerFactory.create(printDetail, binding, classPath, failFast,
				failOnMatch, rule, logger, RuleConsumer.NULL).run();
		} catch (ADarwinException e) {
			throw new BuildException(e.getMessage(), e.getCause());
		}
	}

	public Logger getLogger() {
		return logger;
	}
}
