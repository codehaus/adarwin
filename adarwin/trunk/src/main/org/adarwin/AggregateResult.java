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

import java.util.HashSet;
import java.util.Set;

public class AggregateResult extends Result {
	private final Set matchingClasses;

	public AggregateResult() {
		matchingClasses = new HashSet();
	}

	public void append(Result result) {
		if (result != null) {
			matchingClasses.addAll(result.getMatchingClasses());
		}
	}

	public int getCount() {
		return super.getCount() + matchingClasses.size();
	}

	public Set getMatchingClasses() {
		return matchingClasses;
	}
}
