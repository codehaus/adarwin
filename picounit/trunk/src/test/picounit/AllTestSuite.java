package picounit;

import picounit.mocker.easymock.ProxyFactory;
import picounit.util.MethodUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class AllTestSuite {
	public static TestSuite suite() {
		TestSuite testSuite = new TestSuite("PicoUnit");

		TestSuite mockAroundSuite = new TestSuite("picounit.around.mock.MockAroundTest");
		mockAroundSuite.addTest(createSuite());
		
		testSuite.addTest(mockAroundSuite);
		testSuite.addTestSuite(MyTestCase.class);
		return testSuite;
	}
	
	public static Test createSuite() {
//		final Test test = create();
		
		return (Test) new ProxyFactory().create(Test.class, new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.err.println(method + "(" + asList(args) + ")");

				if (countTestCases.equals(method)) {
					return new Integer(1);
				}
				else if (toString.equals(method)) {
					return "something(picounit.around.mock.MockAroundTest)";
				}
				else if (run.equals(method)) {
					TestResult result = (TestResult) args[0];

					result.startTest((Test) proxy);
//					result.startTest(test);
//					result.endTest(test);
					result.endTest((Test) proxy);
				}
				return null;
			}

			private String asList(Object[] args) {
				return args == null ? "" : Arrays.asList(args).toString();
			}
		});
	}

	public static Test create() {
		return (Test) new ProxyFactory().create(Test.class, new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.err.println(method + "(" + asList(args) + ")");

				if (countTestCases.equals(method)) {
					return new Integer(1);
				}
				else if (toString.equals(method)) {
					return "picounit.Test";
				}
				else if (run.equals(method)) {
					TestResult result = (TestResult) args[0];
					
					result.startTest((Test) proxy);
					result.endTest((Test) proxy);
				}
				return null;
			}

			private String asList(Object[] args) {
				return args == null ? "" : Arrays.asList(args).toString();
			}
		});
	}

	private static final Method toString = new MethodUtil().getMethod("toString");
	private static final Method run = new MethodUtil().getMethod(Test.class, "run", TestResult.class);
	private static final Method countTestCases = new MethodUtil().getMethod(Test.class, "countTestCases");
	
	public static class MyTestCase extends TestCase {
		public MyTestCase() {
			new Throwable("()").printStackTrace();
		}

		public String getName() {
			new Throwable("getName").printStackTrace();

			return "something";
		}

		public void testSomething() {
		
		}
		
		public int countTestCases() {
			return 1;
		}
		
		public void run(TestResult result) {
			result.startTest(this);
			result.endTest(this);
		}
		
//
//		public Test testAt(int index) {
//			return super.testAt(index);
//		}
//		
//		public int testCount() {
//			return super.testCount();
//		}
//		
//		public Enumeration tests() {
//			return super.tests();
//		}	
	}
}

class MyTest implements Test {
	public int countTestCases() {
		return 1;
	}

	public void run(TestResult result) {
		result.startTest(this);
		result.endTest(this);
	}
}
