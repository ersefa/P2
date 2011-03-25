package lps.pr2.commands;

import java.util.Scanner;

import lps.pr2.Game;
import lps.pr2.Parser;

/**
 * HELP command is responsible for showing the help. It delegates in the Parser
 * in order to show the information help because it knows which are all the
 * commands that the game is capable for executing.
 * 
 * @see Parser
 */
public class HelpCommand extends Command {

	/**
	 * The parser in order to delegate it the task of creating the help message
	 */
	protected Parser _parser;

	/**
	 * Constructor for the Help Command. This constructor needs the parser for
	 * creating the help message.
	 * 
	 * @param theGame
	 *            for the superclass
	 * @param theParser
	 *            a Parser to delegate in when the help is selected
	 */
	public HelpCommand(Game theGame, Parser theParser) {
		super(theGame);
		_parser = theParser;
	}

	/**
	 * Executes the command. It delegates on the Parser in order to compose the
	 * help message and prints it.
	 * 
	 * @see Command#execute()
	 */
	@Override
	public void execute() {
		_game.print(_parser.getHelp());
	}

	/**
	 * Parses a "HELP | INFO | ABOUT" command
	 * 
	 * @return Helping information about this command.
	 * 
	 * @see Command#help()
	 */
	@Override
	public String help() {
		return "  HELP | INFO | ABOUT";
	}

	@Override
	public boolean parse(String line) {
		Scanner reader = new Scanner(line);
		if (reader.hasNext()) {
			String firstCommand = reader.next();
			if (firstCommand.equalsIgnoreCase("HELP")
					|| firstCommand.equalsIgnoreCase("INFO")
					|| firstCommand.equalsIgnoreCase("ABOUT"))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "HELP";
	}

}
