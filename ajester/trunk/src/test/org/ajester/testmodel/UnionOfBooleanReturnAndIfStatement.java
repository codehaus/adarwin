package org.ajester.testmodel;

import org.ajester.CodeLocation;

public class UnionOfBooleanReturnAndIfStatement {
	public static final CodeLocation GET_TRUE_LOCATION =
		new CodeLocation(UnionOfBooleanReturnAndIfStatement.class, "getTrue");
	
	public static final CodeLocation IF_EQUAL_LOCATION =
		new CodeLocation(UnionOfBooleanReturnAndIfStatement.class, "ifEqual");
	
	public static final CodeLocation IF_NOT_EQUAL_LOCATION =
		new CodeLocation(UnionOfBooleanReturnAndIfStatement.class, "ifNotEqual");
	
	// Exactly the same as BooleanReturn
	public boolean getTrue() {
		return true;
	}

	// Exactly the same as IfStatement
	private boolean bTrue = true;

	public boolean ifEqual() {
		if (bTrue) {
			return true;
		}
		return false;
	}

	public boolean ifNotEqual() {
		if (!bTrue) {
			return false;
		}
		return true;
	}
}
