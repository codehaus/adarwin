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

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarIterator implements CodeIterator {
	private final JarFile jarFile;
	private final Enumeration entries;

	public JarIterator(String jar, IFileAccessor fileAccessor) {
		try {
			this.jarFile = new JarFile(jar);
			this.entries = jarFile.entries();
		} catch (IOException e) {
			throw new ADarwinException(e);
		}
	}

	public boolean hasNext() {
		return entries.hasMoreElements();
	}

	public ClassSummary next() {
		JarEntry entry = (JarEntry) entries.nextElement();

		if (CodeProducer.isClass(entry.getName())) {
			try {
				return RuleClassVisitor.visit(jarFile.getInputStream(entry));
			} catch (IOException e) {
				throw new ADarwinException(e);
			}
		}

		return null;
	}
}
