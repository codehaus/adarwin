package picounit.around;

import picounit.impl.PicoResolver;
import picounit.runner.ResultListener;
import picounit.runner.ScopeImpl;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class AroundRunnerImpl implements AroundRunner {
	private final List arounds = new LinkedList();
	private final PicoResolver picoResolver;
	private final ResultListener resultListener;

	public AroundRunnerImpl(PicoResolver picoResolver, ResultListener resultListener) {
		this.picoResolver = picoResolver;
		this.resultListener = resultListener;
	}

	public void registryEvent(Class testClass) {
		if (isAround(testClass)) {
			arounds.add(testClass);
		}
	}

	public void before(Object object, Method method) {
		for (Iterator iterator = arounds.iterator(); iterator.hasNext(); ) {
			Class aroundClass = (Class) iterator.next();

			Around around = (Around) picoResolver.getComponent(aroundClass);

			resultListener.enter(new ScopeImpl(Around.class, around));

			around.before(object, method);
		}
	}

	public void after(Object object, java.lang.reflect.Method method) {
		for (Iterator iterator = reverse(arounds).iterator(); iterator.hasNext(); ) {
			Class aroundClass = (Class) iterator.next();
	
			Around around = (Around) picoResolver.getComponent(aroundClass);

			try {
				around.after(object, method);
				
				resultListener.exit();
			}
			catch (Throwable throwable) {
				resultListener.exit(throwable);	
			}
		}
	}

	public int hashCode() {
		return arounds.hashCode() ^ picoResolver.hashCode();
	}

	public boolean equals(Object object) {
		if (object == null || !object.getClass().equals(getClass())) {
			return false;
		}
		
		if (object == this) {
			return true;
		}
		
		AroundRunnerImpl other = (AroundRunnerImpl) object;

		return arounds.equals(other.arounds) &&
			picoResolver.equals(other.picoResolver);
	}

	private boolean isAround(Class testClass) {
		return Around.class.isAssignableFrom(testClass);
	}

	private List reverse(List list) {
		List copy = new LinkedList(list);

		Collections.reverse(copy);

		return copy;
	}
}
