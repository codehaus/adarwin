package picounit.junit;


public interface Recorder {
	void record(Event event);

	void exit();
}
