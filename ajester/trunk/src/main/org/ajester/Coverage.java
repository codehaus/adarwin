package org.ajester;

import java.util.HashSet;
import java.util.Set;

public class Coverage {
	public static final String METHOD_COVERED = "methodCovered";

	private static Set methodsCovered = new HashSet();

	public static void methodCovered(String className, String methodName) {
		methodsCovered.add(methodName);
	}

	public static Set getMethodsCovered() {
		return methodsCovered;
	}
}
