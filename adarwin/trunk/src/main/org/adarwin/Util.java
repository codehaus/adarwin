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

import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Util {
	public static String className(String name) {
		return name.substring(name.lastIndexOf('.') + 1);
	}

	public static String packageName(String name) {
		return name.substring(0, Math.max(0, name.lastIndexOf('.')));
	}

	public static void appendArray(StringBuffer buffer, Object[] array) {
		for (int pLoop = 0; pLoop < array.length; pLoop++) {
			if (pLoop != 0) {
				buffer.append(", ");
			}
			buffer.append(array[pLoop]);
		}
	}

	public static String getToken(int index, String signature, String delimiters) {
		StringTokenizer stringTokenizer = new StringTokenizer(signature, delimiters);

		while (index-- > 0) {
			stringTokenizer.nextToken();
		}

		return stringTokenizer.nextToken();
	}

	public static String[] getTokens(int index, String signature, String delimiters) {
		StringTokenizer stringTokenizer = new StringTokenizer(signature, delimiters);
		String[] parameterTypes = new String[stringTokenizer.countTokens() - index];

		while (index-- > 0) {
			stringTokenizer.nextToken();
		}

		for (int pLoop = 0; pLoop < parameterTypes.length; pLoop++) {
			parameterTypes[pLoop] = stringTokenizer.nextToken();
		}

		return parameterTypes;
	}

	public static boolean matchesPatterns(String[] patterns, String[] parameterTypes) {
		if (patterns.length != parameterTypes.length) {
			return false;
		}

		for (int pLoop = 0; pLoop < patterns.length; pLoop++) {
			if (!Pattern.matches(patterns[pLoop], parameterTypes[pLoop])) {
				return false;
			}
		}

		return true;
	}

	public static boolean balanced(String searchIn, char left, char right) {
		return countNumberOf(searchIn, left) == countNumberOf(searchIn, right);
	}

	public static int countNumberOf(String searchIn, char searchFor) {
		int count = 0;
		byte[] chars = searchIn.getBytes();

		for (int cLoop = 0; cLoop < chars.length; cLoop++) {
			if (chars[cLoop] == searchFor) {
				count++;
			}
		}

		return count;
	}

	public static boolean classMatches(String pattern, String className) {
		return Pattern.matches(pattern, className(className));
	}

	public static boolean packageMatches(String pattern, String className) {
		return Pattern.matches(pattern, packageName(className));
	}
}
