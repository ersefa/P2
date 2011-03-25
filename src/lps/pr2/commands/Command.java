package lps.pr2.commands;

import java.text.MessageFormat;

import lps.pr2.Game;

/**
 * Command defines the interface that every command in the game has. Every
 * command knows how to parse a string in order to create a correct command, so
 * it is also responsible to print its own help.<br><br>
 * 
 * This abstract class follows the Command pattern. It includes methods for
 * executing a command and undoing it.<br><br>
 * 
 * Additionally, this class implements the Prototype pattern. The commands are
 * employed by the Parser class as prototypes of the commands that the user can
 * employ during the game. The clone method needed to implement the prototype is
 * implemented using the Cloneable interface.
 */
public abstract class Command implements Cloneable {

	/**
	 * A reference to a game object. It will be useful for accessing the room
	 * where the player stays, for manipulating the player inventory and for
	 * printing messages.
	 */
	protected Game _game;

	/**
	 * A constructor that initializes the reference to the game.
	 * 
	 * @param theGame
	 *            A reference to a Game object
	 */
	public Command(Game theGame) {
		_game = theGame;
	}

	/**
	 * Tries to configure the command with the information contained in a
	 * string.
	 * 
	 * @param line
	 *            A string with the information for configuring the command
	 * 
	 * @return <li>False, if this command does not know how to parse the line or
	 *         if the line contains mistakes and the command cannot be
	 *         configured correctly.</li><li>True, otherwise. In this case,
	 *         the command is correctly configured and ready to be executed</li>
	 */
	public abstract boolean parse(String line);

	/**
	 * Executes the command.
	 */
	public abstract void execute();

	/**
	 * Undoes the command.
	 */
	public void undo() {
		_game.print(MessageFormat.format("Undoing {0}", this.toString()));
	}

	/**
	 * Creates a string with the information about the command. This information
	 * will be employed for printing the game help.
	 * 
	 * @return Helping information about this command.
	 */
	public abstract String help();

	/**
	 * Implements the toString method (it would be useful to show information
	 * about the command)
	 * 
	 * @see Object#toString()
	 */
	public abstract String toString();

	/**
	 * Returns a clone of this command. It is needed to implement the Prototype
	 * pattern
	 * 
	 * @see Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException {
		return (Command) super.clone();
	}
	

}
