/*****************************************************************************
 * Copyright (C) aDarwin Organisation. All rights reserved.                  *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Idea and Original Code by Stacy Curl                                      *
 *****************************************************************************/

package org.adarwin.rule;

import java.util.Iterator;
import java.util.regex.Pattern;

import org.adarwin.ClassSummary;
import org.adarwin.CodeElement;
import org.adarwin.Grammar;
import org.adarwin.Util;

public class PackageRule implements Rule {
	private String pattern;

	public static PackageRule create(Class clazz) {
		String packageName = Util.packageName(clazz);
		return new PackageRule(packageName.replaceAll("\\.", "\\."));
	}

	public PackageRule(String pattern) {
		this.pattern = pattern;
	}

	public boolean inspect(ClassSummary classSummary) {
		for (Iterator iterator = classSummary.getDependancies().iterator(); iterator.hasNext();) {
			CodeElement codeElement = (CodeElement) iterator.next();

			if (Pattern.matches(pattern, codeElement.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	public String getExpression(Grammar grammar) {
		return grammar.getRule(getClass()) + '(' + pattern + ')';
	}
}
