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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileAccessor implements IFileAccessor {
	public String readFile(String name) throws IOException {
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(openFile(name)));

			StringBuffer buffer = new StringBuffer();

			String currentLine = reader.readLine();

			while (currentLine != null) {
				buffer.append(currentLine);
				currentLine = reader.readLine();
			}

			return buffer.toString();
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public String[] listFiles(String directory) {
		File[] files = new File(directory).listFiles();

		String[] fileNames = new String[files == null ? 0 : files.length];
		for (int fLoop = 0; fLoop < fileNames.length; fLoop++) {
			fileNames[fLoop] = files[fLoop].getAbsolutePath();
		}

		return fileNames;
	}

	public InputStream openFile(String name) throws FileNotFoundException {
		return new FileInputStream(name);
	}

	public boolean isDirectory(String name) {
		return new File(name).isDirectory();
	}

	public IFileIterator files(final String name) {
		return new DirectoryFileIterator(name, this);
	}
}
