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
import java.io.IOException;
import java.io.InputStream;

public interface IFileAccessor {
	String[] listFiles(String directory);

	InputStream openFile(String name) throws FileNotFoundException;
	
	String readFile(String name) throws IOException;

	boolean isDirectory(String name);
}
