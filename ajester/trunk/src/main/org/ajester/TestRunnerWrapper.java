package org.ajester;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestListener;
import junit.framework.TestResult;
import junit.runner.BaseTestRunner;
import junit.runner.TestSuiteLoader;

public class TestRunnerWrapper {
	public Report run(Class testClass, InstructionMutator instructionMutator) throws Exception {
		return run(testClass.getName(), instructionMutator);
	}
	
	public Report run(String testClassName, InstructionMutator mutator) throws Exception {
		return new TestRunner(mutator).run(testClassName);
	}
	
	private class TestRunner extends BaseTestRunner {
		private final InstructionMutator instructionMutator;
		private final MutatingClassLoader loader;

		public TestRunner(final InstructionMutator instructionMutator) {
			this.instructionMutator = instructionMutator;
			loader = new MutatingClassLoader(instructionMutator);
		}
		
		public Report run(String testClassName) throws Exception {
			try {
				Coverage.reset();
				Test test = getTest(testClassName);
				if (test == null) {
					throw new Exception("Unable to find test: " + testClassName);
				}
				return doRun(test);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Could not create and run test suite: " + e);
			}
		}

		public TestSuiteLoader getLoader() {
			return loader;
		}

		public void testFailed(int status, Test test, Throwable t) {
		}

		public void testStarted(String testName) {
		}

		public void testEnded(String testName) {
		}

		private Report doRun(Test suite) {
			TestResult result = new TestResult();
			result.addListener(new TestListener() {
				public void addError(Test test, Throwable t) {
				}

				public void addFailure(Test test, AssertionFailedError t) {
				}

				public void endTest(Test test) {
				}

				public void startTest(Test test) {
					TestCase testCase = (TestCase) test;
					Coverage.getInstance().setCaller(new CodeLocation(testCase.getClass(),
						testCase.getName()));
				}
			});

			suite.run(result);

			return new Report(convertEnumerationToSet(result.failures()),
				convertEnumerationToSet(result.errors()), instructionMutator.getMutations(),
				Coverage.getInstance());
		}

		protected void runFailed(String message) {
		}
	}
	
	public static Set convertEnumerationToSet(Enumeration enumeration) {
		Set set = new HashSet();
		
		while(enumeration.hasMoreElements()) {
			set.add(enumeration.nextElement());
		}
		
		return set;
	}
}