import picounit.MainSuite;
import picounit.junit.SuiteGenerator;
import junit.framework.TestSuite;


public class PicoInJUnit {
	public static TestSuite suite() {
		return new SuiteGenerator().generate(MainSuite.class);
	}
}
