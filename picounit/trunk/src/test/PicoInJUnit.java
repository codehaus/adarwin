import picounit.MainRunner;
import picounit.MainSuite;
import picounit.junit.JUnitListener;
import junit.framework.TestSuite;


public class PicoInJUnit {
	public static TestSuite suite() {
		JUnitListener jUnitListener = new JUnitListener();

		MainRunner.create().run(MainSuite.class, jUnitListener);

		return jUnitListener.getTestSuite();
	}
}
