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

public class ElementType {
	public static final ElementType SOURCE = new ElementType("source");
	public static final ElementType USES = new ElementType("uses");
	public static final ElementType EXTENDS_OR_IMPLEMENTS = new ElementType("uses", "extends or implements");

	private final String type;
	private final String subType;

	private ElementType(String type) {
		this(type, "");
	}

	private ElementType(String type, String subType) {
		this.type = type;
		this.subType = subType;
	}

	private String getType() {
		return type;
	}

	public String getSubType() {
		return subType;
	}

	public boolean equals(Object object) {
		if (object == null ||
			!getClass().equals(object.getClass())) {
			return false;
		}

		ElementType other = (ElementType) object;

		return getType().equals(other.getType());
	}

	public String toString() {
		if (getSubType().length() == 0) {
			return getType();
		}
		else {
			return getType() + '.' + getSubType();
		}
	}
}
