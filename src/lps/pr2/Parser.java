package lps.pr2;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;
import lps.pr2.commands.*;

/**
 * Parser is in charge of parsing a inputstream in order to create a valid
 * command. Parsing is delegated in command objects.<br>
 * <br>
 * 
 * The parser stores a list with the prototypes of every understandable command
 * in the game. When the parser has to return the next command, it runs as
 * follows:<br>
 * <br>
 * 
 * <li>The parser looks for the command that understands the user order.</li>
 * <li>If the command was found, a clone of this command is returned. This
 * command clone is ready to be executed.</li>
 * 
 * The parser also provides the information help. It delegates, again, in the
 * command prototypes in order to compose the game help.
 * 
 * @see Command
 */
public class Parser {

	/**
	 * Enumeration that represents the cardinal directions: North, east, south
	 * and west.
	 */
	public enum Direction {
		NORTH {
			@Override
			public Direction oppositeDirection() {
				return SOUTH;
			}
		},
		EAST {
			@Override
			public Direction oppositeDirection() {
				return WEST;
			}
		},
		SOUTH {
			@Override
			public Direction oppositeDirection() {
				return NORTH;
			}
		},
		WEST {
			@Override
			public Direction oppositeDirection() {
				return EAST;
			}
		};

		/**
		 * It returns the opposite direction of a given one
		 * 
		 * @return The opposite direction
		 */
		public abstract Parser.Direction oppositeDirection();

	}

	/**
	 * Platform-independent line separator
	 */
	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	/**
	 * A reference to the game that created the Interpreter
	 */
	protected Game _game;

	/**
	 * Contains the prototypes of every command that the player can use during
	 * the game
	 */
	protected Vector<Command> _commandPrototypes;

	private Scanner reader;

	/**
	 * Creates a parser, specifying the inputstream from which the game receives
	 * the user orders and the game itself. The latter is needed for configuring
	 * the commands
	 * 
	 * @param in
	 *            Input stream.
	 * @param theGame
	 *            A reference to the game.
	 */
	public Parser(InputStream in, Game theGame) {
		reader = new Scanner(in);
		_game = theGame;
		_commandPrototypes = new Vector<Command>();

		_commandPrototypes.add(new HelpCommand(_game, this));
		_commandPrototypes.add(new QuitCommand(_game));
		_commandPrototypes.add(new MoveCommand(_game));
		_commandPrototypes.add(new LookCommand(_game));
		_commandPrototypes.add(new UndoCommand(_game));
		_commandPrototypes.add(new PickCommand(_game));
		_commandPrototypes.add(new DropCommand(_game));
		_commandPrototypes.add(new ExamineCommand(_game));
	}

	/**
	 * Parses the next command in the input stream and returns the parsed
	 * command
	 * 
	 * @return <li>The parsed command if we have found a correct command.</li>
	 *         <li>null if the input does not contain a correct command.</li>
	 */
	public Command nextCommand() {
		String line = reader.nextLine();
		for (Command com : _commandPrototypes) {
			if (com.parse(line))
				try {
					return (Command) com.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

	/**
	 * Creates a string with information about all the available commands
	 * 
	 * @return The information about available commands
	 */
	public String getHelp() {
		String help = "You are lost. You are alone. You wander"
				+ LINE_SEPARATOR + "Your command words are:";
		for (Command com : _commandPrototypes) {
			help += LINE_SEPARATOR + com.help();
		}
		return help;
	}
}
