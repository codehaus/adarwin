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

public class RunnerFactory implements IRunnerFactory {
	public IRunner create(boolean printDetail, String binding, String classPath, boolean failFast,
		boolean failOnMatch, String ruleExpression, Logger logger, RuleConsumer ruleBuilderListener)
			throws ADarwinException {

		IFileAccessor fileAccessor = new FileAccessor();

		RuleProducer ruleBuilder = new RuleBuilder(new RuleClassBindings(binding, fileAccessor));

		return new Runner(printDetail, failOnMatch, failFast, binding, classPath, ruleExpression,
			logger, ruleBuilderListener, ruleBuilder, new CodeProducer(classPath));
	}
}
