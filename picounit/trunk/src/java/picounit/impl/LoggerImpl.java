package picounit.impl;

import java.io.PrintStream;

public class LoggerImpl implements Logger {
	public void info(String message) {
		System.out.print(message);
	}

	public void error(String message) {
		System.err.print(message);
	}

	public PrintStream printStream() {
		return System.err;
	}
}
