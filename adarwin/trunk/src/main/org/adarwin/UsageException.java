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

public class UsageException extends Exception {
	public static final String USAGE = "Usage: " + Runner.class.getName() +
	" -b <binding-file> -c <class-path> {-r <rule> | -f <rule-file>} -p";

	public UsageException() {
		this(USAGE);
	}
	
	public UsageException(String message) {
		super(message);
	}
}