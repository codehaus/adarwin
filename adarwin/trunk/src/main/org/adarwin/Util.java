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

public class Util {
	public static String className(Class aClass) {
        String name = aClass.getName();
		return name.substring(name.lastIndexOf('.') + 1);
    }

	public static String packageName(Class clazz) {
		String name = clazz.getName();
		return packageName(name);
	}

	public static String packageName(String name) {
		return name.substring(0, Math.max(0, name.lastIndexOf('.')));
	}

	public static String[] convertClassArrayToStringArray(Class[] classParameterTypes) {
		String[] parameterTypes = new String[classParameterTypes.length];
		
		for (int cLoop = 0; cLoop < classParameterTypes.length; ++cLoop) {
			parameterTypes[cLoop] = classParameterTypes[cLoop].getName();
		}
		
		return parameterTypes;
	}
}
