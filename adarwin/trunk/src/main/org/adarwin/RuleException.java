package org.adarwin;

public class RuleException extends Exception {
	public RuleException(String message) {
		super(message);
	}
	
	public RuleException(Throwable throwable) {
		super(throwable);
	}

	public RuleException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
