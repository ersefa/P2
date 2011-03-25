package lps.pr2.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import lps.pr2.commands.test.AllCommandTest;

public class AllTests extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(ParserTest.class);
		suite.addTestSuite(RoomTest.class);
		suite.addTestSuite(GameTest.class);
		suite.addTestSuite(InventoryTest.class);
		suite.addTestSuite(ItemTest.class);

		// Command TESTS
		suite.addTest(AllCommandTest.suite());
		// $JUnit-END$
		return suite;
	}

}
