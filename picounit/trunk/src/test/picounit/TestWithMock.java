package picounit;


public interface TestWithMock extends Test {
	void mock(Dependancy dependancy);
	
	void test();
}
