package picounit.runner;

import picounit.Mocker;
import picounit.Test;
import picounit.TestInstance;
import picounit.Verify;
import picounit.impl.MethodInvoker;
import picounit.impl.ReportImpl;
import picounit.impl.ScopeImpl;

import java.lang.reflect.Method;

public class ReportingTest implements Test {
	// Unit
	private ReportImpl report;

	// Mocks
	private MethodInvoker methodInvoker;

	public void mock(MethodInvoker methodInvoker) {
		this.report = new ReportImpl(methodInvoker);
		
		this.methodInvoker = methodInvoker;
	}
	
	public void testStartsOutWithAllCountsZero(Verify verify) {
		verify.equal(0, report.visitedCount());
		verify.equal(0, report.successesCount());
		verify.equal(0, report.failuresCount());
	}

	public void testAddEvents(Mocker mocker, Verify verify) {
		methodInvoker.invokeMethod(TestInstance.testOne);
		
		mocker.replay();

		report.enter(new ScopeImpl(Test.TEST, Test.class, TestInstance.class));
		report.enter(new ScopeImpl(Test.TEST_METHOD, Method.class, TestInstance.testOne));
		report.exit();
		report.exit();

		verify.equal(2, report.visitedCount());
		verify.equal(2, report.successesCount());
		verify.equal(0, report.failuresCount());
	}
}
