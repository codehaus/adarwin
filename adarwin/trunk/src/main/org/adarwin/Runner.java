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

public class Runner implements IRunner {
	public static final String BINDING = "binding";
	public static final String CLASSPATH = "classPath";
	public static final String RULE_EXPRESSION = "ruleExpression";
	public static final String RULE_VIOLATED = "aDarwin rule violated";
	public static final String MISSING_OR_EMPTY = " parameter missing or empty";
	public static final String CLASSES_VIOLATED = "classes violated: ";
	public static final String RULE_EVALUATION_STOPPED_EARLY = "Rule evaluation stopped early";

	private final boolean printDetail;
	private final boolean failOnMatch;
	private final boolean failFast;
	private final String classPath;
	private final Logger logger;
	private String ruleExpression;
	private RuleProducer builder;
	private final CodeProducer codeProducer;

	public Runner(boolean printDetail, boolean failOnMatch, boolean failFast, String binding,
		String classPath, String ruleExpression, Logger logger,
		RuleConsumer ruleBuilderListener, RuleProducer ruleBuilder, CodeProducer codeProducer) {

		this.ruleExpression = ruleExpression;

		this.printDetail = printDetail;
		this.failOnMatch = failOnMatch;
		this.failFast = failFast;
		this.classPath = classPath;
		this.logger = logger;

		this.builder = ruleBuilder;
		this.codeProducer = codeProducer;
	}

	public final RuleConsumer ruleConsumer = new RuleConsumer() {
		public boolean consume(final Rule rule, final RuleClassBindings ruleClassBindings)
			throws ADarwinException {

			logger.reset(CLASSES_VIOLATED + rule.toString(ruleClassBindings));

			class RuleListenerImpl implements RuleListener {
				private boolean matched;

				public boolean isMatched() {
					return matched;
				}

				public boolean matchesEvent(ClassSummary classSummary, Rule rule, Code code) {
					matched = true;
					classSummary.log(logger, printDetail);

					return classSummary.isEmpty() || !failFast;
				}
			}

			final RuleListenerImpl ruleListener = new RuleListenerImpl();

			codeProducer.produce(new CodeConsumer() {
				public void consume(Code code) throws ADarwinException {
					if (!code.evaluate(rule, ruleListener)) {
						logger.log(RULE_EVALUATION_STOPPED_EARLY);
					}
				}
			});

			return ruleListener.isMatched();
		}
	};

	public void run() throws ADarwinException {
		logger.log("Evaluating rules against: " + classPath);

		class Boolean {
			boolean matches = false;
		}

		final Boolean matches = new Boolean();

		builder.produce(ruleExpression, new RuleConsumer() {
			public boolean consume(Rule rule, RuleClassBindings ruleClassBindings) throws ADarwinException {
				matches.matches = matches.matches | ruleConsumer.consume(rule, ruleClassBindings);

				return true;
			}
		});

		if (matches.matches && failOnMatch) {
			throw new ADarwinException(Runner.RULE_VIOLATED);
		}
	}

	public static void verifyParameter(String value, String name) throws ADarwinException {
		if (isParameterNullOrEmpty(value)) {
			throw new ADarwinException(name + Runner.MISSING_OR_EMPTY);
		}
	}

	public static boolean isParameterNullOrEmpty(String value) {
		return value == null || value.length() == 0;
	}


	public static final int MIN_ARGS = 6;
	public static final int MAX_ARGS = 7;

	public static void main(String[] args) throws ADarwinException, UsageException, IOException{
		boolean printDetail = false;
		boolean failOnMatch = false;
		boolean failFast = false;
		String binding = null;
		String classPath = null;
		String ruleExpression = null;

		if (args.length < MIN_ARGS || args.length > MAX_ARGS) {
			throw new UsageException();
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

		RuleProducer ruleBuilder = new RuleBuilder(
			new RuleClassBindings(binding, new FileAccessor()));

		new Runner(printDetail, failOnMatch, failFast, binding, classPath,
			ruleExpression, logger, RuleConsumer.NULL, ruleBuilder,
			new CodeProducer(classPath)).run();
	}

	private static void checkMoreArgs(int currentIndex, int numArgs, String argument)
		throws UsageException {
	
		if (numArgs <= currentIndex) {
			throw new UsageException("Missing parameter after: " + argument);
		}
	}
}
