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

import java.util.Arrays;

public class Constructor {
	public static final Constructor EMPTY_CONSTRUCTOR = new Constructor(new String[0]);
	
	private final String[] parameterTypes;
	private CodeElement[] exceptions;
	private String toString;
	
	public Constructor(String[] parameterTypes) {
		this(parameterTypes, CodeElement.EMPTY_ARRAY);
	}
	
	public Constructor(String[] parameterTypes, CodeElement[] exceptions) {
		this.parameterTypes = parameterTypes;
		this.exceptions = exceptions;
	}

	public String toString() {
		synchronized (this) {
			if (toString == null) {
				StringBuffer buffer = new StringBuffer("Constructor(");
				appendArray(buffer, parameterTypes);
				buffer.append(')');
				
				if (exceptions != CodeElement.EMPTY_ARRAY) {
					buffer.append(" throws(");
					appendArray(buffer, exceptions);
					buffer.append(")");
				}
				
				toString = buffer.toString();
			}
		}
		
		return toString;
	}
	
	private void appendArray(StringBuffer buffer, Object[] array) {
		for (int pLoop = 0; pLoop < array.length; pLoop++) {
			if (pLoop != 0) {
				buffer.append(", ");
			}
			buffer.append(array[pLoop]);
		}
	}

	public int hashCode() {
		return toString().hashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj == null ||
			!obj.getClass().equals(getClass())) {
			return false;
		}
		
		Constructor other = (Constructor) obj;		
		
		return Arrays.equals(parameterTypes, other.parameterTypes);
	}
}
