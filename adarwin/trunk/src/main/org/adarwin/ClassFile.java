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
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.InputStream;

public class ClassFile extends CodeBase {
	private InputStream inputStream;

	public static InputStream getClassInputStream(Class clazz) {
		return clazz.getResourceAsStream(Util.className(clazz) + ".class");
	}

    public ClassFile(Class clazz) {
        this(getClassInputStream(clazz));
    }

	public ClassFile(InputStream inputStream) {
		this.inputStream = inputStream;
    }

    public Result evaluate(Rule rule) throws IOException {
        try {
			return new RuleClassVisitor(rule).visit(this);
        } finally {
            inputStream.close();
        }
    }

	public Result accept(RuleClassVisitor ruleClassVisitor) throws IOException {
		return ruleClassVisitor.visit(new ClassReader(inputStream));
	}
}
