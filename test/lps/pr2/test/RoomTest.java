package lps.pr2.test;

import junit.framework.TestCase;
import lps.pr2.Parser.Direction;
import lps.pr2.Item;
import lps.pr2.Room;

import org.junit.Before;
import org.junit.Test;

public class RoomTest extends TestCase {

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");
	private Room testRoom1, testRoom2, exitRoom;
	private Item item1, item2;

	@Before
	public void setUp() throws Exception {
		// Rooms
		testRoom1 = new Room("testRoom1", false);
		testRoom2 = new Room("testRoom2", false);
		exitRoom = new Room("exitRoom", true);

		// Items in Rooms
		item1 = new Item("Item1", "Item1 para pruebas", 10);
		item2 = new Item("Item2", "Item2 para pruebas", 20);
	}

	/**
	 * setRoom Test
	 */
	@Test
	public void testSetRoom() {
		testRoom1.setRoom(Direction.NORTH, testRoom2);
		assertEquals("Error setRoom: incorrect Room set",
				testRoom1.nextRoom(Direction.NORTH), testRoom2);
	}

	/**
	 * Test method for {@link lps.pr2.Room#isExit()}.
	 */
	@Test
	public void testIsExit() {
		assertTrue("Error isExit: exitRoom returns false", exitRoom.isExit());
		assertFalse("Error isExit: not exitRoom returns true",
				testRoom1.isExit());
	}

	/**
	 * isClosed Test
	 */
	@Test
	public void testIsClosed() {
		assertTrue("Error isClosed: not closed direction returns false",
				testRoom1.isClosed(Direction.NORTH));
		testRoom1.setRoom(Direction.NORTH, testRoom2);
		assertFalse("Error isClosed: closed direction returns false",
				testRoom1.isClosed(Direction.NORTH));
	}

	/**
	 * nextRoom Test
	 */
	@Test
	public void testNextRoom() {
		assertNull(
				"Error nextRoom: no room in that direction, must return null",
				testRoom1.nextRoom(Direction.NORTH));
		testRoom1.setRoom(Direction.NORTH, testRoom2);
		assertEquals("Error nextRoom: wrong room",
				testRoom1.nextRoom(Direction.NORTH), testRoom2);
	}

	/**
	 * Test method for {@link lps.pr2.Room#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(
				"Error toString: output string don´t match the expected one",
				testRoom1.toString(), "testRoom1");
	}

	/**
	 * Test method for {@link lps.pr2.Room#findItem(String)}.
	 */
	@Test
	public void testFindItem() {
		testRoom1.dropItem(item1);

		// 1. Error case (item not in the room inventory)
		assertNull(
				"Error findItem: item not in the inventory, must return null",
				testRoom1.findItem(item2.getName()));

		// 2. Non error case (item name is in the inventory)
		assertEquals(
				"Error findItem: item in the inventory, must return not null",
				testRoom1.findItem(item1.getName()), item1);

	}

	/**
	 * Test method for {@link lps.pr2.Room#pickItem(String)}.
	 */
	@Test
	public void testPickItem() {
		testRoom1.dropItem(item1);

		// 1. Error case (item not in the room inventory)
		assertNull("Error PickItem: item not in the room",
				testRoom1.pickItem((item2.getName())));

		// 2. Non error case (item name is in the inventory)
		assertEquals(
				"Error PickItem: item in the room, item must be picked correctly",
				testRoom1.pickItem(item1.getName()), item1);

		// 3. Remove Check
		assertEquals(
				"Error PickItem: item in the room, item must be picked correctly",
				testRoom1.showRoomInventory(), "The room is empty");
	}

	/**
	 * Test method for {@link lps.pr2.Room#dropItem(Item)}.
	 */
	@Test
	public void testDropItem() {
		testRoom1.dropItem(item1);

		// 1. Error case (Attempt to drop an item already in the inventory)
		assertFalse(
				"Error dropItem: item already in the room, item mustn`t be droped",
				testRoom1.dropItem(item1));

		// 2. Non error case (Attempt to drop a new item in the room)
		assertTrue(
				"Error PickItem: item not in the room, item must be droped correctly",
				testRoom1.dropItem(item2));

		// 3. Drop Check
		String test = "The room contains the following items:" + LINE_SEPARATOR
				+ item2.getName() + LINE_SEPARATOR + item1.getName();
		assertEquals(
				"Error PickItem: item not in the room, item must be droped correctly",
				testRoom1.showRoomInventory(), test);
	}

	/**
	 * Test method for {@link lps.pr2.Room#putItem(Item)}.
	 */
	@Test
	public void testPutItem() {
		testRoom1.putItem(item1);
		testRoom1.putItem(item2);
		String test = "The room contains the following items:" + LINE_SEPARATOR
				+ item2.getName() + LINE_SEPARATOR + item1.getName();

		assertEquals("Error putItem: items missing in the room",
				testRoom1.showRoomInventory(), test);
	}

	/**
	 * Test method for {@link lps.pr2.Room#showRoomInventory()}.
	 */
	@Test
	public void testShowRoomInventory() {
		// 1. Empty Room
		assertEquals("Error showInventoru: expected empty inventory message",
				testRoom1.showRoomInventory(), "The room is empty");

		// 2. Non empty Room
		testRoom1.dropItem(item1);
		testRoom1.dropItem(item2);
		String test = "The room contains the following items:" + LINE_SEPARATOR
				+ item2.getName() + LINE_SEPARATOR + item1.getName();

		assertEquals("Error showInventoru: missing items in the inventory",
				testRoom1.showRoomInventory(), test);
	}

}
