package picounit.test;

import picounit.Runner;
import picounit.Suite;
import picounit.Test;
import picounit.Verify;

import java.util.LinkedList;
import java.util.List;

public class FixtureTest implements Test {
	public void testFixturesUnregisteredWhenCallStackPopped(Verify verify, Runner runner) {
		Wrapper wrapper = new Wrapper(); 

		runner.registerFixture(wrapper);
		runner.run(ContainingSuite.class);

		verify.equal("SecondSuite should not have access to fixture", null, wrapper.wrapped);
	}

	public static class Wrapper {
		public Object wrapped;
	}

	public static class ContainingSuite implements Suite {
		public void suite(Runner runner) {
			runner.run(FirstSuite.class);
			runner.run(SecondSuite.class);
		}
	}

	public static class FirstSuite implements Suite {
		public void suite(Runner runner) {
			runner.registerFixture(List.class, LinkedList.class);
		}
	}

	public static class SecondSuite implements Suite {
		public void suite(Wrapper wrapper, List list) {
			wrapper.wrapped = list;
		}
	}
}
