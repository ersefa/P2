package lps.pr2.commands.test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import junit.framework.TestCase;
import lps.pr2.Game;
import lps.pr2.Parser;
import lps.pr2.commands.HelpCommand;

import org.junit.Before;
import org.junit.Test;

public class HelpCommandTest extends TestCase {
	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	@Before
	public void setUp() throws Exception {
	}


	/**
	 * Test method for {@link lps.pr2.commands.HelpCommand#parse(String)}.
	 */
	@Test
	public void testParse() {
		Game gameTest = new Game();
		HelpCommand c = new HelpCommand(gameTest, new Parser(System.in,
				gameTest));
		assertFalse(
				"ERROR: wrong help spell (hlp) is parsed as a correct command",
				c.parse("hlp"));
		assertTrue("ERROR: Help is not parsed as a correct command",
				c.parse("Help"));
		assertTrue("ERROR: INFO is not parsed as a correct command",
				c.parse("INFO"));
		assertTrue("ERROR: AbOut is not parsed as a correct command",
				c.parse("AbOut"));
	}


	/**
	 * Test method for {@link lps.pr2.commands.HelpCommand#execute()}.
	 */
	@Test
	public void testExecute() {
		Game gameTest = new Game();
		Parser parserTest = new Parser(System.in, gameTest);
		HelpCommand c = new HelpCommand(gameTest, parserTest);
		OutputStream streamOut = new ByteArrayOutputStream();
		gameTest.init(System.in, streamOut);

		((ByteArrayOutputStream) streamOut).reset();
		c.parse("HELP");
		c.execute();
		assertEquals(
				"ERROR HelpCommand.execute: help don´t match the expected one",
				streamOut.toString(), parserTest.getHelp() + LINE_SEPARATOR);
	}


	/**
	 * Test method for {@link lps.pr2.commands.HelpCommand#help()}.
	 */
	@Test
	public void testHelp() {
		Game gameTest = new Game();
		HelpCommand c = new HelpCommand(gameTest, new Parser(System.in,
				gameTest));
		assertEquals("ERROR: Help.help() output mismatch", c.help(),
				"  HELP | INFO | ABOUT");
	}


	/**
	 * Test method for {@link lps.pr2.commands.HelpCommand#toString()}.
	 */
	@Test
	public void testToString() {
		Game gameTest = new Game();
		HelpCommand c = new HelpCommand(gameTest, new Parser(System.in,
				gameTest));
		c.parse("info");
		assertEquals("ERROR: Help toString output mismatch", c.toString(),
				"HELP");
	}
}
