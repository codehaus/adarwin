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
import java.io.IOException;

import org.adarwin.rule.Rule;

public class ClassDirectory extends CodeBase {
    private String directory;

    public ClassDirectory(String directory) {
        this.directory = directory;
    }

    public Result evaluate(Rule rule) throws IOException {
        File[] files = new File(directory).listFiles();
		AggregateResult result = new AggregateResult();

        for (int fLoop = 0; files != null && fLoop < files.length; ++fLoop) {
            File file = files[fLoop];
            if (file.isDirectory()) {
				Code code = new ClassDirectory(files[fLoop].getAbsolutePath());
				Result directoryResult = code.evaluate(rule);
				if (directoryResult.getCount() > 0) {
					result.append(directoryResult);
				}

            }
            else if (file.getName().endsWith(".class")) {
				Code code = new ClassFile(new FileInputStream(file));
				Result fileResult = code.evaluate(rule);
				if (fileResult.getCount() > 0) {
					result.append(fileResult);
				}
            }
        }

		return result;
    }
}
