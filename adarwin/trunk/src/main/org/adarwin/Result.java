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

public class Result {
	private String className;
	private boolean result;

	protected Result() {
	}

	public Result(boolean result, String fullyQualifiedClassName) {
		this.result = result;
		this.className = fullyQualifiedClassName;
	}

	public int getCount() {
		return (result ? 1 : 0);
	}

	public Set getMatchingClasses() {
		Set result = new HashSet();

		if (getResult()) {
			result.add(className);
		}

		return result;
	}

	public final boolean getResult() {
		return result;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
