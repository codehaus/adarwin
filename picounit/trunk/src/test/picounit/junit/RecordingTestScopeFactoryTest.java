package picounit.junit;

import picounit.Mocker;
import picounit.Test;
import picounit.TestInstance;

public class RecordingTestScopeFactoryTest implements Test {
	private RecordingTestScopeFactoryImpl recordingTestScopeFactory;

	// Mocks
	private Recorder recorder;

	public void mock(Recorder recordingScopeFactory) {
		this.recordingTestScopeFactory = new RecordingTestScopeFactoryImpl(recordingScopeFactory);

		this.recorder = recordingScopeFactory;
	}
	
	public void testRecordsMethodsRun(Mocker mocker) {
		recorder.record(new Event(Test.class, TestInstance.testOne));

		mocker.replay();

		recordingTestScopeFactory.runMethod(TestInstance.testOne);
	}
	
	public void testExit(Mocker mocker) {
		recorder.exit();

		mocker.replay();

		recordingTestScopeFactory.exit();
	}
}
