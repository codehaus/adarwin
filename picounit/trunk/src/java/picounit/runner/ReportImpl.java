package picounit.runner;

import picounit.Suite;
import picounit.Test;
import picounit.impl.FailingTypeFilter;
import picounit.impl.Logger;
import picounit.impl.PassingTypeFilter;
import picounit.impl.PicoResolver;
import picounit.impl.Reporter;
import picounit.impl.ScopeFilter;
import picounit.impl.ScopeTypeFilter;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ReportImpl implements Report {
	private final Date start;
	private final Logger logger;

	private final Collection visited = new LinkedList();
	
	private List stack = new LinkedList();


	public ReportImpl(Logger logger, PicoResolver picoResolver) {
		this.logger = logger;
		this.start = new Date();
	}

	public void registryEvent(Class someClass) {
		if (Reporter.class.isAssignableFrom(someClass)) {
			
		}
	}
	
	public Scope[] matching(ScopeFilter scopeFilter) {
		Collection matching = new LinkedList();

		for (Iterator iterator = visited.iterator(); iterator.hasNext(); ) {
			Scope scope = (Scope) iterator.next();
			if (scopeFilter.matches(scope)) {
				matching.add(scope);
			}
		}

		return (Scope[]) matching.toArray(new Scope[matching.size()]);
	}
	
	public int count(ScopeFilter scopeFilter) {
		return matching(scopeFilter).length;
	}

	public int visitedCount() {
		return visited.size();
	}

	public int successesCount() {
		return count(ScopeFilter.PASSING_FILTER);
	}

	public int failuresCount() {
		return count(ScopeFilter.FAILING_FILTER);
	}

	public int visitedCount(Class type) {
		return count(new ScopeTypeFilter(type));
	}

	public int successesCount(Class type) {
		return count(new PassingTypeFilter(type));
	}
	
	public int failuresCount(Class type) {
		return count(new FailingTypeFilter(type));
	}

	public void enter(Scope scope) {
		visited.add(scope);
		if (Method.class.equals(scope.getType())) {
			logger.info(".");
		}

		stack.add(0, scope);
	}

	public void exit() {
		stack.remove(0);
	}

	public void exit(Throwable reason) {
		Scope scope = (Scope) stack.remove(0);
		scope.setFailure(reason);

//		System.err.println("matches: " + new FailingTypeFilter(Method.class).matches(scope) +
//			", " + scope.getType());
//		reason.printStackTrace();
	}
	
	public void error(Scope scope, Throwable reason) {
		scope.setFailure(reason);
		visited.add(scope);
	}

	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		Scope[] failures = //matching(ScopeFilter.FAILING_FILTER); 
			matching(new FailingTypeFilter(Method.class));

		for (int index = 0; index < failures.length; index++) {
			stringBuffer.append("Failure[" + (index + 1) + "] = " + failures[index].value() + "\n");
		}

		stringBuffer.append("\n");

		for (int index = 0; index < failures.length; index++) {
			stringBuffer.append("Detail[" + (index + 1) + "] = \n");
			stringBuffer.append(failures[index] + "\n");
		}
		
		Date now = new Date();

		return "\n" + start + " - " + now + " (" + (now.getTime() - start.getTime())/1000.0 + ")" + 
			"\nSuites: " + visitedCount(Suite.class) + ", Tests: " + visitedCount(Test.class) +
			", Methods: " + visitedCount(Method.class) + ", Successes: " + successesCount(Method.class) +
			", Failures: " + failuresCount(Method.class) + "\n\n" + stringBuffer.toString();
	} 
}
