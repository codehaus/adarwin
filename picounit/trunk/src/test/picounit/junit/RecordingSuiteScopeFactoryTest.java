package picounit.junit;

import picounit.Mocker;
import picounit.Suite;
import picounit.Test;
import picounit.suite.OrdinarySuiteScopeFactory;
import picounit.suite.SuiteInstance;

public class RecordingSuiteScopeFactoryTest implements Test {
	private RecordingSuiteScopeFactory recordingSuiteScopeFactory;

	// Mocks
	private OrdinarySuiteScopeFactory ordinarySuiteScopeFactory;
	private Recorder recorder;

	public void mock(OrdinarySuiteScopeFactory ordinarySuiteScopeFactory,
		Recorder recordingScopeFactory) {

		this.recordingSuiteScopeFactory =
			new RecordingSuiteScopeFactoryImpl(ordinarySuiteScopeFactory, recordingScopeFactory);

		this.recorder = recordingScopeFactory;
		this.ordinarySuiteScopeFactory = ordinarySuiteScopeFactory;
	}
	
	public void testEnterClass(Mocker mocker) {
		ordinarySuiteScopeFactory.enterClass(SuiteInstance.class);
		
		mocker.replay();
		
		recordingSuiteScopeFactory.enterClass(SuiteInstance.class);
	}

	public void testRecordsMethodsRun(Mocker mocker) {
		recorder.record(new Event(Suite.class, SuiteInstance.suiteOne));
		ordinarySuiteScopeFactory.runMethod(SuiteInstance.suiteOne);
		
		mocker.replay();
		
		recordingSuiteScopeFactory.runMethod(SuiteInstance.suiteOne);
	}

	public void testExit(Mocker mocker) {
		recorder.exit();
		ordinarySuiteScopeFactory.exit();
		
		mocker.replay();
		
		recordingSuiteScopeFactory.exit();
	}
}
