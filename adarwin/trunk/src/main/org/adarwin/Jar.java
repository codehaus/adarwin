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

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Jar implements Code {
    private final String fileName;

    public Jar(String fileName) {
        this.fileName = fileName;
    }

	public void evaluate(Rule rule, RuleListener ruleListener) throws IOException {
		JarFile jarFile = new JarFile(fileName);

		for (Enumeration entries = jarFile.entries(); entries.hasMoreElements();) {
			JarEntry entry = (JarEntry) entries.nextElement();
			
			if (CodeFactory.isClass(entry.getName())) {
				new ClassFile(jarFile.getInputStream(entry)).evaluate(rule, ruleListener);
			}
		}
	}
}
