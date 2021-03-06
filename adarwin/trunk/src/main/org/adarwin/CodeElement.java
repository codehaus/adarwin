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

import java.util.regex.Pattern;


public class CodeElement {
	public static CodeElement createUses(String usesClassName) {
		return new CodeElement(usesClassName, USES);
	}

	public static CodeElement createExtends(String usesClassName) {
		return new CodeElement(usesClassName, EXTENDS_OR_IMPLEMENTS);
	}

	protected static final int SOURCE = 0;
	protected static final int USES = 1;
	protected static final int EXTENDS_OR_IMPLEMENTS = 2;

	private final String className;
	private final int type;

	protected CodeElement(String className) {
		this(className, SOURCE);
	}

	protected CodeElement(String className, int codeType) {
		this.className = className;
		this.type = codeType;
	}

	public boolean isUses() {
		return SOURCE != type;
	}

	public boolean isSource() {
		return SOURCE == type;
	}

	public boolean isExtends() {
		return EXTENDS_OR_IMPLEMENTS == type;
	}

	public boolean equals(Object object) {
		if (object == null || !object.getClass().equals(getClass())) {
			return false;
		}

		CodeElement other = (CodeElement) object;

		return type == other.type && className.equals(other.className);
	}

	public int hashCode() {
		return className.hashCode() ^ type;
	}

	public boolean involvesObject() {
		return Object.class.getName().equals(className);
	}
	
	public boolean matches(String pattern) {
		return Pattern.matches(pattern, className);
	}

	public boolean classMatches(String pattern) {
		return Util.classMatches(pattern, className);
	}

	public boolean packageMatches(String pattern) {
		return Util.packageMatches(pattern, className);
	}
}
