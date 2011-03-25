package lps.pr2.commands;

import lps.pr2.Game;

/**
 * This class represents the QUIT command. It requests the game to finish the
 * main loop.
 */
public class QuitCommand extends Command {

	/**
	 * Constructor for the quit command. Similar to the superclass
	 * 
	 * @param theGame
	 *            for the superclass
	 */
	public QuitCommand(Game theGame) {
		super(theGame);
	}

	@Override
	public void execute() {
		_game.requestQuit();
	}

	@Override
	public String help() {
		return "  QUIT | EXIT | Q";
	}

	/**
	 * Parses a "QUIT | EXIT | Q" command
	 * 
	 * @see Command#parse(String)
	 */
	@Override
	public boolean parse(String line) {
		if (line.equalsIgnoreCase("QUIT") || line.equalsIgnoreCase("EXIT")
				|| line.equalsIgnoreCase("Q"))
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return "QUIT";
	}

}
