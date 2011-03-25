package lps.pr2.commands;

import java.util.Scanner;

import lps.pr2.Game;
import lps.pr2.Inventory;

/**
 * This class represents the EXAMINE command. This command is employed to
 * examine the items stored in the inventory or the whole inventory.<br>
 * <br>
 * 
 * When the user types EXAMINE, the game shows the names of every item stored in
 * the inventory.<br>
 * <br>
 * 
 * When the user types EXAMINE <name>, the game shows the description of the
 * item with this name stored in the inventory. If there is not any item with
 * this name it prints a warning message about that the item was not found in
 * the inventory.
 */
public class ExamineCommand extends Command {

	/**
	 * The name of the item to be examined
	 */
	protected String _itemName;

	/**
	 * A flag that decides if examining the whole inventory or only an item
	 */
	protected boolean _examineInventory;

	/**
	 * A reference to the player inventory
	 */
	protected Inventory _inventory;

	/**
	 * Constructor for the Examine command
	 * 
	 * @param theGame
	 *            for the superclass
	 */
	public ExamineCommand(Game theGame) {
		super(theGame);
	}

	/**
	 * Executes the command. If the command was EXAMINE, then it prints the
	 * names of the items stored in the inventory.<br>
	 * <br>
	 * 
	 * If the command was EXAMINE &lt;name&gt;, then it looks for this item in
	 * the inventory and prints its description. If the item was not found, it
	 * prints a warning message.
	 * 
	 * @see Command#execute()
	 */
	@Override
	public void execute() {
		_inventory = _game.getInventory();
		if (_examineInventory)
			_game.print(_inventory.showInventory());
		else
			_game.print(_inventory.examineItem(_itemName));
	}

	@Override
	public String help() {
		return "  ( EXAMINE | X ) [ <item> ]";
	}

	/**
	 * Parses a "( EXAMINE | X ) [ &lt;item&gt; ]" command
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
			if (firstCommand.equalsIgnoreCase("EXAMINE")
					|| firstCommand.equalsIgnoreCase("X")) {
				_examineInventory = true;
				if (reader.hasNext()) {
					_examineInventory = false;
					_itemName = reader.next();
				}
			} else
				return false;
		} else
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "EXAMINE";
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
