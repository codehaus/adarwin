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

public class Method {
	private final String name;
	private final String returnType;
	private final String[] parameterTypes;
	private String toString;

	public Method(String name, Class returnType, Class[] parameterTypes) {
		this(name, returnType.getName(), Util.convertClassArrayToStringArray(parameterTypes));
	}

	public Method(String name, String returnType, String[] parameterTypes)  {
		this.name = name;
		this.parameterTypes = parameterTypes;
		this.returnType = returnType;
	}
	
	public String getName() {
		return name;
	}

	public String getReturnType() {
		return returnType;
	}

	public String[] getParameterTypes() {
		return parameterTypes;
	}

	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		synchronized (this) {
			if (toString == null) {

				StringBuffer buffer = new StringBuffer(getReturnType() + " " + getName() + '(');
				
				for (int pLoop = 0; pLoop < parameterTypes.length; pLoop++) {
					if (pLoop != 0) {
						buffer.append(", ");
					}
					buffer.append(parameterTypes[pLoop]);
				}
				
				buffer.append(')');
				
				toString = buffer.toString();				
			}
		}
		return toString;
	}
	
	public boolean equals(Object object) {
		if (object == null ||
			!object.getClass().equals(getClass())) {
			return false;
		}
		
		if (object == this) {
			return true;
		}
		
		Method other = (Method) object;
		
		return getReturnType().equals(other.getReturnType()) &&
			Arrays.equals(getParameterTypes(), other.getParameterTypes());
	}
}
