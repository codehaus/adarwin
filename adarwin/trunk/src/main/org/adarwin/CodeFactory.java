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
import java.io.FileInputStream;
import java.io.File;
import java.util.StringTokenizer;

public class CodeFactory implements ICodeFactory {
	public Code create(String name) throws FileNotFoundException {
		if (name.indexOf(File.pathSeparator) != -1) {
			return createPath(name);
		}
		else {
			return createSingle(name);
		}
	}

	private Code createPath(String name) throws FileNotFoundException {
		StringTokenizer tokenizer = new StringTokenizer(name, File.pathSeparator);

		Code[] code = new Code[tokenizer.countTokens()];

		for (int cLoop = 0; tokenizer.hasMoreTokens(); ++cLoop) {
			code[cLoop] = create(tokenizer.nextToken());
		}

		return new ClassPath(code);
	}

	private Code createSingle(String name) throws FileNotFoundException {
		if (isClass(name)) {
			return new ClassFile(new FileInputStream(name));
		}
		else if (isJar(name)) {
			return new JarFile(name);
		}
		else { // Directory
			return new ClassDirectory(name);
		}
	}

	private boolean isClass(String token) {
		return token.endsWith(".class");
	}

	private boolean isJar(String token) {
		return token.endsWith(".jar");
	}
}
