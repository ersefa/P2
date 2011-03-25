package lps.pr2.test;

import junit.framework.TestCase;
import lps.pr2.Item;

import org.junit.Before;
import org.junit.Test;

public class ItemTest extends TestCase {

	private Item item1;

	@Before
	public void setUp() throws Exception {
		item1 = new Item("Item1", "Item1 para pruebas", 10);
	}
	
	/**
	 * Test method for {@link lps.pr2.Item#Item(String, String, int)}.
	 */
	@Test
	public void testItem() {
		Item fakeItem = new Item("fakeItem", "Fake items for test purpouse", 0);
		assertEquals("Error Item Constructor: wrong description",
				fakeItem.getDesc(), "Fake items for test purpouse");
		assertEquals("Error Item Constructor: wrong value",
				fakeItem.getValue(), 0);
		assertEquals("Error Item Constructor: wrong name", fakeItem.getName(),
				"fakeItem");
	}

	/**
	 * Test method for {@link lps.pr2.Item#getDesc()}.
	 */
	@Test
	public void testGetDesc() {
		assertEquals("Error getDesc: descriptions don´t match",
				item1.getDesc(), "Item1 para pruebas");
	}

	/**
	 * Test method for {@link lps.pr2.Item#getValue()}.
	 */
	@Test
	public void testGetValue() {
		assertEquals("Error getValue: values don´t match", item1.getValue(), 10);
	}

	/**
	 * Test method for {@link lps.pr2.Item#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("Error getName: names don´t match", item1.getName(),
				"Item1");
	}

}
