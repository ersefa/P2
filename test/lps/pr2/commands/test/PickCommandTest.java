package lps.pr2.commands.test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import junit.framework.TestCase;
import lps.pr2.Game;
import lps.pr2.Item;
import lps.pr2.commands.PickCommand;

import org.junit.Before;
import org.junit.Test;

public class PickCommandTest extends TestCase {
	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	@Before
	public void setUp() throws Exception {
	}


	/**
	 * Test method for {@link lps.pr2.commands.PickCommand#parse(String)}.
	 */
	@Test
	public void testParse() {
		PickCommand c = new PickCommand(new Game());
		assertFalse(
				"ERROR: pick misspelled (pik) is parsed as a correct command",
				c.parse("pik"));
		assertFalse(
				"ERROR: pick without parameters is parsed as a correct command",
				c.parse("Pick"));
		assertTrue("ERROR: Pick Item1 is not parsed as a correct command",
				c.parse("Pick Item1"));
		assertEquals(
				"ERROR: itemName is not correctly configured after parsing Pick Item1",
				"Item1", c.getItemName());
	}


	/**
	 * Test method for {@link lps.pr2.commands.PickCommand#execute()}.
	 */
	@Test
	public void testExecute() {
		Game gameTest = new Game();
		PickCommand c = new PickCommand(gameTest);
		OutputStream streamOut = new ByteArrayOutputStream();
		gameTest.init(System.in, streamOut);

		// 1. Error: item not in the room
		((ByteArrayOutputStream) streamOut).reset();
		c.parse("PICK mockTicket");
		c.execute();
		assertEquals("Error PickCommand.execute: item not in the room",
				streamOut.toString(), "Item " + c.getItemName()
						+ " was not found in the room" + LINE_SEPARATOR);

		// 2 Error: item already in player inventory
		((ByteArrayOutputStream) streamOut).reset();
		Item it = new Item("Ticket", "Ticket for test pourpose", 0);
		gameTest.getInventory().addItem(it);
		c.parse("Pick Ticket");
		c.execute();
		assertEquals(
				"Error DropCommand.execute: item already in player´s inventory",
				streamOut.toString(), "You have another " + c.getItemName()
						+ " in your inventory" + LINE_SEPARATOR);

		// 3 Non error
		((ByteArrayOutputStream) streamOut).reset();
		gameTest.getInventory().removeItem(it.getName());
		c.parse("Pick Ticket");
		c.execute();
		assertEquals("Error DropCommand.execute: item not dropped",
				streamOut.toString(), "You picked the item "
						+ c.getItemName()
						+ ", whose value is "
						+ gameTest.getInventory().removeItem(c.getItemName())
								.getValue() + LINE_SEPARATOR);
	}


	/**
	 * Test method for {@link lps.pr2.commands.PickCommand#help()}.
	 */
	@Test
	public void testHelp() {
		PickCommand c = new PickCommand(new Game());
		assertEquals("ERROR: Pick.Help() output mismatch", c.help(),
				"  PICK <item>");
	}


	/**
	 * Test method for {@link lps.pr2.commands.PickCommand#toString()}.
	 */
	@Test
	public void testToString() {
		PickCommand c = new PickCommand(new Game());
		c.parse("Pick Item1");
		assertEquals("ERROR: Examine toString output mismatch", c.toString(),
				"PICK " + c.getItemName());
	}
}
