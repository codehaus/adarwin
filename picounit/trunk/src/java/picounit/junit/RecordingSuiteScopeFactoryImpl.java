package picounit.junit;

import picounit.Suite;
import picounit.suite.OrdinarySuiteScopeFactory;

import java.lang.reflect.Method;

public class RecordingSuiteScopeFactoryImpl implements RecordingSuiteScopeFactory {
	private final OrdinarySuiteScopeFactory ordinarySuiteScopeFactory;
	private final Recorder recorder;

	public RecordingSuiteScopeFactoryImpl(OrdinarySuiteScopeFactory ordinarySuiteScopeFactory,
		Recorder recorder) {

		this.ordinarySuiteScopeFactory = ordinarySuiteScopeFactory;
		this.recorder = recorder;
	}

	public void enterClass(Class testClass) {
		ordinarySuiteScopeFactory.enterClass(testClass);
	}

	public void runMethod(Method method) {
		recorder.record(new Event(Suite.class, method));

		ordinarySuiteScopeFactory.runMethod(method);
	}

	public void exit() {
		recorder.exit();
	
		ordinarySuiteScopeFactory.exit();
	}
}
