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

import org.adarwin.BuilderException;
import org.adarwin.Grammar;
import org.adarwin.LazyCodeFactory;
import org.adarwin.Result;
import org.adarwin.RuleBuilder;
import org.adarwin.CodeFactory;
import org.adarwin.ICodeFactory;
import org.adarwin.rule.Rule;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.IOException;
import java.util.Iterator;

public class ADarwinTask extends Task{
    private String rulesFileName;
    private String classPath;
    private String rule;
	private boolean print;
	private boolean failOnMatch = true;

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

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getRulesFileName() {
        return rulesFileName;
    }

    public void setRulesFileName(String rulesFileName) {
        this.rulesFileName = rulesFileName;
    }

	public boolean isFailOnMatch() {
		return failOnMatch;
	}

	public void setFailOnMatch(boolean failOnMatch) {
		this.failOnMatch = failOnMatch;
	}

	public void execute() throws BuildException {
        log("aDarwin checking classpath: " + getClassPath() + ", against the rule: " + getRule());

        try {
            Rule rule = new RuleBuilder(new Grammar(getRulesFileName())).buildRule(getRule());
			Result result = createCodeFactory().create(getClassPath()).evaluate(rule);
			if (getPrint()) {
				for (Iterator iterator = result.getMatchingClasses().iterator(); iterator.hasNext();) {
					log((String) iterator.next());
				}
			}
			log("aDarwin evaluate: " + result.getCount());

			if (isFailOnMatch() && result.getCount() > 0) {
				throw new BuildException("aDarwin Rule Violated");
			}
        } catch (IOException e) {
            throw new BuildException("Unable to find classpath(?): " + getClassPath(), e);
        } catch (BuilderException e) {
			throw new BuildException(e);
        } catch (ClassNotFoundException e) {
            throw new BuildException("Unable to find classpath(?): " + getClassPath(), e);
        }
    }

	private ICodeFactory createCodeFactory() {
		return new LazyCodeFactory(new CodeFactory());
	}
}
