package lps.pr2.commands.test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import junit.framework.TestCase;
import lps.pr2.Game;
import lps.pr2.Item;
import lps.pr2.commands.DropCommand;

import org.junit.Before;
import org.junit.Test;

public class DropCommandTest extends TestCase {
	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link lps.pr2.commands.DropCommand#parse(String)}.
	 */
	@Test
	public void testParse() {
		DropCommand c = new DropCommand(new Game());
		assertFalse(
				"ERROR: drop without parameters is parsed as a correct command by a DropCommand",
				c.parse("Drop"));
		assertTrue(
				"ERROR: DROP Item1 is not parsed as a correct command by a DropCommand",
				c.parse("DROP Item1"));
		assertEquals(
				"ERROR: DropCommand itemName is not correctly configured after parsing Drop Item1",
				"Item1", c.getItemName());
	}


	/**
	 * Test method for {@link lps.pr2.commands.DropCommand#execute()}.
	 */
	@Test
	public void testExecute() {
		Game gameTest = new Game();
		DropCommand c = new DropCommand(gameTest);
		OutputStream streamOut = new ByteArrayOutputStream();
		gameTest.init(System.in, streamOut);

		// 1. Error: item already in the room
		((ByteArrayOutputStream) streamOut).reset();
		Item it = new Item("Ticket", "Ticket for test pourpose", 0);
		gameTest.getInventory().addItem(it);
		c.parse("DROP Ticket");
		c.execute();
		assertEquals("Error DropCommand.execute: item already in the room", streamOut.toString(),
				"There is another " + c.getItemName()
						+ " in this room so you cannot drop it here."
						+ LINE_SEPARATOR);

		// 2 Error: item not in player inventory
		((ByteArrayOutputStream) streamOut).reset();
		c.parse("DROP mockItem");
		c.execute();
		assertEquals("Error DropCommand.execute: item not in player inventory",
				streamOut.toString(), "Item " + c.getItemName()
						+ " was not found in the inventory" + LINE_SEPARATOR);

		// 3 Non error
		((ByteArrayOutputStream) streamOut).reset();
		gameTest.getCurrentRoom().pickItem("Ticket");
		c.parse("DROP Ticket");
		c.execute();
		assertEquals("Error DropCommand.execute: item can´t be dropped", streamOut.toString(),
				"Item " + c.getItemName() + " has been dropped in this room"
						+ LINE_SEPARATOR);
	}


	/**
	 * Test method for {@link lps.pr2.commands.DropCommand#help()}.
	 */
	@Test
	public void testHelp() {
		DropCommand c = new DropCommand(new Game());
		assertEquals("ERROR Drop.Help(): output mismatch", c.help(),
				"  DROP <item>");
	}


	/**
	 * Test method for {@link lps.pr2.commands.DropCommand#toString()}.
	 */
	@Test
	public void testToString() {
		DropCommand c = new DropCommand(new Game());
		c.parse("DROP Item1");
		assertEquals("ERROR Drop toString: output mismatch", c.toString(),
				"DROP " + c.getItemName());
	}
}
