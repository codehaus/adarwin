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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.adarwin.rule.Rule;

public class JarFile implements Code {
    private String fileName;

	public static String createJarFile(Class clazz) throws IOException {
        return createJarFile(new Class[] {clazz});
    }

    public JarFile(String fileName) {
        this.fileName = fileName;
    }

	public JarFile(Class clazz) throws IOException {
		this(new Class[] {clazz});
	}

	public JarFile(Class[] classes) throws IOException {
		this(createJarFile(classes));
	}

    public Result evaluate(Rule rule) throws IOException {
        java.util.jar.JarFile jarFile = new java.util.jar.JarFile(fileName);
		AggregateResult aggregateResult = new AggregateResult();

        for (Enumeration enumeration = jarFile.entries(); enumeration.hasMoreElements();) {
            JarEntry entry = (JarEntry) enumeration.nextElement();
            if (isClassFile(entry.getName())) {
                aggregateResult.append(new ClassFile(jarFile.getInputStream(entry)).evaluate(rule));
            }
        }

		return aggregateResult;
    }

    private boolean isClassFile(String fileName) {
        return fileName.endsWith(".class");
    }

	private static String createJarFile(Class[] classes) throws IOException {
        File tempFile = File.createTempFile("adarwin-jar-test", ".jar");

        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(tempFile));

        addManifestFile(zipOutputStream);

        for (int cLoop = 0; cLoop < classes.length; ++cLoop) {
            addEntry(zipOutputStream, classes[cLoop]);
        }

        zipOutputStream.close();

        return tempFile.getAbsolutePath();
    }

	private static void addManifestFile(ZipOutputStream zipOutputStream) throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry("META-INF/Manifest.mf"));
        zipOutputStream.write("Dummy manifest file".getBytes());
        zipOutputStream.closeEntry();
    }

	/*
	 * TODO: Consider moving this to ClassFile
	 */
    private static void addEntry(ZipOutputStream zipOutputStream, Class clazz) throws IOException {
        String fileName = clazz.getName().replace('.', '/') + ".class";
        zipOutputStream.putNextEntry(new ZipEntry(fileName));
        InputStream classStream = ClassFile.getClassInputStream(clazz);

        copyInputStreamToOutputStream(classStream, zipOutputStream);

        zipOutputStream.closeEntry();
    }

	private static void copyInputStreamToOutputStream(InputStream classStream, OutputStream outputStream) throws IOException {
        int read = classStream.read();
        while (read != -1) {
            outputStream.write(read);
            read = classStream.read();
        }
    }
}
