package picounit.test;

import picounit.MainRunner;
import picounit.Runner;
import picounit.Suite;
import picounit.Test;
import picounit.Verify;
import picounit.impl.Filter;
import picounit.impl.NothingFilter;

import java.util.LinkedList;
import java.util.List;

public class FilterTest implements Test {
	// Fixtures
	private MainRunner picoSuiteRunnerImpl;
	private StringBuffer stringBuffer;
	private Verify verify;
	
	private Filter sterlingInUKFilter = new Filter() {
		public boolean passes(List callStack) {
			return matches(callStack, callStack(UKSuite.class, SterlingTest.class));
		}
	};

	public void setUp(Verify verify) {
		this.verify = verify;
		this.stringBuffer = new StringBuffer();
		
		this.picoSuiteRunnerImpl = new MainRunner(false);
		this.picoSuiteRunnerImpl.registerFixture(stringBuffer);
	}
	
	public void testRunNothing() {
		picoSuiteRunnerImpl.applyFilter(new NothingFilter()).run(IgnoreTest.class);

		verify.equal("", stringBuffer.toString());
	}

	public void testRunOneTestClass(Runner runner) {
//		StringBuffer stringBuffer = new StringBuffer();
		
//		runner.registerFixture(stringBuffer);
		
		Filter filter = new Filter() {
			public boolean passes(List callStack) {
				return matches(callStack, callStack(FilterSuite.class, DontIgnoreTest.class)); 
			}
		};

//		runner.applyFilter(filter);
//		runner.run(FilterSuite.class);
		
		picoSuiteRunnerImpl.applyFilter(filter).run(FilterSuite.class);

		verify.equal("DontIgnoreTest ", stringBuffer.toString());
	}

	public void testRunSterlingTestOnlyInUKSuite() {
		picoSuiteRunnerImpl.applyFilter(sterlingInUKFilter).run(UKSuite.class);

		verify.equal("SterlingTest ", stringBuffer.toString());
	}

	public void testDontRunSterlingTestInUSSuite() {
		picoSuiteRunnerImpl.applyFilter(sterlingInUKFilter).run(USSuite.class);

		verify.equal("", stringBuffer.toString());
	}

	public static class FilterSuite implements Suite {
		public void suite(Runner runner) {
			runner.run(IgnoreTest.class);
			runner.run(DontIgnoreTest.class);
		}
	}
	
	public static class UKSuite implements Suite {
		public void suite(Runner runner) {
			runner.run(DollarTest.class);
			runner.run(SterlingTest.class);
		}
	}
	
	public static class USSuite implements Suite {
		public void suite(Runner runner) {
			runner.run(DollarTest.class);
			runner.run(SterlingTest.class);
		}
	}

	public static class IgnoreTest implements Test {
		public void testShouldBeIgnored(StringBuffer stringBuffer) {
			stringBuffer.append("IgnoreTest ");
		}
	}

	public static class DontIgnoreTest implements Test {
		public void testShouldBeIgnored(StringBuffer stringBuffer) {
			stringBuffer.append("DontIgnoreTest ");
		}
	}

	public static class SterlingTest implements Test {
		public void testSterling(StringBuffer stringBuffer) {
			stringBuffer.append("SterlingTest ");
		}
	}

	public static class DollarTest implements Test {
		public void testShouldBeIgnored(StringBuffer stringBuffer) {
			stringBuffer.append("DollarTest ");
		}
	}

	private boolean matches(List callStack, List pattern) {
		String patternString = pattern.toString();
		patternString = patternString.substring(1, patternString.length() - 1);
		String callStackString = callStack.toString();
		callStackString = callStackString.substring(1, callStackString.length() - 1);

		return patternString.indexOf(callStackString) != -1;
	}

	private List callStack(Object first, Object second) {
		List callStack = new LinkedList();

		callStack.add(0, first);
		callStack.add(0, second);

		return callStack;
	}
}
