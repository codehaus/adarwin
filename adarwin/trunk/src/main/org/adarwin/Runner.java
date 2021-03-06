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

import org.adarwin.rule.Rule;

import java.io.IOException;
import java.util.Properties;

public class Runner {
	public static final String EVALUATING_RULES_AGAINST = "Evaluating rules against: ";
	public static final String BINDING = "binding";
	public static final String CLASSPATH = "classPath";
	public static final String RULE_EXPRESSION = "ruleExpression";
	public static final String RULE_VIOLATED = "aDarwin rule violated";
	public static final String MISSING_OR_EMPTY = " parameter missing or empty";
	public static final String RULE_EVALUATION_STOPPED_EARLY = "Rule evaluation stopped early";

	private final boolean printDetail;
	private final boolean failOnMatch;
	private final boolean failFast;
	private final String classPath;
	private final Logger logger;

	private final RuleProducer ruleProducer;
	private final CodeProducer codeProducer;

	public static void run(boolean printDetail, String binding, String classPath,
		boolean failFast, boolean failOnMatch, String rule, Logger logger) {

		IFileAccessor fileAccessor = new FileAccessor();

		new Runner(printDetail, failOnMatch, failFast, binding, classPath, logger,
			new RuleBuilder(rule, logger, getProperties(binding, fileAccessor)),
			new CodeProducer(classPath, fileAccessor)).run();
	}

	public Runner(boolean printDetail, boolean failOnMatch, boolean failFast, String binding,
		String classPath, Logger logger, RuleProducer ruleProducer, CodeProducer codeProducer) {

		this.printDetail = printDetail;
		this.failOnMatch = failOnMatch;
		this.failFast = failFast;
		this.classPath = classPath;
		this.logger = logger;

		this.ruleProducer = ruleProducer;
		this.codeProducer = codeProducer;
	}

	public void run() {
		logger.log(EVALUATING_RULES_AGAINST + classPath);

		boolean matches = false;

		rules: for(RuleIterator ruleIterator = ruleProducer.iterator(); ruleIterator.hasNext();) {
			Rule rule = ruleIterator.next();

			for(CodeIterator codeIterator = codeProducer.iterator(); codeIterator.hasNext();) {
				matches = matches | rule.inspect(codeIterator.next()).log(logger, printDetail);

				if (matches && failFast) {
					logger.log(RULE_EVALUATION_STOPPED_EARLY);
					break rules;
				}
			}
		}

		if (matches && failOnMatch) {
			throw new ADarwinException(Runner.RULE_VIOLATED);
		}
	}

	private static void verifyParameter(String value, String name) {
		if (isParameterNullOrEmpty(value)) {
			throw new ADarwinException(name + Runner.MISSING_OR_EMPTY);
		}
	}

	public static boolean isParameterNullOrEmpty(String value) {
		return value == null || value.length() == 0;
	}

	public static final int MIN_ARGS = 6;
	public static final int MAX_ARGS = 7;
	public static final String USAGE = "Usage: " + Runner.class.getName() +
		" -b <binding-file> -c <class-path> {-r <rule> | -f <rule-file>} -p";

	public static void main(String[] args) throws UsageException, IOException {
		boolean printDetail = false;
		boolean failOnMatch = false;
		boolean failFast = false;
		String binding = null;
		String classPath = null;
		String ruleExpression = null;

		if (args.length < MIN_ARGS || args.length > MAX_ARGS) {
			throw new UsageException(USAGE);
		}

		IFileAccessor fileAccessor = new FileAccessor();

		for (int aLoop = 0; aLoop < args.length; ++aLoop) {
			String currentArg = args[aLoop];
			switch (currentArg.charAt(1)) {
				case 'b':
					checkMoreArgs(aLoop, args.length, currentArg);
					binding = args[++aLoop];
					break;
				case 'c':
					checkMoreArgs(aLoop, args.length, currentArg);
					classPath = args[++aLoop];
					break;
				case 'r':
					checkMoreArgs(aLoop, args.length, currentArg);
					ruleExpression = args[++aLoop];
					break;
				case 'f':
					checkMoreArgs(aLoop, args.length, currentArg);
					ruleExpression = fileAccessor.readFile(args[++aLoop]);
					break;
				case 'p':
					printDetail = true;
					break;
				default:
			}
		}

		verifyParameter(binding, Runner.BINDING);
		verifyParameter(classPath, Runner.CLASSPATH);
		verifyParameter(ruleExpression, RULE_EXPRESSION);

		Logger logger = new Logger() {
			private String prefix;
			public void reset(String prefix) {
				this.prefix = prefix;
			}
			public void log(String toLog) {
				if (prefix != null) {
					System.out.println(prefix);
					prefix = null;
				}
				System.out.println(toLog);
			}
		};
		
		run(printDetail, binding, classPath, failFast, failOnMatch, ruleExpression, logger);
	}

    private static Properties getProperties(String propertiesFileName, IFileAccessor fileAccessor) {
        try {
			Properties properties = new Properties();
			properties.load(fileAccessor.openFile(propertiesFileName));
			return properties;
		} catch (IOException e) {
			throw new ADarwinException("Unable to loading bindings: " + propertiesFileName, e);
		}
	}

	private static void checkMoreArgs(int currentIndex, int numArgs, String argument)
		throws UsageException {

		if (numArgs <= currentIndex) {
			throw new UsageException("Missing parameter after: " + argument);
		}
	}
}
