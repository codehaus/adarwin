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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.adarwin.BuilderException;
import org.adarwin.CodeFactory;
import org.adarwin.Grammar;
import org.adarwin.ICodeFactory;
import org.adarwin.LazyCodeFactory;
import org.adarwin.Result;
import org.adarwin.RuleBuilder;
import org.adarwin.rule.Rule;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class ADarwinTask extends Task {
	public static interface ILogger {
		public void log(String toLog);
	}
	
    private String binding;
    private String classPath;
    private String ruleExpression;
	private boolean print;
	private boolean failOnMatch = true;
	private ILogger logger;
	private String ruleFileName;
	
	public ADarwinTask() {
		setLogger(new ILogger() {
			public void log(String toLog) {
				ADarwinTask.super.log(toLog);
			}
		});
	}

	public boolean getPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getRuleExpression() {
    	return ruleExpression;
    }

	public void setRuleExpression(String rule) {
        this.ruleExpression = rule;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }
    
	public void setRuleFileName(String ruleFileName) {
		this.ruleFileName = ruleFileName;
	}
	
	public String getRuleFileName() {
		return ruleFileName;
	}

    public void setLogger(ILogger logger) {
    	this.logger = logger;
    }

	public boolean isFailOnMatch() {
		return failOnMatch;
	}

	public void setFailOnMatch(boolean failOnMatch) {
		this.failOnMatch = failOnMatch;
	}

	public void execute() throws BuildException {
        try {
        	Grammar grammar = new Grammar(getBinding());
            Rule[] rules = new RuleBuilder(grammar).buildRules(acquireRuleExpression());
            
			logger.log("aDarwin checking classpath: " + getClassPath());

			boolean throwException = false;
            
            for (int rLoop = 0; rLoop < rules.length; rLoop++) {
				throwException = throwException | executeRule(grammar, rules[rLoop]);	
			}
			
			if (throwException) {
				throw new BuildException("Rule violated");
			}
			
        } catch (IOException e) {
            throw new BuildException("Unable to find classpath(?): " + getClassPath(), e);
        } catch (BuilderException e) {
			throw new BuildException(e);
        } catch (ClassNotFoundException e) {
            throw new BuildException("Unable to find classpath(?): " + getClassPath(), e);
        }
    }

	private boolean executeRule(Grammar grammar, Rule rule) throws IOException, FileNotFoundException {
		String ruleExpression = rule.getExpression(grammar);
		
		Result result = createCodeFactory().create(getClassPath()).evaluate(rule);
		logger.log(result.getCount() + " classes matched: " + ruleExpression);
		if (getPrint()) {
			for (Iterator iterator = result.getMatchingClasses().iterator(); iterator.hasNext();) {
				logger.log("  " + (String) iterator.next());
			}
		}

		return isFailOnMatch() && result.getCount() > 0;
	}

	private ICodeFactory createCodeFactory() {
		return new LazyCodeFactory(new CodeFactory());
	}

	private String acquireRuleExpression() throws IOException {
		if (getRuleExpression() != null) {
			return getRuleExpression();
		}
		else {
			return readRuleExpressionFromFile();    		
		}
	}

	private String readRuleExpressionFromFile() throws FileNotFoundException, IOException {
		File ruleFile = new File(getRuleFileName());
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(ruleFile)));

		try {
			return reader.readLine();
		}
		finally {
			reader.close();
		}
	}
}
