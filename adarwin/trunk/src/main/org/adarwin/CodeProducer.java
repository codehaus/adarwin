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
import java.io.FileNotFoundException;

public class CodeProducer {
	private final String name;
	private final IFileAccessor fileAccessor;

	public CodeProducer(String name, IFileAccessor fileAccessor) {
		this.name = name;
		this.fileAccessor = fileAccessor;
	}
	
	public CodeIterator iterator() {
		if (isClass(name)) {
			return new CodeIterator() {
				private boolean done = false;

				public boolean hasNext() {
					return !done;
				}

				public ClassSummary next() {
					try {
						return RuleClassVisitor.visit(fileAccessor.openFile(name));
					}
					catch (FileNotFoundException e) {
						throw new ADarwinException(e);
					}
					finally {
						done = true;
					}
				}
			};
		}
		else if (isClassPath(name)) {
			return new ClassPathIterator(name, fileAccessor);
		}
		else if (isJar(name)) {
			return new JarIterator(name, fileAccessor);
		}
		else if (fileAccessor.isDirectory(name)) {
			return new ClassDirectoryIterator(fileAccessor, fileAccessor.files(name));
		}

		return CodeIterator.NULL;
	}

	private boolean isClassPath(String name) {
		return name.indexOf(File.pathSeparator) != -1;
	}

	public static boolean isClass(String token) {
		return token.endsWith(".class");
	}

	private boolean isJar(String token) {
		return token.endsWith(".jar");
	}
}
