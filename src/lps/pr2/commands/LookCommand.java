package lps.pr2.commands;

import java.util.Scanner;

import lps.pr2.Game;

/**
 * The command responsible for inspecting a room
 */
public class LookCommand extends Command {

	/**
	 * Constructor for the look command. Similar to the superclass
	 * 
	 * @param theGame
	 *            for the superclass
	 */
	public LookCommand(Game theGame) {
		super(theGame);
	}

	/**
	 * Execute the command. Describes the current room where the user stands.
	 * 
	 * @see Command#execute()
	 */
	@Override
	public void execute() {
		_game.describeRoom();
	}

	@Override
	public String help() {
		return "  LOOK | L";
	}

	/**
	 * Parse a "LOOK | L" command
	 * 
	 * @see Command#parse(String)
	 */
	@Override
	public boolean parse(String line) {
		Scanner reader = new Scanner(line);
		if (reader.hasNext()) {
			String firstCommand = reader.next();
			if (firstCommand.equalsIgnoreCase("LOOK")
					|| firstCommand.equalsIgnoreCase("L"))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "LOOK";
	}

}
