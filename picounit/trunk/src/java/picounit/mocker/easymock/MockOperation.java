package picounit.mocker.easymock;

import org.easymock.MockControl;

public abstract class MockOperation implements Operation {
	public final void operate(Object operand) {
		operate((MockControl) operand);
	}

	abstract protected void operate(MockControl mockControl);
}