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

public class Type implements IType {
	public static final Type BOOLEAN = new Type("boolean", "Z");
	public static final Type BYTE = new Type("byte", "B");
	public static final Type CHAR = new Type("char", "C");
	public static final Type DOUBLE = new Type("double", "D");
	public static final Type FLOAT = new Type("float", "F");
	public static final Type INT = new Type("int", "I");
	public static final Type LONG = new Type("long", "J");
	public static final Type SHORT = new Type("short", "S");
	public static final Type VOID = new Type("void", "V");

	private final String type;
	private final String form;
	private final boolean isPrimative;

	public Type(String type) {
		this(type, getForm(type), false);
	}

	public Type(String type, String form) {
		this(type, form, true);
	}

	private Type(String type, String form, boolean isPrimative) {
		this.type = type;
		this.form = form;
		this.isPrimative = isPrimative;
	}
	
	public IType getType() {
		return this;
	}

	public String getTypeName() {
		return type;
	}

	public String getForm() {
		return form;
	}

	public boolean isPrimative() {
		return isPrimative;
	}
	
	public boolean equals(Object object) {
		if (object == null || 
			!object.getClass().equals(getClass())) {
			return false;
		}
		
		Type other = (Type) object;
		
		return isPrimative == other.isPrimative() &&
			getTypeName().equals(other.getTypeName());
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		return "Type(" + getTypeName() + ", " + isPrimative + ")";
	}

	private static String getForm(String clazz) {
		return "L" + clazz.replace('.', '/') + ";";
	}
}
