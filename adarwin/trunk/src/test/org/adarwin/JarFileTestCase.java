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
import org.adarwin.rule.SourceRule;
import org.adarwin.testmodel.a.InPackageA;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class JarFileTestCase extends RuleTestCase {
    private final Rule rule = new SourceRule(createPackageRule(InPackageA.class));
    
    public void testEmptyJar() throws IOException {
    	assertNumMatches(0, rule, createJarFile(new Class[0]));
    }

    public void testJarWithOneClass() throws IOException {
    	assertNumMatches(1, rule, createJarFile(InPackageA.class));
    }

	public static Jar createJarFile(Class clazz) throws IOException {
		return createJarFile(new Class[] {clazz});
	}

	public static Jar createJarFile(Class[] classes) throws IOException {
	    File tempFile = File.createTempFile("adarwin-jar-test", ".jar");
	
	    ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(tempFile));
	
	    addManifestFile(zipOutputStream);
	
	    for (int cLoop = 0; cLoop < classes.length; ++cLoop) {
	        addEntry(zipOutputStream, classes[cLoop]);
	    }
	
	    zipOutputStream.close();
	
	    return new Jar(tempFile.getAbsolutePath());
	}

	private static void addManifestFile(ZipOutputStream zipOutputStream) throws IOException {
	    zipOutputStream.putNextEntry(new ZipEntry("META-INF/Manifest.mf"));
	    zipOutputStream.write("Dummy manifest file".getBytes());
	    zipOutputStream.closeEntry();
	}

	private static void addEntry(ZipOutputStream zipOutputStream, Class clazz) throws IOException {
	    String fileName = clazz.getName().replace('.', '/') + ".class";
	    zipOutputStream.putNextEntry(new ZipEntry(fileName));
	    InputStream classStream = Util.getInputStream(clazz);
	
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

