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

import org.adarwin.rule.Filter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ClassSummary {
	private final ClassName className;
	private final Set dependancies;

	public static ClassSummary or(ClassSummary[] summaries) {
		Set union = new HashSet();

		for (int sLoop = 0; sLoop < summaries.length; sLoop++) {
			union.addAll(summaries[sLoop].dependancies);
		}

		return new ClassSummary(summaries[0].getClassName(), union);
	}

	public static ClassSummary and(ClassSummary[] summaries) {
		Set intersection = new HashSet(summaries[0].dependancies);

		for (int sLoop = 0; sLoop < summaries.length; sLoop++) {
			Set set = summaries[sLoop].dependancies;

			if (set.isEmpty()) {
				return new ClassSummary(summaries[0].getClassName(), new HashSet());
			}

			intersection.addAll(set);
		}

		return new ClassSummary(summaries[0].getClassName(), intersection);
	}

	public ClassSummary(ClassName className, Set dependancies) {
    	this.className = className;
    	this.dependancies = dependancies;
    }

    public ClassName getClassName() {
    	return className;
    }

	public ClassSummary filter(Filter filter) {
		final Set filtered = new HashSet();

		for (Iterator iterator = dependancies.iterator(); iterator.hasNext();) {
			CodeElement codeElement = (CodeElement) iterator.next();

			if (filter.matches(codeElement)) {
				filtered.add(codeElement);
			}
		}

		return new ClassSummary(getClassName(), filtered);
	}

	public boolean contains(CodeElement element) {
		return dependancies.contains(element);
	}

	public String toString() {
		return "ClassSummary(" + className + ", " + dependancies + ")";
	}

	public void log(Logger logger, boolean printDetail) {
		if (!isEmpty()) {
			logger.log("  " + getClassName().getFullClassName());
			
			if (printDetail) {
				for(Iterator iterator = dependancies.iterator(); iterator.hasNext();) {
					logger.log("    " + iterator.next());
				}
			}
		}
	}

	public boolean isEmpty() {
		return dependancies.isEmpty();
	}

	public ClassSummary negate(ClassSummary original) {
		return isEmpty() ? original : empty();
	}

	public ClassSummary empty() {
		return new ClassSummary(getClassName(), Collections.EMPTY_SET);
	}
}
