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

import java.io.FileNotFoundException;

public class ClassDirectoryIterator implements CodeIterator {
	private final IFileIterator fileIterator;
	private final IFileAccessor fileAccessor;
	private ClassSummary next;

	public ClassDirectoryIterator(IFileAccessor fileAccessor, IFileIterator fileIterator) {
		this.fileAccessor = fileAccessor;
		this.fileIterator = fileIterator;

		next = prefetch();
	}

	public boolean hasNext() {
		return next != null;
	}

	public ClassSummary next() {
		ClassSummary toReturn = next;

		if (next != null) {
			next = prefetch();
		}

		return toReturn;
	}

	private ClassSummary prefetch() {
		if (!fileIterator.hasNext()) {
			return null;
		}

		try {
			String fileName;

			for(fileName = fileIterator.next(); !CodeProducer.isClass(fileName);) {
				if (!fileIterator.hasNext()) {
					return null;
				}

				fileName = fileIterator.next();
			}

			return RuleClassVisitor.visit(fileAccessor.openFile(fileName));
		}
		catch (FileNotFoundException e) {
			throw new ADarwinException(e);
		}
	}
}
