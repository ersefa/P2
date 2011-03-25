package lps.pr2.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import junit.framework.TestCase;
import lps.pr2.Game;
import lps.pr2.Inventory;
import lps.pr2.Item;
import lps.pr2.Parser.Direction;
import lps.pr2.Room;
import org.junit.Before;
import org.junit.Test;

public class GameTest extends TestCase {

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	private Game testGame;
	private Room testRoom1;
	private Inventory testInventory;
	private Item item1, item2, item3, roomItem1, roomItem2;

	@Before
	public void setUp() throws Exception {
		testGame = new Game();
		testRoom1 = new Room("testRoom1", false);
		testInventory = new Inventory();

		item1 = new Item("Item1", "Item1 para pruebas", 10);
		item2 = new Item("Item2", "Item2 para pruebas", 20);
		item3 = new Item("Item3", "Item3 para pruebas", 30);

		roomItem1 = new Item("roomItem1", "Room item1 para pruebas", 100);
		roomItem2 = new Item("roomItem2", "Room item2 para pruebas", 200);

		testInventory.addItem(item1);
		testInventory.addItem(item2);
		testInventory.addItem(item3);

		testRoom1.pickItem(roomItem1.getName());
		testRoom1.pickItem(roomItem2.getName());
	}

	/**
	 * Test method for
	 * {@link lps.pr2.Game#init(java.io.InputStream, OutputStream)}.
	 */
	@Test
	public void testInit() {
		// 1. Error case input/output stream = null
		assertFalse(testGame.init(null, System.out));
		assertFalse(testGame.init(System.in, null));

		// 2. Non error case
		assertTrue(testGame.init(System.in, System.out));

		// 3. Check initialization (Rooms created)
		assertEquals(
				testGame.getCurrentRoom().toString(),
				"MAIN ENTRANCE"
						+ LINE_SEPARATOR
						+ "You are at the School of Computer Science, at Complutense University of Madrid. "
						+ "After the first Java assignment you are determined to pass the second one. "
						+ "Although the assignment is almost finish, you don't want to take any risk: you are going to bribe the lecturers, Federico and Guillermo."
						+ LINE_SEPARATOR
						+ "As a student, you don't have too much money. But the building is full of valuable things that people loose."
						+ LINE_SEPARATOR
						+ "Find the items and return to the bus. At home, you can wrape them in a nice wrapping paper.");
	}

	/**
	 * Test method for {@link lps.pr2.Game#describeRoom()}.
	 */
	@Test
	public void testDescribeRoom() throws IOException {
		OutputStream streamOut = new ByteArrayOutputStream();
		testGame.init(System.in, streamOut);

		String expectedOut = testGame.getCurrentRoom().toString()
				+ LINE_SEPARATOR
				+ testGame.getCurrentRoom().showRoomInventory()
				+ LINE_SEPARATOR;

		// Limpio el stream para que sólo obtenga la salida de describeRoom
		((ByteArrayOutputStream) streamOut).reset();

		testGame.describeRoom();
		assertEquals(streamOut.toString(), expectedOut);
	}

	@Test
	public void testChangeRoom() {
		OutputStream streamOut = new ByteArrayOutputStream();
		testGame.init(System.in, streamOut);

		// 1. Case True: next Room is an exit.
		assertTrue("ERROR in ChangeRoom: next Room is an exit",
				testGame.changeRoom(Direction.SOUTH));
		// 2. Case False: next Room is not an exit.
		assertFalse("ERROR in ChangeRoom: next Room is not an exit",
				testGame.changeRoom(Direction.NORTH));

		// 3. next Room is a wall.
		((ByteArrayOutputStream) streamOut).reset();
		Direction dir = Direction.EAST;
		String expectedOut = "The way is closed in direction " + dir
				+ LINE_SEPARATOR;
		testGame.changeRoom(dir);
		assertEquals("ERROR in ChangeRoom: next Room is a wall",
				streamOut.toString(), expectedOut);

		// 4. next Room is not a wall.
		((ByteArrayOutputStream) streamOut).reset();
		dir = Direction.NORTH;
		testGame.changeRoom(dir);
		expectedOut = testGame.getCurrentRoom().toString() + LINE_SEPARATOR
				+ testGame.getCurrentRoom().showRoomInventory()
				+ LINE_SEPARATOR;
		assertEquals("ERROR in ChangeRoom: change not happens",
				streamOut.toString(), expectedOut);

	}

	@Test
	public void testUndo() {
	}

	/**
	 * Test method for {@link lps.pr2.Game#getCurrentRoom()}.
	 */
	@Test
	public void testGetCurrentRoom() {
		testGame.init(System.in, System.out);
		String expectedOut = "MAIN ENTRANCE"
				+ LINE_SEPARATOR
				+ "You are at the School of Computer Science, at Complutense University of Madrid. "
				+ "After the first Java assignment you are determined to pass the second one. "
				+ "Although the assignment is almost finish, you don't want to take any risk: you are going to bribe the lecturers, Federico and Guillermo."
				+ LINE_SEPARATOR
				+ "As a student, you don't have too much money. But the building is full of valuable things that people loose."
				+ LINE_SEPARATOR
				+ "Find the items and return to the bus. At home, you can wrape them in a nice wrapping paper.";
		assertEquals("ERROR in getCurrentRoom: expectedOutput don´t match",
				testGame.getCurrentRoom().toString(), expectedOut);
	}

	/**
	 * Test method for {@link lps.pr2.Game#print(String)}.
	 */
	@Test
	public void testPrint() throws IOException {
		OutputStream streamOut = new ByteArrayOutputStream();
		testGame.init(System.in, streamOut);
		String expectedOut = "Testing 123" + LINE_SEPARATOR;

		((ByteArrayOutputStream) streamOut).reset();
		testGame.print("Testing 123");
		assertEquals("ERROR in print: expectedOutput don´t match",
				streamOut.toString(), expectedOut);
	}

	/**
	 * Test method for {@link lps.pr2.Game#getInventory()}.
	 */
	@Test
	public void testGetInventory() {
		testGame.init(System.in, System.out);
		String expectedOut = "The inventory contains the following items:"
				+ LINE_SEPARATOR + "Ticket";
		testGame.getInventory().addItem(
				testGame.getCurrentRoom().pickItem("Ticket"));
		assertEquals(
				"ERROR in getInventory: inventory output don´t match the expected one",
				testGame.getInventory().showInventory(), expectedOut);
	}

}
