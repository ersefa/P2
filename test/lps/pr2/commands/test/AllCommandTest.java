package lps.pr2.commands.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllCommandTest extends TestCase {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllCommandTest.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(MoveCommandTest.class);
		suite.addTestSuite(ExamineCommandTest.class);
		suite.addTestSuite(LookCommandTest.class);
		suite.addTestSuite(DropCommandTest.class);
		suite.addTestSuite(HelpCommandTest.class);
		suite.addTestSuite(QuitCommandTest.class);
		suite.addTestSuite(PickCommandTest.class);
		suite.addTestSuite(UndoCommandTest.class);
		//$JUnit-END$
		return suite;
	}

}
