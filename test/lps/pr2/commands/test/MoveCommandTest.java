package lps.pr2.commands.test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import junit.framework.TestCase;
import lps.pr2.Game;
import lps.pr2.commands.MoveCommand;

import org.junit.Before;
import org.junit.Test;

public class MoveCommandTest extends TestCase {
	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	@Before
	public void setUp() throws Exception {
	}


	/**
	 * Test method for {@link lps.pr2.commands.MoveCommand#parse(String)}.
	 */
	@Test
	public void testParse() {
		MoveCommand c = new MoveCommand(new Game());
		assertFalse(
				"ERROR: GO without parameters is parsed as a correct command",
				c.parse("GO"));
		assertFalse(
				"ERROR: Move misspelled (mov) is parsed as a correct command",
				c.parse("mov"));
		assertTrue("ERROR: GO NORTH is not parsed as a correct command",
				c.parse("GO NORTH"));
		assertEquals(
				"ERROR: Direction is not correctly configured after parsing GO NORTH",
				"NORTH", c.getDirection().toString());
		assertTrue("ERROR: MOVE SOUTH is not parsed as a correct command",
				c.parse("MOVE SOUTH"));
		assertEquals(
				"ERROR: Direction is not correctly configured after parsing MOVE SOUTH",
				"SOUTH", c.getDirection().toString());
		assertTrue("ERROR: GO n is not parsed as a correct command",
				c.parse("GO n"));
		assertEquals(
				"ERROR: Direction is not correctly configured after parsing GO NORTH",
				"NORTH", c.getDirection().toString());
		assertTrue("ERROR: MOVE S is not parsed as a correct command",
				c.parse("MOVE S"));
		assertEquals(
				"ERROR: Direction is not correctly configured after parsing MOVE SOUTH",
				"SOUTH", c.getDirection().toString());
	}


	/**
	 * Test method for {@link lps.pr2.commands.MoveCommand#execute()}.
	 */
	@Test
	public void testExecute() {
		Game gameTest = new Game();
		MoveCommand c = new MoveCommand(gameTest);
		OutputStream streamOut = new ByteArrayOutputStream();
		gameTest.init(System.in, streamOut);

		// 1. Next room = exitRoom case
		((ByteArrayOutputStream) streamOut).reset();
		c.parse("GO SOUTH");
		c.execute();
		assertTrue(
				"ERROR MoveCommand.execute: exitRoom reached but game still runs, bad move made?",
				gameTest.hasQuit());

		// 2. Next room != exitRoom case
		((ByteArrayOutputStream) streamOut).reset();
		c.parse("GO NORTH");
		c.execute();
		assertEquals("ERROR MoveCommand.execute: wrong room reached",
				streamOut.toString(), gameTest.getCurrentRoom().toString()
						+ LINE_SEPARATOR
						+ gameTest.getCurrentRoom().showRoomInventory()
						+ LINE_SEPARATOR);

	}


	/**
	 * Test method for {@link lps.pr2.commands.MoveCommand#help()}.
	 */
	@Test
	public void testHelp() {
		MoveCommand c = new MoveCommand(new Game());
		assertEquals("ERROR: Move.Help() output mismatch", c.help(),
				"  (GO | MOVE) (NORTH| N | SOUTH | S | WEST | W | EAST | E)");
	}


	/**
	 * Test method for {@link lps.pr2.commands.MoveCommand#toString()}.
	 */
	@Test
	public void testToString() {
		MoveCommand c = new MoveCommand(new Game());
		c.parse("Go North");
		assertEquals("ERROR: Examine toString output mismatch", c.toString(),
				"GO " + c.getDirection().toString());
	}
}
