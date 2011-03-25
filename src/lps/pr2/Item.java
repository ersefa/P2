package lps.pr2;

/**
 * This class represents the items that can appear in the rooms. An item has a
 * name, a description and a value. An item is contained in a room.<br>
 * <br>
 * When the player picks an item, this one is stored in the player inventory.<br>
 * <br>
 * When the player drops an item, this one appears in the current room.
 */
public class Item {

	/**
	 * Item description. The description is shown when the player examines the
	 * item
	 */
	protected String _desc;

	/**
	 * The item value
	 */
	protected int _value;

	/**
	 * The item name. It identifies the item
	 */
	protected String _name;

	/**
	 * Item constructor, specifying the name of the item, its description and
	 * its value
	 * 
	 * @param name
	 *            Item name
	 * @param desc
	 *            Item description
	 * @param value
	 *            Item value
	 */
	public Item(String name, String desc, int value) {
		_name = name;
		_desc = desc;
		_value = value;
	}

	/**
	 * Returns the item description
	 * 
	 * @return The item description
	 */
	public String getDesc() {
		return _desc;
	}

	/**
	 * Returns the item value
	 * 
	 * @return The item value
	 */
	public int getValue() {
		return _value;
	}

	/**
	 * Returns the item name
	 * 
	 * @return The item name
	 */
	public String getName() {
		return _name;
	}
}
