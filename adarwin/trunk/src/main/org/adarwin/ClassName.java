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

public class ClassName {
	private final String packageName;
	private final String className;
	private final String fullClassName;

	public ClassName(String fullClassName) {
		this(packageName(fullClassName), className(fullClassName), fullClassName);
	}

	private ClassName(String packageName, String className, String fullClassName) {
		this.packageName = packageName;
		this.className = className;
		this.fullClassName = fullClassName;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

	public String getFullClassName() {
		return fullClassName;
	}

	public String toString() {
		return getFullClassName();
	}

	public boolean isObject() {
		return Object.class.getName().equals(getFullClassName());
	}

	public int hashCode() {
		return getClassName().hashCode() ^ getPackageName().hashCode();
	}

	public boolean equals(Object object) {
		if (object == null || !getClass().equals(object.getClass())) {
			return false;
		}

		if (object == this) {
			return true;
		}

		ClassName other = (ClassName) object;

		return getPackageName().equals(other.getPackageName()) &&
			getClassName().equals(other.getClassName());
	}

	private static String packageName(String fullClassName) {
		if (fullClassName.indexOf('.') != -1) {
			return fullClassName.substring(0, fullClassName.lastIndexOf('.'));
		}
		else {
			return "";
		}
	}

	private static String className(String fullClassName) {
		if (fullClassName.indexOf('.') != -1) {
			return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
		}
		else {
			return fullClassName;
		}
	}
}
