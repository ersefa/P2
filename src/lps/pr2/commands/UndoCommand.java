package lps.pr2.commands;

import lps.pr2.Game;

/**
 * This class implements the UNDO command. This command is responsible for
 * requesting the game to undo the last executed command. The game only has a
 * reference to the last executed command.
 */
public class UndoCommand extends Command {

	/**
	 * UndoCommand constructor. Similar to its superclass
	 * 
	 * @param theGame
	 *            for the superclass
	 */
	public UndoCommand(Game theGame) {
		super(theGame);
	}

	/**
	 * Executes the command, requesting the game to undo the action contained in
	 * the history.
	 * 
	 * @see Command#execute()
	 */
	@Override
	public void execute() {
		_game.undo();
	}

	@Override
	public String help() {
		return "  UNDO";
	}

	/**
	 * Parses a "UNDO" command
	 * 
	 * @see Command#parse(String)
	 */
	@Override
	public boolean parse(String line) {
		if (line.equalsIgnoreCase("UNDO"))
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return "UNDO";
	}

}
