package picounit.mocker.easymock;

import org.easymock.MockControl;

import picounit.Mocker;
import picounit.Refinement;
import picounit.util.CollectionUtil;
import picounit.util.MethodUtil;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

import junit.framework.AssertionFailedError;

public class MockerImpl implements Mocker {
	private static class State {};
	private static final State record = new State();
	private static final State playback = new State();
	
	private final Collection mockControls = new HashSet();
	private MockControl lastMockControl = null;
	private State state = record;

	public Object mock(Class interfaceClass) {
		MockControl mockControl = MockControl.createStrictControl(interfaceClass);

		mockControls.add(mockControl);

		return wrapInvocationHandler(interfaceClass, mockControl); 
	}

	public Refinement expectAndReturn(Object ignore, Object returnValue) {
		if (lastMockControl == null) {
			throw new RuntimeException("Cannot set expected return without invoking mocked method");
		}

		lastMockControl.expectAndReturn(ignore, returnValue);

		Refinement refinement = new RefinementImpl(lastMockControl);

		lastMockControl = null;
		
		return refinement;
	}
	
	public Refinement expectAndReturn(boolean ignore, boolean returnValue) {
		if (lastMockControl == null) {
			throw new RuntimeException("Cannot set expected return without invoking mocked method");
		}

		lastMockControl.expectAndReturn(ignore, returnValue);
		
		Refinement refinement = new RefinementImpl(lastMockControl);

		lastMockControl = null;

		return refinement;
	}

	public Refinement ignoreReturn(Object ignore) {
		return expectAndReturn(ignore, null);
	}

	public void useArrayMatcher() {
		if (lastMockControl == null) {
			throw new RuntimeException("Cannot set expected return without invoking mocked method");
		}

		lastMockControl.setMatcher(MockControl.ARRAY_MATCHER);
	}

	public void replay() {
		if (state == playback) {
			return;
		}

		forEachMock(new MockOperation() {
			protected final void operate(MockControl mockControl) {
				mockControl.replay();
			}
		});
		
		state = playback;
	}

	public void verify() {
		if (state == record) {
			replay();
		}

		forEachMock(new MockOperation() {
			protected final void operate(MockControl mockControl) {
				mockControl.verify();
			}
		});
	}

	public void reset() {
		mockControls.clear();
		
		state = record;
	}

	public Object instanceOf(Class interfaceClass) {
		return createProxy(interfaceClass, new InstanceOfInvocationHandler(interfaceClass));
	}

	public Object stub(Class interfaceClass) {
		return createProxy(interfaceClass, new StubbingInvocationHandler(interfaceClass));
	}

	private Object createProxy(Class interfaceClass, InvocationHandler invocationHandler) {
		return new ProxyFactory().create(interfaceClass, invocationHandler);
	}

	private Object wrapInvocationHandler(final Class interfaceClass, final MockControl mockControl) {
		return createProxy(interfaceClass, new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				lastMockControl = mockControl;

				if (MethodUtil.equals.equals(method)) {
					return new Boolean(proxy == args[0]);
				}
				try {
					return method.invoke(mockControl.getMock(), args);
				}
				catch (InvocationTargetException e) {
					if (isJUnitAssertionFailedError(e.getTargetException())) {
						throw new AssertionFailedError(interfaceClass + e.getTargetException().getMessage());
					}
					else {
						throw e;
					}
				}
			}
		});
	}

	private void forEachMock(MockOperation mockOperation) {
		new CollectionUtil().forEach(mockControls, mockOperation);
	}

	private boolean isJUnitAssertionFailedError(Throwable targetException) {
		return targetException.getClass().getName().equals("junit.framework.AssertionFailedError");
	}
}
