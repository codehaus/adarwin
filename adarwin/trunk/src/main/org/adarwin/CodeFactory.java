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
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CodeFactory implements ICodeFactory {
	public Code create(String name) throws FileNotFoundException {
		if (name.indexOf(File.pathSeparator) != -1) {
			return new ClassPath(name, this);
		}
		else {
			return createSingle(name);
		}
	}

	private Code createSingle(String name) throws FileNotFoundException {
		if (isClass(name)) {
			return new ClassFile(new FileInputStream(name));
		}
		else if (isJar(name)) {
			return new Jar(name);
		}
		else { // Directory
			return new ClassDirectory(name, this);
		}
	}

	public static boolean isClass(String token) {
		return token.endsWith(".class");
	}

	public static boolean isJar(String token) {
		return token.endsWith(".jar");
	}
}
