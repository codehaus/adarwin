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

import org.adarwin.ClassSummary;

public interface Rule {
	Rule NULL = new Rule() {
		public ClassSummary inspect(ClassSummary classSummary) {
			return classSummary.empty();
		}
	};

	ClassSummary inspect(ClassSummary classSummary);
}
