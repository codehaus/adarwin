package picounit.runner;

import picounit.Mocker;
import picounit.Test;
import picounit.TestInstance;
import picounit.Verify;
import picounit.impl.Logger;
import picounit.impl.ReportImpl;
import picounit.impl.ScopeImpl;

import java.lang.reflect.Method;

public class ReportTest implements Test {
	// Mocks
	private Logger logger;

	// Unit
	private ReportImpl report;

	public void mock(Logger logger) {
		this.logger = logger;

		this.report = new ReportImpl();
	}

	public void testStartsOutWithAllCountsZero(Verify verify) {
		verify.equal(0, report.visitedCount());
		verify.equal(0, report.successesCount());
		verify.equal(0, report.failuresCount());
	}

	public void testAddEvents(Mocker mocker, Verify verify) {
		mocker.replay();

		report.enter(new ScopeImpl(Test.class, TestInstance.class));
		report.enter(new ScopeImpl(Method.class, TestInstance.testOne));
		report.exit();
		report.exit();

		verify.equal(2, report.visitedCount());
		verify.equal(2, report.successesCount());
		verify.equal(0, report.failuresCount());
	}
}
