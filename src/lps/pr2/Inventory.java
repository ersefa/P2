package lps.pr2;

import java.text.MessageFormat;
import java.util.Hashtable;

/**
 * An inventory stores the items the player picks during the game. The inventory
 * can be examined, presenting the names of the objects contained in it.
 * Additionally, we can examine a single object in the inventory.
 */
public class Inventory {

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	/**
	 * The collection that stores the items collected by the player
	 */
	protected Hashtable<String, Item> _items;

	/**
	 * Creates a new, empty inventory
	 */
	public Inventory() {
		_items = new Hashtable<String, Item>();
	}

	/**
	 * Adds a new item to the inventory, if there is no item with the same name
	 * 
	 * @param newItem
	 *            The item that can be stored in the inventory
	 * 
	 * @return <li>true, if the item was correctly stored in the inventory</li>
	 *         <li>false, if there is another item with the same name in the
	 *         inventory. In this case, the newItem is not stored in the
	 *         inventory.</li>
	 */
	public boolean addItem(Item newItem) {
		if (!_items.containsKey(newItem.getName())) {
			_items.put(newItem.getName(), newItem);
			return true;
		} else
			return false;
	}

	/**
	 * Provides information about an item contained in the inventory, identified
	 * by its name
	 * 
	 * @param name
	 *            Name of the item to be examined
	 * 
	 * @return The item description, or a warning message, if the item was not
	 *         found in the inventory
	 */
	public String examineItem(String name) {
		if (_items.containsKey(name))
			return _items.get(name).getDesc();
		else {
			String NOTFOUND_MSG = MessageFormat.format(
					"Item {0} was not found in the inventory", name);
			return NOTFOUND_MSG;
		}
	}

	/**
	 * Removes the item identified by the name from the inventory and returns
	 * the removed item
	 * 
	 * @param name
	 *            Name of the item to be removed
	 * 
	 * @return The removed item, or null, if there is not any item with this
	 *         name in the inventory
	 */
	public Item removeItem(String name) {
		if (_items.containsKey(name)) {
			Item it = _items.get(name);
			_items.remove(name);
			return it;
		} else
			return null;
	}

	/**
	 * Provides information about which items the inventory stores
	 * 
	 * @return A string with information about the items contained in the
	 *         inventory
	 */
	public String showInventory() {
		if (!_items.isEmpty()) {
			String list = "The inventory contains the following items:";
			for (Item it : _items.values())
				list += LINE_SEPARATOR + it.getName();
			return list;
		} else
			return "The inventory is empty.";
	}

	/**
	 * Computes the total number of points provided by the items stored in the
	 * inventory
	 * 
	 * @return The total number of points as the sum up of the item values
	 */
	public int computePoints() {
		int totalPoints = 0;
		for (Item it : _items.values()) {
			totalPoints += it.getValue();
		}
		return totalPoints;
	}
}
