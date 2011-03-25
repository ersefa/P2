package lps.pr2.test;

import junit.framework.TestCase;
import lps.pr2.Inventory;
import lps.pr2.Item;

import org.junit.Before;
import org.junit.Test;

public class InventoryTest extends TestCase {

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	private Inventory testInventory;
	private Item item1, item2, item3, fakeItem;

	@Before
	public void setUp() throws Exception {
		testInventory = new Inventory();
		item1 = new Item("Item1", "Item1 para pruebas", 10);
		item2 = new Item("Item2", "Item2 para pruebas", 20);
		item3 = new Item("Item3", "Item3 para pruebas", 30);
		fakeItem = new Item("fakeItem", "Fake items for test purpouse", 0);

		testInventory.addItem(item1);
		testInventory.addItem(item2);
		testInventory.addItem(item3);
	}

	/**
	 * Test method for {@link lps.pr2.Inventory#addItem(Item)}.
	 */
	@Test
	public void testAddItem() {
		// 1. Error case (the inventory already have that item)
		assertFalse("ERROR in addItem: inventory already have that item",
				testInventory.addItem(item1));

		// 2. Non-error case
		assertTrue("ERROR in addItem: addItem not performed",
				testInventory.addItem(fakeItem));

		// 3. Add check
		assertEquals("ERROR in addItem: addItem not performed",
				testInventory.examineItem(fakeItem.getName()),
				fakeItem.getDesc());
	}

	/**
	 * Test method for {@link lps.pr2.Inventory#examineItem(String)}.
	 */
	@Test
	public void testExamineItem() {
		String test = item1.getDesc();
		assertEquals("ERROR in examineItem: Output don´t match", test,
				testInventory.examineItem(item1.getName()));
	}

	/**
	 * Test method for {@link lps.pr2.Inventory#removeItem(String)}.
	 */
	@Test
	public void testRemoveItem() {
		// 1. Error case (the inventory does not have that item)
		assertNull("ERROR in removeItem: inventory do not have that item",
				testInventory.removeItem(fakeItem.getName()));

		// 2. Non-error case
		assertEquals("ERROR in removeItem: remove not performed",
				testInventory.removeItem(item1.getName()), item1);

		// 3. Remove check
		String test = "The inventory contains the following items:"
				+ LINE_SEPARATOR + item3.getName() + LINE_SEPARATOR
				+ item2.getName();
		assertEquals("ERROR in removeItem: remove not performed",
				testInventory.showInventory(), test);
		assertEquals("ERROR in removeItem: remove not performed",
				testInventory.computePoints(), 50);
	}

	/**
	 * Test method for {@link lps.pr2.Inventory#showInventory()}.
	 */
	@Test
	public void testShowInventory() {
		String test = "The inventory contains the following items:"
				+ LINE_SEPARATOR + item3.getName() + LINE_SEPARATOR
				+ item2.getName() + LINE_SEPARATOR + item1.getName();
		assertEquals("ERROR in showInventory: Output don´t match", test,
				testInventory.showInventory());
	}

	/**
	 * Test method for {@link lps.pr2.Inventory#computePoints()}.
	 */
	@Test
	public void testComputePoints() {
		int totalPoints = testInventory.computePoints();
		assertEquals("ERROR in computePoints: totalPoints must be 60",
				totalPoints,
				item1.getValue() + item2.getValue() + item3.getValue());
	}

}
