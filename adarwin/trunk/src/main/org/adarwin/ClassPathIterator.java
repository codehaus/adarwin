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

import java.io.File;
import java.util.StringTokenizer;

public class ClassPathIterator implements CodeIterator {
	private final StringTokenizer tokenizer;
	private final IFileAccessor fileAccessor;
	private CodeIterator iterator;

	public ClassPathIterator(String classPath, IFileAccessor fileAccessor) {
		this.fileAccessor = fileAccessor;
		this.tokenizer = new StringTokenizer(classPath, File.pathSeparator);
	}

	public boolean hasNext() {
		return tokenizer.hasMoreTokens() || (iterator != null && iterator.hasNext());
	}

	public ClassSummary next() {
		if ((iterator == null || !iterator.hasNext()) && tokenizer.hasMoreTokens()) {
			iterator = new CodeProducer(tokenizer.nextToken(), fileAccessor).iterator();
		}

		if (iterator != null && iterator.hasNext()) {
			return iterator.next();
		}

		return null;
	}
}
