package picounit.runner;

import picounit.MainRunner;
import picounit.Test;
import picounit.Verify;
import picounit.impl.Logger;
import picounit.impl.NullLogger;
import picounit.impl.Report;

import java.io.PrintStream;
import java.lang.reflect.Method;

public class ReportingTest implements Test {
	// Fixtures
	private Report report;

	// Unit
	private final MainRunner suiteRunner = new MainRunner(false);

	public static class WooWooLogger implements Logger {

		public void info(String message) {
			System.err.println("woo woo");
		}

		public void error(String message) {
			System.err.println("woo woo");
		}

		public PrintStream printStream() {
			return System.err;
		}
	}
	
	public void testPassingTest(Verify verify) {
	 	suiteRunner.registerFixture(Logger.class, NullLogger.class).run(PassingTest.class);

	 	report = (Report) suiteRunner.get(Report.class);

	 	verify.equal(1, report.visitedCount(Test.class));
	 	verify.equal(2, report.visitedCount(Method.class));
	 	verify.equal(2, report.successesCount(Method.class));
	 	verify.equal(0, report.failuresCount(Method.class));

	 	verify.equal(9, report.visitedCount());
	 	verify.equal(9, report.successesCount());
	 	verify.equal(0, report.failuresCount());
	}

	public void testFailingTest(Verify verify) {
		suiteRunner.run(FailingTest.class);

		report = (Report) suiteRunner.get(Report.class);

	 	verify.equal(1, report.visitedCount(Test.class));
	 	verify.equal(2, report.visitedCount(Method.class));
	 	verify.equal(0, report.successesCount(Method.class));
	 	verify.equal(2, report.failuresCount(Method.class));
	 	
	 	verify.equal(9, report.visitedCount());
	 	verify.equal(7, report.successesCount());
	 	verify.equal(2, report.failuresCount());
	}

	public static class PassingTest implements Test {
		public void testFirst(Verify verify) {
			verify.that(true);
		}

		public void testSecond(Verify verify) {
			verify.not(false);
		}
	}

	public static class FailingTest implements Test {
		public void testFirst(Verify verify) {
			verify.that(false);
		}

		public void testSecond(Verify verify) {
			verify.not(true);
		}
	}
}
