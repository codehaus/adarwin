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
import java.security.cert.Certificate;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;

class MockJarEntry extends JarEntry {
    private Class clazz;

    public MockJarEntry(Class clazz) {
        super("");
        this.clazz = clazz;
    }

    public Certificate[] getCertificates() {
        System.out.println("MJE.getCertificates");
        return super.getCertificates();
    }

    public Attributes getAttributes() throws IOException {
        System.out.println("MJE.getAttributes");
        return super.getAttributes();
    }

    public int getMethod() {
        System.out.println("MJE.getMethod");
        return super.getMethod();
    }

    public int hashCode() {
        System.out.println("MJE.hashCode");
        return super.hashCode();
    }

    public long getCompressedSize() {
        System.out.println("MJE.getCompressedSize");
        return super.getCompressedSize();
    }

    public long getCrc() {
        System.out.println("MJE.getCrc");
        return super.getCrc();
    }

    public long getSize() {
        System.out.println("MJE.getSize");
        return super.getSize();
    }

    public long getTime() {
        System.out.println("MJE.getTime");
        return super.getTime();
    }

    public boolean isDirectory() {
        System.out.println("MJE.isDirectory");
        return super.isDirectory();
    }

    public byte[] getExtra() {
        System.out.println("MJE.getExtra");
        return super.getExtra();
    }

    public void setMethod(int method) {
        System.out.println("MJE.setMethod().method = " + method);
        super.setMethod(method);
    }

    public void setCompressedSize(long csize) {
        System.out.println("MJE.setCompressedSize().csize = " + csize);
        super.setCompressedSize(csize);
    }

    public void setCrc(long crc) {
        System.out.println("MJE.setCrc().crc = " + crc);
        super.setCrc(crc);
    }

    public void setSize(long size) {
        System.out.println("MJE.setSize().size = " + size);
        super.setSize(size);
    }

    public void setTime(long time) {
        System.out.println("MJE.setTime().time = " + time);
        super.setTime(time);
    }

    public void setExtra(byte[] extra) {
        System.out.println("MJE.setExtra().extra = " + extra);
        super.setExtra(extra);
    }

    public Object clone() {
        System.out.println("MJE.clone");
        return super.clone();
    }

    public String getComment() {
        System.out.println("MJE.getComment");
        return super.getComment();
    }

    public String getName() {
        return clazz.getName();
    }

    public String toString() {
        System.out.println("MJE.toString");
        return super.toString();
    }

    public void setComment(String comment) {
        System.out.println("MJE.setComment().comment = " + comment);
        super.setComment(comment);
    }
}
