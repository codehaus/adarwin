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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.adarwin.rule.Rule;

public class Runner {
	public static final String BINDING = "binding";
	public static final String CLASSPATH = "classPath";
	public static final String RULE_VIOLATED = "aDarwin rule violated";
	public static final String MISSING_OR_EMPTY = " parameter missing or empty";
	public static final String RULE_EXPRESSION_AND_RULE_FILE_NAME_MISSING =
		"both ruleExpression and ruleFileName parameters are missing or empty";

	private Logger logger;
	private String binding;
	private String classPath;
	private String ruleExpression;
	private String ruleFileName;
	private boolean failOnMatch = true;
	private boolean print;

	public void setFailOnMatch(boolean failOnMatch) {
		this.failOnMatch = failOnMatch;
	}

	public boolean isFailOnMatch() {
		return failOnMatch;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public boolean getPrint() {
		return print;
	}

	public void setRuleExpression(String rule) {
		this.ruleExpression = rule;
	}

	public void setRuleFileName(String ruleFileName) {
		this.ruleFileName = ruleFileName;
	}

	public String getBinding() {
		return binding;
	}

	public String getClassPath() {
		return classPath;
	}

	public Logger getLogger() {
		return logger;
	}

	public String getRuleExpression() {
		return ruleExpression;
	}

	public String getRuleFileName() {
		return ruleFileName;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public void run() throws RuleException {
		verifyParameter(getBinding(), Runner.BINDING);
		if (isParameterNullOrEmpty(getClassPath())) {
			throw new RuleException(Runner.CLASSPATH + Runner.MISSING_OR_EMPTY);
		}

		if (isParameterNullOrEmpty(getRuleExpression()) &&
			isParameterNullOrEmpty(getRuleFileName())) {
			throw new RuleException(RULE_EXPRESSION_AND_RULE_FILE_NAME_MISSING);		
		}
		
		try {
			RuleClassBindings ruleClassBindings = new RuleClassBindings(getBinding());
			Rule[] rules = new RuleBuilder(ruleClassBindings).buildRules(acquireRuleExpression());
            
			logger.log("Evaluating rules against: " + getClassPath());

			boolean throwException = false;
            
			for (int rLoop = 0; rLoop < rules.length; rLoop++) {
				throwException = throwException | executeRule(ruleClassBindings, rules[rLoop]);	
			}

			if (throwException) {
				throw new RuleException(Runner.RULE_VIOLATED);
			}
		} catch (IOException e) {
			throw new RuleException("Unable to find classpath(?): " + getClassPath(), e);
		} catch (BuilderException e) {
			e.printStackTrace();
			throw new RuleException(e);
		} catch (ClassNotFoundException e) {
			throw new RuleException("Unable to find classpath(?): " + getClassPath(), e);
		}
	}

	private void verifyParameter(String value, String name) throws RuleException {
		if (isParameterNullOrEmpty(value)) {
			throw new RuleException(name + Runner.MISSING_OR_EMPTY);
		}
	}

	private boolean executeRule(RuleClassBindings ruleClassBindings, Rule rule) throws IOException, FileNotFoundException {
		final String ruleExpression = rule.toString(ruleClassBindings);

		Code code = createCodeFactory().create(getClassPath());

		logger.reset("classes violated: " + ruleExpression);
		LoggingOperator operator = new LoggingOperator(ruleExpression);
		code.evaluate(rule, operator);

		return isFailOnMatch() && operator.isMatched();
	}

	private ICodeFactory createCodeFactory() {
		return new LazyCodeFactory(new CodeFactory());
	}

	private boolean isParameterNullOrEmpty(String value) {
		return value == null || value.length() == 0;
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
			StringBuffer buffer = new StringBuffer();
			
			String currentLine = reader.readLine();
			
			while (currentLine != null) {
				buffer.append(currentLine);
				currentLine = reader.readLine();
			}
			
			return buffer.toString();
		}
		finally {
			reader.close();
		}
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	private final class LoggingOperator implements RuleListener {
		private boolean loggedHeader = false;
		private final String ruleExpression;
		private boolean matched;

		private LoggingOperator(String ruleExpression) {
			this.ruleExpression = ruleExpression;
		}

		public boolean isMatched() {
			return matched;
		}

		public void matches(ClassSummary classSummary) {
			matched = true;
//			if (!loggedHeader) {
//				loggedHeader = true;
//				logger.log("classes violated: " + ruleExpression);
//			}

			classSummary.log(logger, getPrint());
		}

		public void matches(CodeElement codeElement) {
		}
	}

	public static class Main {
		public static final int MIN_ARGS = 6;
		public static final int MAX_ARGS = 7;

		public static final String USAGE = "Usage: " + Runner.class.getName() +
			" -b <binding-file> -c <class-path> {-r <rule> | -f <rule-file>} -p";
			
		private Runner runner;

		public Main(String[] args) throws UsageException {
			runner = new Runner();
			if (args.length < MIN_ARGS || args.length > MAX_ARGS) {
				usage();
				return;
			}
			for (int aLoop = 0; aLoop < args.length; ++aLoop) {
				String currentArg = args[aLoop];
				switch (currentArg.charAt(1)) {
					case 'b':
						checkMoreArgs(aLoop, args.length, currentArg);
						runner.setBinding(args[++aLoop]);
						break;
					case 'c':
						checkMoreArgs(aLoop, args.length, currentArg);
						runner.setClassPath(args[++aLoop]);
						break;
					case 'r':
						checkMoreArgs(aLoop, args.length, currentArg);
						runner.setRuleExpression(args[++aLoop]);
						break;
					case 'f':
						checkMoreArgs(aLoop, args.length, currentArg);
						runner.setRuleFileName(args[++aLoop]);
						break;
					case 'p':
						runner.setPrint(true);
						break;
					default:
				}
			}
		}

		public Runner getRunner() {
			return runner;
		}

		private void usage() throws UsageException {
			throw new UsageException(USAGE);
		}

		private void checkMoreArgs(int currentIndex, int numArgs, String argument)
			throws UsageException {
				
			if (numArgs <= currentIndex) {
				throw new UsageException("Missing parameter after: " + argument);
			}
		}
	}
	
	public static class UsageException extends Exception {
		public UsageException(String message) {
			super(message);
		}
	}
	
	public static void main(String[] args) throws Exception {
		try {
			new Main(args).getRunner().run();
		} catch (UsageException e) {
			System.out.println(e.getMessage());
		}
	}
}
