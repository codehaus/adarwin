package picounit;

import picounit.impl.VerifyImpl;

import java.util.Arrays;

import junit.framework.AssertionFailedError;

public class VerifyTest implements Test {
	private Verify verify = new VerifyImpl();

	public void testFail() {
		try {
			verify.fail();
		}
		catch (AssertionFailedError assertionFailedError) {
			return;
		}

		throw new AssertionFailedError();
	}
	
	public void testFailWithMessage() {
	 	try {
	 		verify.fail("message");
	 	}
	 	catch (AssertionFailedError assertionFailedError) {
	 		if (!"message".equals(assertionFailedError.getMessage())) {
	 			throw new AssertionFailedError();
	 		}
	 		return;
	 	}
	 	
	 	throw new AssertionFailedError();
	}

	public void testThat() {
		verify.that(true);

		try {
			verify.that(false);
		}
		catch (AssertionFailedError assertionFailedError) {
			expectMessage(assertionFailedError.getMessage(), "false", "true");
			return;
		}

		verify.fail();
	}

	public void testThatWithMessage() {
		verify.that("message", true);

		try {
			verify.that("message", false);
		}
		catch (AssertionFailedError assertionFailedError) {
			expectMessage("message", assertionFailedError.getMessage(), "false", "true");
			return;
		}

		verify.fail();
	}
	
	public void testNot() {
		verify.not(false);
		
		try {
			verify.not(true);
		}
		catch (AssertionFailedError assertionFailedError) {
			expectMessage(assertionFailedError.getMessage(), "false", "true");
			return;
		}
		
		verify.fail();
	}

	public void testNotWithMessage() {
		verify.not("message", false);

		try {
			verify.not("message", true);
		}
		catch (AssertionFailedError assertionFailedError) {
			//expectMessage("message", assertionFailedError.getMessage(), "false", "true");
			return;
		}

		verify.fail();
	}

	public void testBooleanEquals() {
	 	verify.equal(true, true);
	 	
	 	try {
	 		verify.equal(true, false);
	 	}
	 	catch (AssertionFailedError assertionFailedError) {
			expectMessage(assertionFailedError.getMessage(), "true", "false");
	 		return;
	 	}

	 	verify.fail();
	}
	
	public void testBooleanEqualWithMessage() {
	 	verify.equal("message", true, true);
	 	
	 	try {
	 		verify.equal("message", true, false);
	 	}
	 	catch (AssertionFailedError assertionFailedError) {
	 		expectMessage("message", assertionFailedError.getMessage(), "true", "false");
	 		return;
	 	}

	 	verify.fail();
	}
	
	public void testLongEquals() {
		verify.equal(1, 1);
		
		try {
			verify.equal(1, 2);
		}
		catch (AssertionFailedError assertionFailedError) {
			expectMessage(assertionFailedError.getMessage(), "1", "2");
			return;
		}
		
		verify.fail();
	}

	public void testLongEqualsWithMessage() {
		verify.equal("message", 1, 1);
		
		try {
			verify.equal("message", 1, 2);
		}
		catch (AssertionFailedError assertionFailedError) {
			expectMessage("message", assertionFailedError.getMessage(), "1", "2");
			return;
		}
		
		verify.fail();
	}

	public void testObjectEquals() {
		verify.equal("one", "one");
		
		try {
			verify.equal("one", "two");
		}
		catch (AssertionFailedError assertionFailedError) {
			expectMessage(assertionFailedError.getMessage(), "one", "two");
			return;
		}
		
		verify.fail();
	}

	public void testObjectEqualsWithMessage() {
		verify.equal("message", "one", "one");
		
		try {
			verify.equal("message","expected", "actual");
		}
		catch (AssertionFailedError assertionFailedError) {
			expectMessage("message", assertionFailedError.getMessage(), "expected", "actual");
			return;
		}
		
		verify.fail();
	}

	public void testArraysEquals() {
		verify.equal(new String[] {"one", "two"}, new String[] {"one", "two"});
		
		String[] expected = new String[] {"one", "two"};
		String[] actual = new String[] {"two", "one"};
		try {
			verify.equal(expected, actual);
		}
		catch (AssertionFailedError assertionFailedError) {
			expectMessage(assertionFailedError.getMessage(), toString(expected), toString(actual));
			return;
		}
		
		verify.fail();
	}

	public void testBooleanNotEquals() {
	 	verify.notEqual(true, false);
	 	
	 	try {
	 		verify.notEqual(true, true);
	 	}
	 	catch (AssertionFailedError assertionFailedError) {
	 		expectNotEqualMessage(assertionFailedError.getMessage(), "true");
	 		return;
	 	}

	 	verify.fail();
	}
	
	public void testBooleanNotEqualWithMessage() {
	 	verify.notEqual("message", true, false);
	 	
	 	try {
	 		verify.notEqual("message", true, true);
	 	}
	 	catch (AssertionFailedError assertionFailedError) {
	 		expectNotEqualMessage("message", assertionFailedError.getMessage(), "true");
			return;
	 	}

	 	verify.fail();
	}
	
	public void testLongNotEquals() {
		verify.notEqual(1, 2);
		
		try {
			verify.notEqual(1, 1);
		}
		catch (AssertionFailedError assertionFailedError) {
			expectNotEqualMessage(assertionFailedError.getMessage(), "1");
			return;
		}
		
		verify.fail();
	}

	public void testLongNotEqualsWithMessage() {
		verify.notEqual("message", 1, 2);
		
		try {
			verify.notEqual("message", 1, 1);
		}
		catch (AssertionFailedError assertionFailedError) {
			expectNotEqualMessage("message", assertionFailedError.getMessage(), "1");
			return;
		}
		
		verify.fail();
	}

	public void testObjectNotEquals() {
		verify.notEqual("one", "two");
		
		try {
			verify.notEqual("one", "one");
		}
		catch (AssertionFailedError assertionFailedError) {
			expectNotEqualMessage(assertionFailedError.getMessage(), "one");
			return;
		}

		verify.fail();
	}
	
	public void testObjectNotEqualsWithMessage() {
		verify.notEqual("message", "one", "two");
		
		try {
			verify.notEqual("message","one", "one");
		}
		catch (AssertionFailedError assertionFailedError) {
			expectNotEqualMessage("message", assertionFailedError.getMessage(), "one");
			return;
		}
		
		verify.fail();
	}

	private void expectMessage(String message, String expected, String actual) {
		expectMessage("", message, expected, actual);
	}

	private void expectMessage(String prefix, String message, String expected, String actual) {
		if (prefix.length() != 0) {
			prefix = prefix + ", ";
		}
		String expectedMessage = prefix + "expected: <" + expected + ">, but was: <" + actual + ">";
		String actualMessage = message;
		if (!expectedMessage.equals(actualMessage)) {
			verify.fail("expected: \"" + expectedMessage + "\" but was: " + actualMessage); 
		}
	}
	
	private void expectNotEqualMessage(String message, String expected) {
		expectNotEqualMessage("", message, expected);
	}
	
	private void expectNotEqualMessage(String prefix, String message, String expected) {
		if (prefix.length() != 0) {
			prefix = prefix + ", ";
		}
		String expectedMessage = prefix + "expected not equal: <" + expected + ">";
		String actualMessage = message;
		if (!expectedMessage.equals(actualMessage)) {
			verify.fail("expected: \"" + expectedMessage + "\" but was: " + actualMessage); 
		}
	}
	
	private String toString(String[] expected) {
		return Arrays.asList(expected).toString();
	}
}
