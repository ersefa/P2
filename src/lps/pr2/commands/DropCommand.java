/**
 * 
 */
package lps.pr2.commands;

import java.text.MessageFormat;
import java.util.Scanner;

import lps.pr2.Game;
import lps.pr2.Inventory;
import lps.pr2.Item;

/**
 * This class implements the DROP &lt;item&gt; command. When this command is
 * executed, an item in the player inventory is removed from it and dropped in
 * the current room.<br>
 * <br>
 * 
 * If there isn't any item in the player inventory with this name, the command
 * prints a warning message about that the item was not found<br>
 * <br>
 * 
 * If the room contains an item with the same name, the item cannot be dropped
 * and a warning message about this fact is displayed.
 */
public class DropCommand extends Command {

	/**
	 * A reference to the player inventory
	 */
	protected Inventory _inventory;

	/**
	 * The name of the item to be dropped
	 */
	protected String _itemName;

	/**
	 * Constructor for the Drop command
	 * 
	 * @param theGame
	 *            for the superclass
	 */
	public DropCommand(Game theGame) {
		super(theGame);
	}

	/**
	 * Removes an item from the inventory and drops it in the current room. The
	 * item name was provided by the user.<br>
	 * <br>
	 * 
	 * The command is performed only if the item is stored in the inventory or
	 * if the room does not store any item with the same name. Otherwise, a
	 * warning message is displayed.
	 * 
	 * @see Command#execute()
	 */
	@Override
	public void execute() {
		_inventory = _game.getInventory();
		Item it;
		// 1. Compruebo que tengo dicho objeto en el inventario
		if ((it = _inventory.removeItem(_itemName)) != null) {
			// 2. Compruebo que no existe un objeto igual en la habitación.
			if (_game.getCurrentRoom().dropItem(it))
				_game.print(MessageFormat.format(
						"Item {0} has been dropped in this room", _itemName));
			else {
				// removeItem Undo
				_inventory.addItem(it);
				_game.print(MessageFormat
						.format("There is another {0} in this room so you cannot drop it here.",
								_itemName));
			}
		} else
			_game.print(MessageFormat.format(
					"Item {0} was not found in the inventory", _itemName));
	}

	@Override
	public String help() {
		return "  DROP <item>";
	}

	/**
	 * Parses a "DROP &lt;item&gt;" command
	 * 
	 * @param line
	 *            A string with the information for configuring the command
	 * 
	 * @return <li>False, if this command does not know how to parse the line or
	 *         if the line contains mistakes and the command cannot be
	 *         configured correctly.</li> <li>True, otherwise. In this case, the
	 *         command is correctly configured and ready to be executed</li>
	 * 
	 * @see Command#parse(String)
	 */
	@Override
	public boolean parse(String line) {
		Scanner reader = new Scanner(line);
		if (reader.hasNext()) {
			String firstCommand = reader.next();
			if (firstCommand.equalsIgnoreCase("DROP") && reader.hasNext()) {
				_itemName = reader.next();
				return true;
			}
		}
		return false;
	}

	/**
	 * Undoes the drop action, that is, the item is picked from the room.
	 * 
	 * @see Command#undo()
	 */
	public void undo() {
		super.undo();
		_inventory.addItem(_game.getCurrentRoom().pickItem(_itemName));
	}

	@Override
	public String toString() {
		return "DROP " + _itemName;
	}

	/**
	 * Returns a String with the item name
	 * 
	 * @return the item name
	 */
	public String getItemName() {
		return _itemName;
	}

}
