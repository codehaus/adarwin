package picounit.impl;

import java.io.PrintStream;

public interface Logger {
	void info(String message);

	void error(String message);

	PrintStream printStream();
}
