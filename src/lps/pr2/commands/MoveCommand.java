package lps.pr2.commands;

import java.util.Scanner;

import lps.pr2.Game;
import lps.pr2.Parser.Direction;

/**
 * The Movement command. It parses the input in order to move the player in a
 * given direction. This direction is stored in order to execute and undo this
 * command.
 * 
 * @see lps.pr2.Parser.Direction
 */
public class MoveCommand extends Command {

	/**
	 * The movement direction
	 */
	protected Direction _dir;

	/**
	 * MoveCommand Constructor. Similar to the superclass
	 * 
	 * @param theGame
	 *            for the superclass
	 */
	public MoveCommand(Game theGame) {
		super(theGame);
	}

	@Override
	public void execute() {
		if (_game.changeRoom(_dir))
			_game.requestQuit();
	}

	@Override
	public String help() {
		return "  (GO | MOVE) (NORTH| N | SOUTH | S | WEST | W | EAST | E)";
	}

	/**
	 * Parses a (GO | MOVE) (NORTH| N | SOUTH | S | WEST | W | EAST | E) command
	 * 
	 * @see Command#parse(String)
	 */
	@Override
	public boolean parse(String line) {
		Scanner reader = new Scanner(line);
		if (reader.hasNext()) {
			String firstCommand = reader.next();
			if ((firstCommand.equalsIgnoreCase("GO") || firstCommand
					.equalsIgnoreCase("MOVE")) && (reader.hasNext())) {
				String secondCommand = reader.next();
				if (secondCommand.equalsIgnoreCase("NORTH")
						|| secondCommand.equalsIgnoreCase("N")) {
					_dir = Direction.NORTH;
					return true;
				}
				if (secondCommand.equalsIgnoreCase("SOUTH")
						|| secondCommand.equalsIgnoreCase("S")) {
					_dir = Direction.SOUTH;
					return true;
				}
				if (secondCommand.equalsIgnoreCase("WEST")
						|| secondCommand.equalsIgnoreCase("W")) {
					_dir = Direction.WEST;
					return true;
				}
				if (secondCommand.equalsIgnoreCase("EAST")
						|| secondCommand.equalsIgnoreCase("E")) {
					_dir = Direction.EAST;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Undoes the go command. This command supposes that two rooms are connected
	 * in both directions (although it was false in our first adventure)
	 * 
	 * @see Command#undo()
	 */
	public void undo() {
		super.undo();
		_game.changeRoom(_dir.oppositeDirection());
	}

	@Override
	public String toString() {
		return "GO " + _dir;
	}

	/**
	 * Returns the direction given
	 * 
	 * @return the direction given
	  */
	public Direction getDirection() {
		return _dir;
	}

}
