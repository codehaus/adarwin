package picounit.junit;

import picounit.around.AroundRunner;
import picounit.impl.MethodInvoker;
import picounit.impl.PicoResolver;
import picounit.impl.Registry;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Test;

public class JUnitRunnerImpl implements JUnitRunner {
	private final MethodInvoker methodInvoker;
	private final JUnitListener junitListener;
	private final AroundRunner aroundRunner;
	private final PicoResolver picoResolver;
	private final Registry registry;
	private final List contextStack = new LinkedList();
	
	private static class Context {
		public final Object component;
		public final Method method;

		public Context(Object component, Method method) {
			this.component = component;
			this.method = method;
		}
	}

	public JUnitRunnerImpl(PicoResolver picoResolver, AroundRunner aroundRunner,
		MethodInvoker methodInvoker, JUnitListener junitListener, Registry registry) {

		this.picoResolver = picoResolver;
		this.aroundRunner = aroundRunner;
		this.methodInvoker = methodInvoker;
		this.junitListener = junitListener;
		this.registry = registry;
	}
	
	public void enterSuite(Method suiteMethod) {
		Object component = picoResolver.getComponent(suiteMethod.getDeclaringClass());

		aroundRunner.before(component, suiteMethod);

		contextStack.add(new Context(component, suiteMethod));
	}

	public void runTest(Method method, Test test, TestResultProxy testResultProxy) {
		junitListener.setContext(test, testResultProxy);
		registry.push();
		registry.registerFixture(method.getDeclaringClass());
		methodInvoker.invokeMethod(method, junitListener);
		registry.pop();
	}

	public void exitSuite() {
		Context context = (Context) contextStack.remove(contextStack.size() - 1);

		aroundRunner.after(context.component, context.method);
	}
}
