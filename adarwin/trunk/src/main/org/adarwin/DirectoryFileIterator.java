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

import java.util.LinkedList;
import java.util.List;

public class DirectoryFileIterator implements IFileIterator {
	private final IFileAccessor fileAccessor;
	private List fileStack = new LinkedList();
	private String nextFile;

	public DirectoryFileIterator(String directory, IFileAccessor fileAccessor) {
		this.fileAccessor = fileAccessor;

		if (fileAccessor.isDirectory(directory)) {
			addAll(directory);
		}
		else {
			fileStack.add(directory);
		}
		
		nextFile = prefetch();
	}

	public boolean hasNext() {
		return nextFile != null;
	}

	public String next() {
		String next = nextFile;

		if (nextFile != null) {
			nextFile = prefetch();
		}

		return next;
	}

	private String prefetch() {
		String fileName = null;

		while (fileName == null && !fileStack.isEmpty()) {
			fileName = (String) fileStack.remove(0);

			if (!fileAccessor.isDirectory(fileName)) {
				return fileName;
			}
			else {
				addAll(fileName);

				fileName = null;
			}
		}

		return fileName;
	}

	private void addAll(String directory) {
		String[] files = fileAccessor.listFiles(directory);

		for (int fLoop = 0; fLoop < files.length; fLoop++) {
			fileStack.add(files[fLoop]);
		}
	}
}
