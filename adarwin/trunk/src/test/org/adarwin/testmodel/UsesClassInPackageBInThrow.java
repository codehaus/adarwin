package org.adarwin.testmodel;

public class UsesClassInPackageBInThrow {
	public void naughty() {
		throw new org.adarwin.testmodel.b.ExceptionInPackageB();
	}
}
