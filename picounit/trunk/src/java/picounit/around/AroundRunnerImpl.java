package picounit.around;

import picounit.impl.PicoResolver;
import picounit.impl.ResultListener;
import picounit.impl.ScopeImpl;

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

	public void registryEvent(Class someClass) {
		if (isAround(someClass)) {
			arounds.add(someClass);
		}
	}

	public void before(Object object, Method method) {
		for (Iterator iterator = arounds.iterator(); iterator.hasNext(); ) {
			Around around = nextAround(iterator);

			resultListener.enter(new ScopeImpl(Around.class, around));

			around.before(object, method);
		}
	}

	public void after(Object object, java.lang.reflect.Method method) {
		for (Iterator iterator = reverse(arounds).iterator(); iterator.hasNext(); ) {
			Around around = nextAround(iterator);

			try {
				around.after(object, method);

				resultListener.exit();
			}
			catch (Throwable throwable) {
				resultListener.exit(throwable);	
			}
		}
	}

	private Around nextAround(Iterator iterator) {
		return (Around) picoResolver.getComponent((Class) iterator.next());
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
