package picounit.mocker.easymock;

import org.easymock.MockControl;

import picounit.Refinement;

public class RefinementImpl implements Refinement {
	private final MockControl mockControl;

	public RefinementImpl(MockControl mockControl) {
		this.mockControl = mockControl;
	}

	public void useArrayMatcher() {
		mockControl.setMatcher(MockControl.ARRAY_MATCHER);
	}
}
