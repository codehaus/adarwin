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
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

class MockJarFile extends java.util.jar.JarFile {
    private Class[] classes;

    public MockJarFile(Class[] classes) throws IOException {
        super("");
        this.classes = classes;
    }

    public Enumeration entries() {
        System.out.println("MJF.entries");
        return new Enumeration() {
            int index = 0;
            public boolean hasMoreElements() {
                return index < classes.length;
            }
            public Object nextElement() {
                return new MockJarEntry(classes[index++]);
            }
        };
    }

//    public Enumeration entries() {
//        System.out.println("MJF.entries");
//        final Enumeration enum = super.entries();
//        return new Enumeration() {
//            public boolean hasMoreElements() {
//            return enum.hasMoreElements();
//            }
//            public Object nextElement() {
//            ZipEntry ze = (ZipEntry)enum.nextElement();
//                System.out.println("MJF.entries$Enumeration.nextElement");
//            return new MockJarEntry(ze);
//            }
//        };
//    }

//    public Enumeration entries() {
//        Enumeration enumeration = super.entries();
//
//        System.out.println("MJF.entries = " + enumeration);
//        while (enumeration.hasMoreElements()) {
//            JarEntry entry = (JarEntry) enumeration.nextElement();
//            System.out.println("MJF.entries = " + entry + ", class = " + entry.getClass());
//        }
//
//        return super.entries();
//    }

    public Manifest getManifest() throws IOException {
        System.out.println("MJF.getManifest");
        return super.getManifest();
    }

    public synchronized InputStream getInputStream(ZipEntry ze) throws IOException {
        System.out.println("MJF.getInputStream().ze = " + ze);
        return super.getInputStream(ze);
    }

    public JarEntry getJarEntry(String name) {
        System.out.println("MJF.getJarEntry().name = " + name);
        return super.getJarEntry(name);
    }

    public ZipEntry getEntry(String name) {
        System.out.println("MJF.getEntry().name = " + name);
        return super.getEntry(name);
    }

    public int size() {
        System.out.println("MJF.size");
        return super.size();
    }

    public void close() throws IOException {
        System.out.println("MJF.close");
        super.close();
    }

    protected void finalize() throws IOException {
        System.out.println("MJF.finalise");
        super.finalize();
    }

    public String getName() {
        System.out.println("MJF.getName");
        return super.getName();
    }

    public int hashCode() {
        System.out.println("MJF.hashCode");
        return super.hashCode();
    }

    protected Object clone() throws CloneNotSupportedException {
        System.out.println("MJF.clone");
        return super.clone();
    }

    public boolean equals(Object obj) {
        System.out.println("MJF.equals().obj = " + obj);
        return super.equals(obj);
    }

    public String toString() {
        System.out.println("MJF.toString");
        return super.toString();
    }
}
