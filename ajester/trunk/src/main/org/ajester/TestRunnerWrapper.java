package org.ajester;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.runner.BaseTestRunner;
import junit.runner.TestSuiteLoader;

public class TestRunnerWrapper {
//	public static void main(String[] args) throws Exception {
//		TestResults r = new TestRunnerWrapper().run("org.ajester.testmodel.SimpleConditionalTest",
//				"org.ajester.testmodel.BooleanModel",
//				new BooleanReturnCodeAdapter());
//		for (Iterator iterator = r.getFailures().iterator(); iterator.hasNext(); ) {
//			System.out.println("Failure: " + iterator.next());
//		}
//
//		for (Iterator iterator = r.getErrors().iterator(); iterator.hasNext(); ) {
//			System.out.println("Errors: " + iterator.next());
//		}
//	}
//
	public TestResults run(String testClassName, MutatingClassAdapter mutatingClassAdapter)
		throws Exception {
		
		return new TestRunner(mutatingClassAdapter).run(testClassName);
	}

	private class TestRunner extends BaseTestRunner {
		private MutatingClassAdapter mutatingClassAdapter;

		public TestRunner(MutatingClassAdapter mutatingClassAdapter) {
			this.mutatingClassAdapter = mutatingClassAdapter;
		}
		
		public TestResults run(String testClassName) throws Exception {
			try {
				Test test = getTest(testClassName);
				if (test == null) {
					throw new Exception("Unable to find test: " + testClassName);
				}
				return doRun(test);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Could not create and run test suite: " + e);
			}
		}

		public TestSuiteLoader getLoader() {
			return new MutatingClassLoader(mutatingClassAdapter);
		}

		public void testFailed(int status, Test test, Throwable t) {
		}

		public void testStarted(String testName) {
		}

		public void testEnded(String testName) {
		}

		private TestResults doRun(Test suite) {
			TestResult result = new TestResult();
			long startTime = System.currentTimeMillis();
			suite.run(result);
			long endTime = System.currentTimeMillis();
			long runTime = endTime - startTime;

			return new TestResults(result);
		}

		protected void runFailed(String message) {
		}
	}
}