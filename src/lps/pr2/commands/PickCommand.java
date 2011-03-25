package lps.pr2.commands;

import java.text.MessageFormat;
import java.util.Scanner;

import lps.pr2.Game;
import lps.pr2.Inventory;
import lps.pr2.Item;

/**
 * This class implements the PICK &lt;item&gt; command. When this command is executed,
 * an item in the room is picked and stored in the player inventory.<br>
 * <br>
 * 
 * If there isn't any item in the room with this name the command prints a
 * warning message about that the item was not found.<br>
 * <br>
 * 
 * If the player inventory contains an item with the same name, the item cannot
 * be picked and a warning message about this fact is displayed
 */
public class PickCommand extends Command {

	/**
	 * A reference to the player inventory
	 */
	protected Inventory _inventory;

	/**
	 * The name of the item to be picked
	 */
	protected String _itemName;

	/**
	 * Constructor for the Pick command
	 * 
	 * @param theGame
	 *            for the superclass
	 */
	public PickCommand(Game theGame) {
		super(theGame);
	}

	/**
	 * Executes the command, picking the item identified by its name that lays
	 * in the room and storing it in the inventory.<br>
	 * <br>
	 * 
	 * However, if the room does not store any item with this name or the
	 * inventory contains an item with the same name, then the action is not
	 * executed and a warning message is displayed.
	 * 
	 * @see Command#execute()
	 */
	@Override
	public void execute() {
		_inventory = _game.getInventory();
		Item it;
		// 1. Compruebo que la habitación contiene el objeto
		if ((it = _game.getCurrentRoom().pickItem(_itemName)) != null) {
			// 2. Compruebo que no tengo repetido dicho objeto en mi inventario
			if (_inventory.addItem(it))
				_game.print(MessageFormat.format(
						"You picked the item {0}, whose value is {1}",
						_itemName, it.getValue()));
			else {
				_game.getCurrentRoom().dropItem(it);
				_game.print(MessageFormat
						.format("You have another {0} in your inventory",
								_itemName));	
			}
		} else
			_game.print(MessageFormat.format(
					"Item {0} was not found in the room", _itemName));
	}

	@Override
	public String help() {
		return "  PICK <item>";
	}

	/**
	 * Parses a "PICK &lt;item&gt;" command
	 * 
	 * @see Command#parse(String)
	 */
	@Override
	public boolean parse(String line) {
		Scanner reader = new Scanner(line);
		if (reader.hasNext()) {
			String firstCommand = reader.next();
			if (firstCommand.equalsIgnoreCase("PICK") && reader.hasNext()) {
				_itemName = reader.next();
				return true;
			}
		}
		return false;
	}

	public void undo() {
		super.undo();
		_game.getCurrentRoom().dropItem(_inventory.removeItem(_itemName));
	}

	@Override
	public String toString() {
		return "PICK " + _itemName;
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
