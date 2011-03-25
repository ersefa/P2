package lps.pr2;

import java.util.EnumMap;
import java.util.Hashtable;
import lps.pr2.Parser.Direction;

/**
 * This class represents a room in the adventure game. Every room has no more
 * than 4 doors and contains a textual description about itself.<br>
 * <br>
 * 
 * The rooms can also contain items that the player can pick and drop. Each item
 * have a unique name, that is, it is not allowed to be two different items with
 * the same name in a room.
 */
public class Room {

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	/**
	 * Room description
	 */
	protected String _desc;

	/**
	 * Adjacent rooms. It there will be a room in every direction (NORTH, EAST,
	 * SOUTH, WEST). If _doors.get(X) == null then there is not any adjacent
	 * room in direction X
	 */
	protected EnumMap<Direction, Room> _doors;

	/**
	 * Is this room an exit room?
	 */
	protected boolean _exitRoom;

	/**
	 * The items contained in the room
	 */
	protected Hashtable<String, Item> _items;

	/**
	 * Room constructor, specifying the room description and if the room is an
	 * exit room
	 * 
	 * @param desc
	 *            Room description
	 * @param exitRoom
	 *            Describes if this room is an exit room
	 */
	public Room(String desc, boolean exitRoom) {
		_desc = desc;
		_exitRoom = exitRoom;
		_items = new Hashtable<String, Item>();
		_doors = new EnumMap<Direction, Room>(Direction.class);
	}

	/**
	 * Establishes the adjacent room in a given direction
	 * 
	 * @param d
	 *            Door direction
	 * @param room
	 *            The adjacent room in the given direction
	 */
	public void setRoom(Direction d, Room room) {
		_doors.put(d, room);
	}

	/**
	 * Is it an exit room?
	 * 
	 * @return true if this room is an exit room
	 */
	public boolean isExit() {
		return _exitRoom;
	}

	/**
	 * Checks if there is any adjacent room in the given direction
	 * 
	 * @param d
	 *            The direction to check
	 * 
	 * @return <li>true if the door is closed (there is not any room) in the
	 *         given direction</li> <li>false, otherwise</li>
	 */
	public boolean isClosed(Direction d) {
		return (_doors.get(d) == null);
	}

	/**
	 * Returns the adjacent room connected in the given direction. If there is
	 * not any connected room, it returns null
	 * 
	 * @param d
	 *            The direction to check
	 * 
	 * @return The room connected with this one in the provided direction. If
	 *         the door in this direction is closed (it is not connected to any
	 *         room in this direction) it returns null
	 */
	public Room nextRoom(Direction d) {
		if (!isClosed(d))
			return _doors.get(d);
		else
			return null;
	}

	/**
	 * Returns the room description
	 * 
	 * @see Object#toString()
	 */
	public String toString() {
		return _desc;
	}

	/**
	 * Finds an item in the room. The item is identified by its name. If there
	 * is not any item with this name it returns null. This method returns a
	 * reference of the item but it does not remove the item from the room
	 * 
	 * @param name
	 *            Name of the item to be found
	 * 
	 * @return A reference to the item identified by the name. It returns null
	 *         if there is not any item identified with this name
	 */
	public Item findItem(String name) {
		if (_items.containsKey(name))
			return _items.get(name);
		else
			return null;
	}

	/**
	 * Picks an item identified by its name from the room. If there is not an
	 * item with this name it returns null. Otherwise, it returns the item and
	 * this one is removed from the room.
	 * 
	 * @param name
	 *            Name of the item to be picked
	 * 
	 * @return The item identified by the name. It returns null if there is not
	 *         any item identified by this name
	 */
	public Item pickItem(String name) {
		if (_items.containsKey(name)) {
			Item it = _items.get(name);
			_items.remove(name);
			return it;
		} else
			return null;
	}

	/**
	 * Drops an item in this room. We can drop an item if there is not any item
	 * with the same name in this room. If the operation is performed, the room
	 * will contain the dropped item
	 * 
	 * @param it
	 *            The item to be dropped
	 * 
	 * @return <li>True if we can drop the item (and the item appears in the
	 *         room)</li> <li>False, otherwise</li>
	 */
	public boolean dropItem(Item it) {
		if (!_items.containsKey(it.getName())) {
			_items.put(it.getName(), it);
			return true;
		} else
			return false;
	}

	/**
	 * Puts an item in a room. This method is only employed for creating the
	 * room. It does not check whether the room contains another object with the
	 * same name. If there is an item, the former is replaced with the new item.
	 * <br><br>
	 * <b>Do not use this method to drop an item</b>
	 * 
	 * @param it
	 *            The item that the room will store.
	 */
	public void putItem(Item it) {
		_items.put(it.getName(), it);
	}

	/**
	 * Returns a String with all Room items
	 * 
	 * @return All room items
	 */
	public String showRoomInventory() {
		if (!_items.isEmpty()) {
			String list = "The room contains the following items:";
			for (Item it : _items.values())
				list += LINE_SEPARATOR + it.getName();
			return list;
		}
		return "The room is empty";
	}
}
