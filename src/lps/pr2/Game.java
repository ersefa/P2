package lps.pr2;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.MessageFormat;

import lps.pr2.Parser.Direction;
import lps.pr2.commands.Command;

/**
 * This class is responsible for controlling the main loop of the interactive
 * adventure. It creates the rooms and the parser and runs the main loop until
 * the user has arrived at an exit room.
 */
public class Game {

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	private static final String WAYCLOSED = "The way is closed in direction ";
	private final String PROMPT = LINE_SEPARATOR + "> ";
	private final String ERROR_COMMAND = "What?";

	/**
	 * Room where the player stays
	 */
	protected Room _currentRoom;

	/**
	 * The parser responsible for interpreting the user instructions
	 */
	protected Parser _parser;

	/**
	 * The flag that decides when the game has finished.
	 */
	protected boolean _quitFlag = false;

	/**
	 * Last executed command
	 */
	protected Command _history;

	/**
	 * The player inventory
	 */
	protected Inventory _playerInventory;

	/**
	 * Output PrintStream
	 */
	private PrintStream ps;

	/**
	 * Game constructor (default)
	 */
	public Game() {
	}

	/**
	 * Initializes the game, creating the rooms and the parser
	 * 
	 * @param in
	 *            The inputstream where the user writes the commands
	 * @param out
	 *            The outputstream where the game prints the messages during the
	 *            game
	 * 
	 * @return true if the game was initialized without errors
	 */
	public boolean init(InputStream in, OutputStream out) {
		if (in != null && out != null) {
			_parser = new Parser(in, this);
			_playerInventory = new Inventory();
			ps = new PrintStream(out);
			initMap();
			return true;
		} else
			return false;
	}

	/**
	 * Main loop of the game
	 */
	public void runGame() {
		Command _command = null;
		while (!_quitFlag) {
			_history = _command;
			ps.print(PROMPT);
			_command = _parser.nextCommand();
			if (_command != null)
				_command.execute();
			else
				ps.println(ERROR_COMMAND);
		}
		this.endGame();
	}

	/**
	 * Prints the information when the game has finished
	 */
	protected void endGame() {
		String totalValue = MessageFormat.format("GAME OVER" + LINE_SEPARATOR
				+ "Your inventory contains items with a value of {0} points."
				+ LINE_SEPARATOR + "Thank you for playing, goodbye.",
				getInventory().computePoints());
		ps.println(totalValue);
	}

	/**
	 * Prints the description of the current room on the outputstream
	 */
	public void describeRoom() {
		ps.println(_currentRoom.toString());
		ps.println(_currentRoom.showRoomInventory());
	}

	/**
	 * Changes the current room according to the given direction.<br>
	 * <br>
	 * If there is a wall in this direction, then a warning message is printed.<br>
	 * <br>
	 * If we have changed the room, then the game prints the room description.
	 * 
	 * @param d
	 *            The direction we want to walk to.
	 * 
	 * @return true if we have arrived at the exit. False, otherwise.
	 */
	public boolean changeRoom(Direction d) {
		if (!_currentRoom.isClosed(d)) {
			_currentRoom = _currentRoom.nextRoom(d);
			describeRoom();
		} else
			ps.println(WAYCLOSED + d);
		return (_currentRoom.isExit());
	}

	/**
	 * Creates the rooms contained in the map, sets the initial room where the
	 * player is going to start the game and prints the room description.
	 * 
	 * @return true if there was no problems during the initialization.<br>
	 * <br>
	 *         False, otherwise.
	 */
	public boolean initMap() {
		_currentRoom = createRooms();
		if (_currentRoom != null) {
			describeRoom();
			return true;
		}
		return false;
	}

	/**
	 * Creates all the rooms contained in the map
	 * 
	 * @return The room where the player starts the game
	 */
	protected Room createRooms() {

		/* Definition of the rooms */
		Room mainEntrance, toilet, libraryEntrance, busStop, hall, corridor1, stairs, canteen, corridor2, classroom2, classroom5;

		mainEntrance = new Room(
				"MAIN ENTRANCE"
						+ LINE_SEPARATOR
						+ "You are at the School of Computer Science, at Complutense University of Madrid. "
						+ "After the first Java assignment you are determined to pass the second one. "
						+ "Although the assignment is almost finish, you don't want to take any risk: you are going to bribe the lecturers, Federico and Guillermo."
						+ LINE_SEPARATOR
						+ "As a student, you don't have too much money. But the building is full of valuable things that people loose."
						+ LINE_SEPARATOR
						+ "Find the items and return to the bus. At home, you can wrape them in a nice wrapping paper.",
				false);

		toilet = new Room(
				"TOILET"
						+ LINE_SEPARATOR
						+ "You drank too much water this morning. You should do a little pause here.",
				false);

		libraryEntrance = new Room(
				"LIBRARY ENTRANCE"
						+ LINE_SEPARATOR
						+ "This is the perfect place to find a gift for the lecturers... books.",
				false);

		busStop = new Room(
				"BUS STOP"
						+ LINE_SEPARATOR
						+ "The bus is coming... and your backpack is full (or not)."
						+ LINE_SEPARATOR
						+ "It is late, so you decide to go back home.", true);

		hall = new Room(
				"HALL"
						+ LINE_SEPARATOR
						+ "Everybody walks up and down in this place. It is perfect for finding interesting items!",
				false);

		corridor1 = new Room(
				"CORRIDOR"
						+ LINE_SEPARATOR
						+ "Shhh!! Be quiet!! The classrooms are closed. Do not disturb the other students.",
				false);

		stairs = new Room("STAIRS" + LINE_SEPARATOR
				+ "This is a quiet and lonely place. What could I find here?",
				false);

		canteen = new Room(
				"CANTEEN"
						+ LINE_SEPARATOR
						+ "You are hungry. You decide to eat something."
						+ LINE_SEPARATOR
						+ "Oh no, your friends are there again... and they are playing... AN ADVENTURE GAME??!!!"
						+ LINE_SEPARATOR
						+ "(5 hours later...) These games are amazing!! I couldn't imagine that I should kill the troll usinh the axe...",
				false);

		corridor2 = new Room(
				"MORE CORRIDOR"
						+ LINE_SEPARATOR
						+ "The current classes have finished. Everybody is running through the corridors to be ready for the next class.",
				false);

		classroom2 = new Room(
				"CLASSROOM 2"
						+ LINE_SEPARATOR
						+ "Oh my God! LPS!!! Federico is explaining the third assignment. Should I stay or should I go?",
				false);

		classroom5 = new Room(
				"CLASSROOM 5"
						+ LINE_SEPARATOR
						+ "LPS here??? Guillermo is explaining Java exceptions in the blackboard..."
						+ LINE_SEPARATOR
						+ "(Guillermo) You are late!! And you are disturbing the lecture. "
						+ "Please, sit down and I will continue with the explanation of the next assignment.",
				false);

		/* Connections between rooms */

		mainEntrance.setRoom(Direction.NORTH, libraryEntrance);
		mainEntrance.setRoom(Direction.SOUTH, busStop);
		mainEntrance.setRoom(Direction.WEST, hall);

		toilet.setRoom(Direction.EAST, corridor2);

		libraryEntrance.setRoom(Direction.SOUTH, mainEntrance);
		libraryEntrance.setRoom(Direction.WEST, corridor1);

		busStop.setRoom(Direction.NORTH, mainEntrance);

		hall.setRoom(Direction.NORTH, corridor1);
		hall.setRoom(Direction.SOUTH, stairs);
		hall.setRoom(Direction.EAST, mainEntrance);
		hall.setRoom(Direction.WEST, canteen);

		corridor1.setRoom(Direction.NORTH, classroom2);
		corridor1.setRoom(Direction.SOUTH, hall);
		corridor1.setRoom(Direction.WEST, corridor2);
		corridor1.setRoom(Direction.EAST, libraryEntrance);

		stairs.setRoom(Direction.NORTH, hall);

		canteen.setRoom(Direction.NORTH, corridor2);
		canteen.setRoom(Direction.EAST, hall);

		corridor2.setRoom(Direction.NORTH, classroom5);
		corridor2.setRoom(Direction.SOUTH, canteen);
		corridor2.setRoom(Direction.WEST, toilet);
		corridor2.setRoom(Direction.EAST, corridor1);

		classroom2.setRoom(Direction.SOUTH, corridor1);

		classroom5.setRoom(Direction.SOUTH, corridor2);

		/* Definition of items */

		Item book, bone1, bone2, ticket, dynamite, mug1, mug2, hat, underwear, fish, key1, key2, tshirt1, tshirt2, map;

		book = new Item(
				"Book",
				"\"Big Whoop: Unclaimed Bonanza Or Myth?\" This book describes the fate of the four men possessing the map pieces leading to Big Whoop; Rapp Scallion, Young Lindy, Rum Rogers, and Captain Marley.",
				39);

		bone1 = new Item("Bone", "The bone of Largo's ancestor.", 32);

		bone2 = new Item("Bone", "Your standard issue tibia.", 18);

		ticket = new Item("Ticket", "A ticket to see the Fettuccini Brothers.",
				26);

		dynamite = new Item("Dynamite", "For ages 3 and up.", 3);

		mug1 = new Item("Mug", "This is a grog-drinking mug, sans grog.", 6);

		mug2 = new Item(
				"Mug",
				"That's no ordinary Grog; twice the calories and twice the alcohol; the Grog equivalent to Red Bull.",
				36);

		hat = new Item("Hat", "An old pirate hat for 30 pieces of eight.", 50);

		underwear = new Item("Underwear", "Uuugghh...", 25);

		fish = new Item("Fish", "A herring.", 36);

		key1 = new Item("Key",
				"It's a giant cotton swab: the key for the Monkey Head", 3);

		key2 = new Item("Key", "A key. Exciting.", 39);

		tshirt1 = new Item("T-Shirt",
				"A shirt that says \"I beat the Swordmaster.\"", 35);

		tshirt2 = new Item(
				"T-Shirt",
				"I found the Treasure of Melee Island and all I got was this lousy T-Shirt!",
				24);

		map = new Item(
				"Map",
				"They look like dance instructions, but they lead you to treasure! Honest!",
				40);

		/* Location of items in rooms */

		mainEntrance.putItem(ticket);

		toilet.putItem(tshirt1);
		toilet.putItem(underwear);

		libraryEntrance.putItem(book);
		libraryEntrance.putItem(key2);

		corridor1.putItem(tshirt2);

		stairs.putItem(mug1);
		stairs.putItem(key1);

		canteen.putItem(fish);
		canteen.putItem(mug2);

		classroom2.putItem(bone2);
		classroom2.putItem(dynamite);

		classroom5.putItem(bone1);
		classroom5.putItem(map);
		classroom5.putItem(hat);

		return mainEntrance;
	}

	/**
	 * This method is employed by the commands that have to invoke the end of
	 * the game
	 */
	public void requestQuit() {
		_quitFlag = true;
	}

	/**
	 * Undoes the last command executed (if any). After that, it cleans the
	 * command history
	 */
	public void undo() {
		if (_history != null)
			_history.undo();
	}

	/**
	 * Returns the current room where the player stays
	 * 
	 * @return the current room
	 */
	public Room getCurrentRoom() {
		return _currentRoom;
	}

	/**
	 * Prints a message on the outputstream
	 * 
	 * @param message
	 *            The string that contains the information printed by the game
	 */
	public void print(String message) {
		ps.println(message);
	}

	/**
	 * Returns the player inventory
	 * 
	 * @return the player inventory
	 */
	public Inventory getInventory() {
		return _playerInventory;
	}
	
	/**
	 * Returns the _quitFlage status
	 * 
	 * @return _quitFlag
	 */
	public boolean hasQuit(){
		return _quitFlag;
	}
}
