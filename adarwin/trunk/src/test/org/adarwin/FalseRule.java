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

import java.util.Collections;


public class FalseRule implements Rule {
	public ClassSummary inspect(ClassSummary classSummary) {
		return new ClassSummary("", Collections.EMPTY_SET); 
	}

	public int hashCode() {
		return getClass().hashCode();
	}

	public boolean equals(Object object) {
		return object != null && getClass().equals(object.getClass());
	}
}
