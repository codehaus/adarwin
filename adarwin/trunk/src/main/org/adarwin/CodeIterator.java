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

public interface CodeIterator {
	CodeIterator NULL = new CodeIterator() {
		public boolean hasNext() {
			return false;
		}
		public ClassSummary next() {
			return null;
		}
	};

	public boolean hasNext();

	public ClassSummary next();
}
