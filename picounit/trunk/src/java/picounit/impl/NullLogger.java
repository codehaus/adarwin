package picounit.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class NullLogger implements Logger {
	public void info(String message) {
	}

	public void error(String message) {
	}

	public PrintStream printStream() {
		return new PrintStream(new OutputStream() {
			public void write(byte[] b, int off, int len) throws IOException {
			}

			public void write(byte[] b) throws IOException {
			}

			public void write(int b) throws IOException {
			}
		});
	}
}
