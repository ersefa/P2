package lps.pr2.commands.test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

import junit.framework.TestCase;
import lps.pr2.Game;
import lps.pr2.commands.LookCommand;
import lps.pr2.commands.MoveCommand;
import lps.pr2.commands.UndoCommand;

import org.junit.Before;
import org.junit.Test;

public class UndoCommandTest extends TestCase {
	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link lps.pr2.commands.UndoCommand#parse(String)}.
	 */
	@Test
	public void testParse() {
		UndoCommand c = new UndoCommand(new Game());
		assertFalse(
				"ERROR: Undo misspelled (Und) is parsed as a correct command",
				c.parse("Und"));
		assertTrue("ERROR: Undo is not parsed as a correct command",
				c.parse("Undo"));
		assertTrue("ERROR: uNdO is not parsed as a correct command",
				c.parse("uNdO"));
	}


	/**
	 * Test method for {@link lps.pr2.commands.UndoCommand#execute()}.
	 */
	@Test
	public void testExecute() throws Exception {
		Game gameTest = new Game();
		UndoCommand u = new UndoCommand(gameTest);
		MoveCommand m = new MoveCommand(gameTest);
		LookCommand l = new LookCommand(gameTest);
		OutputStream streamOut = new ByteArrayOutputStream();
		gameTest.init(System.in, streamOut);

		String expectedOut = gameTest.getCurrentRoom().toString()
				+ LINE_SEPARATOR
				+ gameTest.getCurrentRoom().showRoomInventory()
				+ LINE_SEPARATOR;
		m.parse("GO NORTH");
		m.execute();

		/*
		 * Using Java Reflections "Feeling the power"
		 * 
		 * Needed to set protected _history with the last command. _history
		 * update only happens in runGame
		 */
		Field privateField = Game.class.getDeclaredField("_history");
		privateField.setAccessible(true);
		privateField.set(gameTest, m);

		/*
		 * Accessing private fields with reflection: Command fieldValue =
		 * (Command) privateField.get(gameTest);
		 * System.out.print(fieldValue.toString());
		 */

		u.parse("Undo");
		u.execute();
		((ByteArrayOutputStream) streamOut).reset();
		l.parse("LOOK");
		l.execute();
		assertEquals(
				"Error UndoCommand.execute: moveCommand must be undone, we are not in the right room",
				streamOut.toString(), expectedOut);
	}


	/**
	 * Test method for {@link lps.pr2.commands.UndoCommand#help()}.
	 */
	@Test
	public void testHelp() {
		UndoCommand c = new UndoCommand(new Game());
		assertEquals("ERROR: Undo.Help() output mismatch", c.help(), "  UNDO");
	}


	/**
	 * Test method for {@link lps.pr2.commands.UndoCommand#toString()}.
	 */
	@Test
	public void testToString() {
		UndoCommand c = new UndoCommand(new Game());
		c.parse("Undo");
		assertEquals("ERROR: Examine toString output mismatch", c.toString(),
				"UNDO");
	}
}
