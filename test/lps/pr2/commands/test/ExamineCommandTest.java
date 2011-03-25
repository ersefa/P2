package lps.pr2.commands.test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import junit.framework.TestCase;
import lps.pr2.Game;
import lps.pr2.commands.ExamineCommand;

import org.junit.Before;
import org.junit.Test;

public class ExamineCommandTest extends TestCase {
	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	@Before
	public void setUp() throws Exception {
	}


	/**
	 * Test method for {@link lps.pr2.commands.ExamineCommand#parse(String)}.
	 */
	@Test
	public void testParse() {
		ExamineCommand c = new ExamineCommand(new Game());
		assertFalse(
				"ERROR: Examine misspelled (exam) is parsed as a correct command",
				c.parse("exam"));
		assertTrue("ERROR: Examine is not parsed as a correct command",
				c.parse("Examine"));
		assertTrue("ERROR: X is not parsed as a correct command", c.parse("X"));
		assertTrue(
				"ERROR: Examine Item1 is not parsed as a correct command by a DropCommand",
				c.parse("Examine Item1"));
		assertEquals(
				"ERROR: itemName is not correctly configured after parsing Examine Item1",
				"Item1", c.getItemName());
		assertTrue(
				"ERROR: X Item1 is not parsed as a correct command by a DropCommand",
				c.parse("X Item1"));
		assertEquals(
				"ERROR: itemName is not correctly configured after parsing Examine Item1",
				"Item1", c.getItemName());
	}


	/**
	 * Test method for {@link lps.pr2.commands.ExamineCommand#execute()}.
	 */
	@Test
	public void testExecute() {
		Game gameTest = new Game();
		ExamineCommand c = new ExamineCommand(gameTest);
		OutputStream streamOut = new ByteArrayOutputStream();
		gameTest.init(System.in, streamOut);

		// 1. Show one item
		((ByteArrayOutputStream) streamOut).reset();
		gameTest.getInventory().addItem(
				gameTest.getCurrentRoom().pickItem("Ticket"));
		c.parse("Examine Ticket");
		c.execute();
		assertEquals("Error Examine.execute: only one item must be shown",
				streamOut.toString(),
				gameTest.getInventory().examineItem("Ticket") + LINE_SEPARATOR);

		// 2. Show all inventory
		((ByteArrayOutputStream) streamOut).reset();
		c.parse("Examine");
		c.execute();
		assertEquals("Error Examine.execute: all items must be shown",
				streamOut.toString(), gameTest.getInventory().showInventory()
						+ LINE_SEPARATOR);
	}


	/**
	 * Test method for {@link lps.pr2.commands.ExamineCommand#help()}.
	 */
	@Test
	public void testHelp() {
		ExamineCommand c = new ExamineCommand(new Game());
		assertEquals("ERROR: Examine.Help() output mismatch", c.help(),
				"  ( EXAMINE | X ) [ <item> ]");
	}


	/**
	 * Test method for {@link lps.pr2.commands.ExamineCommand#toString()}.
	 */
	@Test
	public void testToString() {
		ExamineCommand c = new ExamineCommand(new Game());
		c.parse("Examine");
		assertEquals("ERROR: Examine toString output mismatch", c.toString(),
				"EXAMINE");
	}
}
