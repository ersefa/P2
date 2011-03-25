package lps.pr2.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;
import lps.pr2.Game;
import lps.pr2.Parser;
import lps.pr2.commands.Command;
import lps.pr2.commands.MoveCommand;

import org.junit.Before;
import org.junit.Test;

public class ParserTest extends TestCase {

	private Parser parserTest;
	private Game gameTest;
	private Command comTest;

	@Before
	public void setUp() throws Exception {
		gameTest = new Game();
		gameTest.init(System.in, System.out);
	}

	/**
	 * Test method for {@link lps.pr2.Parser#nextCommand()}.
	 */
	@Test
	public void testNextCommand() throws IOException {
		byte[] strToByte;
		InputStream ps;

		// 1. Error case (not a correct command in the input line);
		strToByte = "GO nr".getBytes("UTF-8");
		ps = new ByteArrayInputStream(strToByte);
		parserTest = new Parser(ps, gameTest);
		assertNull("Error nextCommand: wrong command parsed as good",
				parserTest.nextCommand());

		// 2. Non error case (correct command in the input line);
		strToByte = "GO north".getBytes("UTF-8");
		ps = new ByteArrayInputStream(strToByte);
		parserTest = new Parser(ps, gameTest);
		comTest = parserTest.nextCommand();
		assertEquals("Error nextCommand: right command not parsed",
				comTest.toString(), "GO NORTH");
		assertTrue("Error nextCommand: wrong instance of parsed Command",
				comTest instanceof MoveCommand);
	}

	/**
	 * Test method for {@link lps.pr2.Parser#getHelp()}.
	 */
	@Test
	public void testGetHelp() {
		parserTest = new Parser(System.in, gameTest);
		String expectedHelp = "You are lost. You are alone. You wander\r\nYour command words are:\r\n  HELP | INFO | ABOUT\r\n  QUIT | EXIT | Q\r\n  (GO | MOVE) (NORTH| N | SOUTH | S | WEST | W | EAST | E)\r\n  LOOK | L\r\n  UNDO\r\n  PICK <item>\r\n  DROP <item>\r\n  ( EXAMINE | X ) [ <item> ]";
		String testedHelp = parserTest.getHelp();
		assertEquals("Error getHelp: help test don´t match the expected one",
				expectedHelp, testedHelp);
	}
}
