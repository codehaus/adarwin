package picounit.junit;

import picounit.Test;
import picounit.test.TestScopeFactory;

import java.lang.reflect.Method;

public class RecordingTestScopeFactoryImpl implements TestScopeFactory {
	private final Recorder recorder;

	public RecordingTestScopeFactoryImpl(Recorder recorder) {
		this.recorder = recorder;
	}

	public void enterClass(Class testClass) {
	}

	public void runMethod(Method method) {
		recorder.record(new Event(Test.class, method));
	}

	public void exit() {
		recorder.exit();
	}
}
