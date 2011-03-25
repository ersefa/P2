package lps.pr2.commands.test;

import junit.framework.TestCase;
import lps.pr2.Game;
import lps.pr2.commands.QuitCommand;

import org.junit.Before;
import org.junit.Test;

public class QuitCommandTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}


	/**
	 * Test method for {@link lps.pr2.commands.QuitCommand#parse(String)}.
	 */
	@Test
	public void testParse() {
		QuitCommand c = new QuitCommand(new Game());
		assertFalse(
				"ERROR: Quit misspelled (Qui) is parsed as a correct command",
				c.parse("Qui"));
		assertTrue("ERROR: Quit is not parsed as a correct command",
				c.parse("Quit"));
		assertTrue("ERROR: exit is not parsed as a correct command",
				c.parse("exit"));
		assertTrue("ERROR: Q is not parsed as a correct command", c.parse("Q"));
	}


	/**
	 * Test method for {@link lps.pr2.commands.QuitCommand#execute()}.
	 */
	@Test
	public void testExecute() {
		Game gameTest = new Game();
		gameTest.init(System.in, System.out);
		QuitCommand c = new QuitCommand(gameTest);

		c.parse("QUIT");
		c.execute();
		assertTrue("Error QuitCommand.execute: game still runs",
				gameTest.hasQuit());
	}


	/**
	 * Test method for {@link lps.pr2.commands.QuitCommand#help()}.
	 */
	@Test
	public void testHelp() {
		QuitCommand c = new QuitCommand(new Game());
		assertEquals("ERROR: Quit.Help() output mismatch", c.help(),
				"  QUIT | EXIT | Q");
	}


	/**
	 * Test method for {@link lps.pr2.commands.QuitCommand#toString()}.
	 */
	@Test
	public void testToString() {
		QuitCommand c = new QuitCommand(new Game());
		c.parse("Q");
		assertEquals("ERROR: Examine toString output mismatch", c.toString(),
				"QUIT");
	}
}
