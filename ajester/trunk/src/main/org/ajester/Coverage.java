package org.ajester;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Coverage {
	public static final String METHOD_COVERED = "methodCovered";

	private static final CodeLocation UNKNOWN_CALLER =
		new CodeLocation("<UnknownClass>", "<UnknownMethod>");	

	public static Coverage getInstance() {
		return coverage;
	}

	public static void methodCovered(String className, String methodName) {
		getInstance().methodCovered(new CodeLocation(className.replace('/','.'), methodName));
	}

	public static void reset() {
		coverage = new Coverage();
	}

	private static Coverage coverage = new Coverage();

	private CodeLocation caller;
	private final Set methodsCovered;
	private final Map callerCalledMap;
	private final Map calledCallerMap;
	
	public Coverage() {
		this(new HashSet(), new HashMap(), new HashMap());
	}
	
	public Coverage(Set methodsCovered, Map callerCalledMap, Map calledCallerMap) {
		caller = UNKNOWN_CALLER;
		this.methodsCovered = methodsCovered;
		this.callerCalledMap = callerCalledMap;
		this.calledCallerMap = calledCallerMap;
	}

	public void setCaller(CodeLocation rootCaller) {
		caller = rootCaller;
	}

	public void methodCovered(CodeLocation codeLocation) {
		methodsCovered.add(codeLocation);

		addCallerToCalled(codeLocation);
		addCalledToCaller(codeLocation);
	}

	public boolean contains(CodeLocation codeLocation) {
		return methodsCovered.contains(codeLocation);
	}

	public Set getCoverage() {
		return methodsCovered;
	}
	
	public Map getCallerCalledMap() {
		return callerCalledMap;
	}
	
	public Map getCalledCallerMap() {
		return calledCallerMap;
	}

	public Set getCodeCalled(CodeLocation testLocation) {
		return (Set) callerCalledMap.get(testLocation);
	}

	public Set getTestsCovering(CodeLocation codeLocation) {
		return (Set) calledCallerMap.get(codeLocation);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("Coverage:");

		for (Iterator iterator = calledCallerMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			buffer.append("\n" + entry.getKey() + " -> " + entry.getValue());
		}

		return buffer.toString();
	}

	private void addCallerToCalled(CodeLocation called) {
		Set methodsCalled = (Set) callerCalledMap.get(caller);

		if (methodsCalled == null) {
			methodsCalled = new HashSet();
			methodsCalled.add(called);
			callerCalledMap.put(caller, methodsCalled);
		}
		else {
			methodsCalled.add(called);
		}
	}

	private void addCalledToCaller(CodeLocation called) {
		Set callers = (Set) calledCallerMap.get(called);

		if (callers == null) {
			callers = new HashSet();
			callers.add(caller);
			calledCallerMap.put(called, callers);
		}
		else {
			callers.add(called);
		}
	}
}
