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

public class ArrayType implements IType {
	private IType type;

	public ArrayType(IType type) {
		this.type = type;
	}
	
	public IType getType() {
		return type;
	}
	
	public String getTypeName() {
		return type.getTypeName();
	}

	public String getForm() {
		return "[" + type.getForm();
	}
	
	public boolean isPrimative() {
		return type.isPrimative();
	}
}
