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
import java.io.InputStream;

import org.adarwin.rule.Rule;
import org.objectweb.asm.ClassReader;

public class ClassFile implements Code {
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
			ClassSummary classSummary = new RuleClassVisitor().visit(this);
            return new Result(rule.inspect(classSummary), classSummary);
        } finally {
            inputStream.close();
        }
    }

	public ClassSummary accept(RuleClassVisitor ruleClassVisitor) throws IOException {
		return ruleClassVisitor.visit(new ClassReader(inputStream));
	}
}
