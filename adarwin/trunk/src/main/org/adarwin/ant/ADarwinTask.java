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

import org.adarwin.Logger;
import org.adarwin.RuleException;
import org.adarwin.Runner;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class ADarwinTask extends Task {
	private Runner runner;
	
	public ADarwinTask() {
		this(new Runner());
	}

	public ADarwinTask(Runner runner) {
		this.runner = runner;
		runner.setLogger(new Logger() {
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
		});
	}

	public void setPrint(boolean print) {
		runner.setPrint(print);
	}

    public void setClassPath(String classPath) {
    	runner.setClassPath(classPath);
    }

	public void setRuleExpression(String rule) {
		runner.setRuleExpression(rule);
    }

    public void setBinding(String binding) {
    	runner.setBinding(binding);
    }
    
	public void setRuleFileName(String ruleFileName) {
		runner.setRuleFileName(ruleFileName);
	}
	
	public void setFailOnMatch(boolean failOnMatch) {
		runner.setFailOnMatch(failOnMatch);
	}

	public void setLogger(Logger logger) {
		runner.setLogger(logger);
	}

	public void execute() throws BuildException {
		try {
			runner.run();
		} catch (RuleException e) {
			throw new BuildException(e.getMessage(), e.getCause());
		}
	}
}
