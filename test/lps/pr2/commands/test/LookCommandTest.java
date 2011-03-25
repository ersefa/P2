package lps.pr2.commands.test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import junit.framework.TestCase;
import lps.pr2.Game;
import lps.pr2.commands.LookCommand;

import org.junit.Before;
import org.junit.Test;

public class LookCommandTest extends TestCase {
	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	@Before
	public void setUp() throws Exception {
	}


	/**
	 * Test method for {@link lps.pr2.commands.LookCommand#parse(String)}.
	 */
	@Test
	public void testParse() {
		LookCommand c = new LookCommand(new Game());
		assertFalse(
				"ERROR: Look misspelled (loock) is parsed as a correct command",
				c.parse("loock"));
		assertTrue("ERROR: Look is not parsed as a correct command",
				c.parse("Look"));
		assertTrue("ERROR: L is not parsed as a correct command", c.parse("L"));
	}


	/**
	 * Test method for {@link lps.pr2.commands.LookCommand#execute()}.
	 */
	@Test
	public void testExecute() {
		Game gameTest = new Game();
		LookCommand c = new LookCommand(gameTest);
		OutputStream streamOut = new ByteArrayOutputStream();
		gameTest.init(System.in, streamOut);

		((ByteArrayOutputStream) streamOut).reset();
		c.parse("LOOK");
		c.execute();
		assertEquals(
				"ERROR LookCommand.execute: room description don´t match the expected one",
				streamOut.toString(), gameTest.getCurrentRoom().toString()
						+ LINE_SEPARATOR
						+ gameTest.getCurrentRoom().showRoomInventory()
						+ LINE_SEPARATOR);
	}


	/**
	 * Test method for {@link lps.pr2.commands.LookCommand#help()}.
	 */
	@Test
	public void testHelp() {
		LookCommand c = new LookCommand(new Game());
		assertEquals("ERROR: Look.Help() output mismatch", c.help(),
				"  LOOK | L");
	}


	/**
	 * Test method for {@link lps.pr2.commands.LookCommand#toString()}.
	 */
	@Test
	public void testToString() {
		LookCommand c = new LookCommand(new Game());
		c.parse("L");
		assertEquals("ERROR: Examine toString output mismatch", c.toString(),
				"LOOK");
	}
}
