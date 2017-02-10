import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import javafx.scene.layout.Border;

import javax.swing.JTable;

/**
 *
 * Program: a Top Trumps game featuring a GUI and a database connection to store
 * data.
 * 
 * Class: GUI class for user interaction and for launching model-controller
 * classes.
 * 
 * @author Team A Faisal Ahsan 2242114a Aidan Butler 2281611b Stewart Brown
 *         2276998b Jane Kennedy 2287767k Svetoslava Nikolova 1004630n
 */
public class TopTrumpsGUI extends JFrame implements ActionListener {
	
	// Instance variables
	
	//----------Constants----------
	private final String DECK_FILE_NAME;
	private final int NUM_CARDS;
	private final String USER_NAME;
	private final String LINE_BREAK_STRING;

	//----------Model and Controller objects----------
	private Deck currentDeck;  		 // Deck consists of multiple cards
	private Game currentGame;  		 // Game consists of multiple rounds
	private Round currentRound;		 // Represents the current round
	private Player[] currentPlayers; // Array storing current players
	private Player decidingPlayer;
	private CommunalPile currentPile;

	// Database
	private TrumpsDBI DB;

	// Display variable
	private String prevRoundString;

	//----------View widgets----------	
	// Meta row Buttons
	private JButton newGameButton;
	private JButton showStatsButton;

	// Combo-box
	private JComboBox<String> numPlayersComboBox;

	// Play button label
	private JLabel playButtonLabel, communalPileLabel;

	// Play button text
	private String compChooseButText;

	// Play buttons
	private JButton playCompChooseButton;
	private JButton playAttr1Button;
	private JButton playAttr2Button;
	private JButton playAttr3Button;
	private JButton playAttr4Button;
	private JButton playAttr5Button;

	// Setting up the Players areas
	private JTextArea player1, player2, player3, player4, userAttributes;

	// Display area widgets
	private JTextArea outputTextArea;
	
	private StatsReport statsReport;

	
	// Constructor
	public TopTrumpsGUI() throws HeadlessException {

		//----------Constants----------
		/**
		 * Debugging note:
		 *
		 * Change the values of this.DECK_FILE_NAME and the corresponding value
		 * of this.NUM_CARDS to use a different deck.
		 */

		// Comment out filename and add another to use a different deck
		this.DECK_FILE_NAME = "deck.txt";

		// Comment out filename and add another to use a different deck
		this.NUM_CARDS = 40;
		this.USER_NAME = "You";
		this.LINE_BREAK_STRING = "-------------------------------------" 
		                       + "-------------------------------------"
				               + "--------------------------";

		// Previous round display initialised to an empty String
		this.prevRoundString = "";

		//----------Initialise Model/Controller objects----------
		// Initial window set-up
		this.setTitle("Top Trumps");
		this.setSize(850, 850);
		this.setLocation(300, 100);
		this.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		//----------Widget set-up----------
		// Meta buttons
		newGameButton = new JButton("New Game");
		showStatsButton = new JButton("Show Previous Game Stats");

		// Adding meta button action listeners
		newGameButton.addActionListener(this);
		showStatsButton.addActionListener(this);

		// Setup Combo box with allowable number of players
		String[] numPlayerOptions = { "2 Players", "3 Players", "4 Players", "5 Players" }; 
																							
		numPlayersComboBox = new JComboBox<String>(numPlayerOptions);

		// Button label
		playButtonLabel = new JLabel("");
		communalPileLabel = new JLabel("");

		// Button text
		compChooseButText = "Go!";

		// Play buttons
		playCompChooseButton = new JButton(compChooseButText);
		playAttr1Button = new JButton("");
		playAttr2Button = new JButton("");
		playAttr3Button = new JButton("");
		playAttr4Button = new JButton("");
		playAttr5Button = new JButton("");

		// Adding play button action listeners
		playCompChooseButton.addActionListener(this);
		playAttr1Button.addActionListener(this);
		playAttr2Button.addActionListener(this);
		playAttr3Button.addActionListener(this);
		playAttr4Button.addActionListener(this);
		playAttr5Button.addActionListener(this);

		//----------Output widgets----------
		// Setting up the players' text areas
		player1 = new JTextArea();
		player1.setBorder(playerBorder("WatsonBot"));
		player1.setBackground(new Color(255, 255, 255));
		player1.setText("\n");
		player1.setFont(new Font("monospaced", Font.PLAIN, 12));
		player1.setEditable(false);
		player1.setPreferredSize(new Dimension(140, 70));

		player2 = new JTextArea();
		player2.setBorder(playerBorder("Amiga64Bot"));
		player2.setBackground(new Color(255, 255, 255));
		player2.setText("\n");
		player2.setFont(new Font("monospaced", Font.PLAIN, 12));
		player2.setEditable(false);
		player2.setPreferredSize(new Dimension(140, 70));

		player3 = new JTextArea();
		player3.setBorder(playerBorder("BabbageBot"));
		player3.setBackground(new Color(255, 255, 255));
		player3.setText("\n");
		player3.setFont(new Font("monospaced", Font.PLAIN, 12));
		player3.setEditable(false);
		player3.setPreferredSize(new Dimension(140, 70));

		player4 = new JTextArea();
		player4.setBorder(playerBorder("TuringBot"));
		player4.setBackground(new Color(255, 255, 255));
		player4.setText("\n");
		player4.setFont(new Font("monospaced", Font.PLAIN, 12));
		player4.setEditable(false);
		player4.setPreferredSize(new Dimension(140, 70));

		userAttributes = new JTextArea();
		userAttributes.setBorder(playerBorder("You"));
		userAttributes.setBackground(new Color(255, 255, 255));
		userAttributes.setFont(new Font("monospaced", Font.PLAIN, 12));
		userAttributes.setPreferredSize(new Dimension(500, 80));

		//----------Panels----------
		// Creates a meta panel and adds widgets
		JPanel metaPan = new JPanel();
		metaPan.add(newGameButton);
		metaPan.add(numPlayersComboBox);
		metaPan.add(showStatsButton);
		metaPan.setBackground(new Color(204,204,255));

		// Creates a button panel and adds widgets
		JPanel labelPan = new JPanel();
		labelPan.add(playButtonLabel);
		labelPan.setBackground(new Color(204,204,255));

		JPanel goButtonPan = new JPanel();
		goButtonPan.add(playCompChooseButton);
		goButtonPan.setBackground(new Color(204,204,255));

		JPanel attributesPanel = new JPanel();
		attributesPanel.add(playAttr1Button);
		attributesPanel.add(playAttr2Button);
		attributesPanel.add(playAttr3Button);
		attributesPanel.add(playAttr4Button);
		attributesPanel.add(playAttr5Button);
		attributesPanel.setBackground(new Color(204,204,255));

		JPanel playerPanelTop = new JPanel();
		playerPanelTop.add(player2);
		playerPanelTop.add(player3);
		playerPanelTop.setBackground(new Color(204,204,255));

		JPanel playerPanelLeft = new JPanel();
		playerPanelLeft.add(player1);
		playerPanelLeft.setBackground(new Color(204,204,255));

		JPanel playerPanelRight = new JPanel();
		playerPanelRight.add(player4);
		playerPanelRight.setBackground(new Color(204,204,255));
		
		JPanel communalPile = new JPanel();
		communalPile.add(communalPileLabel);
		communalPile.setBackground(new Color(204,204,255));
		
		JPanel userPanel = new JPanel();
		userPanel.add(userAttributes);
		userPanel.setBackground(new Color(204,204,255));
		
		// Disable unnecessary buttons at initialisation
		playCompChooseButton.setEnabled(false);
		playAttr1Button.setEnabled(false);
		playAttr2Button.setEnabled(false);
		playAttr3Button.setEnabled(false);
		playAttr4Button.setEnabled(false);
		playAttr5Button.setEnabled(false);

		// Set-up text display
		outputTextArea = new JTextArea();
		outputTextArea.setText("");
		outputTextArea.setBorder(playerBorder("Previous Round Information"));
		outputTextArea.setPreferredSize(new Dimension(550, 90));
		outputTextArea.setFont(new Font("monospaced", Font.PLAIN, 12));
		outputTextArea.setEditable(false);


		// Creates input panel
		JPanel midPlayersPan = new JPanel(new GridLayout(1, 2));
		midPlayersPan.add(playerPanelLeft);
		midPlayersPan.add(playerPanelRight);
		midPlayersPan.setBackground(new Color(204,204,255));

		JPanel midPan = new JPanel(new GridLayout(2, 1));
		midPan.add(labelPan);
		midPan.add(goButtonPan);
		midPan.setBackground(new Color(204,204,255));

		JPanel bottomPan = new JPanel(new GridLayout(2, 1));
		bottomPan.add(communalPile);
		bottomPan.add(attributesPanel);
		bottomPan.setBackground(new Color(204,204,255));

		JPanel textAreaPan = new JPanel();
		textAreaPan.add(outputTextArea);
		textAreaPan.setBackground(new Color(204,204,255));
		
		JPanel gamePan = new JPanel(new GridLayout(8, 1));
		gamePan.setBackground(new Color(204,204,255));

		gamePan.add(metaPan);
		gamePan.add(playerPanelTop);
		gamePan.add(midPlayersPan);
		gamePan.add(midPan);
		gamePan.add(bottomPan);
		gamePan.add(userPanel);
		gamePan.add(textAreaPan);

		// Adds main panel to window
		add(gamePan);

		// Initialise Deck object
		generateDeck();
		
		// Initialise DB and StatsReport objects. Set latter to be invisible.
		this.DB = new TrumpsDBI();
		statsReport = new StatsReport();
		statsReport.setVisible(false);
		
		// Refresh screen
		this.repaint();
		this.revalidate();

	}

	/**
	 * a method to format the players textAreas
	 *
	 * @param playerName
	 * @return
	 */
	private TitledBorder playerBorder(String playerName) {
		TitledBorder newPlayerBorder;
		newPlayerBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), playerName);
		newPlayerBorder.setTitleJustification(TitledBorder.CENTER);
		return newPlayerBorder;
	}

	/**
	 * Generates a Deck of Cards by calling the readDeckInfoFromFile() method.
	 *
	 */
	private void generateDeck() {

		// Read deck info into a string array, line by line
		String[] deckLineStrings = readDeckInfoFromFile();

		// Extract name Strings from beginning of file
		String names = deckLineStrings[0];
		String[] namesStringArray = names.split(" +");
		String attri1Name = namesStringArray[1];
		String attri2Name = namesStringArray[2];
		String attri3Name = namesStringArray[3];
		String attri4Name = namesStringArray[4];
		String attri5Name = namesStringArray[5];

		Card[] CardArray = generateCardsArray(deckLineStrings);

		// Create deck. Shuffle when new game is launched
		this.currentDeck = new Deck(CardArray, attri1Name, attri2Name, attri3Name, attri4Name, attri5Name);

		// Calls function to print unshuffled deck to console for grading
		// and debugging purposes.
		printUnshuffledDeck();

		// Asigns correct names to attribute choice buttons
		playAttr1Button.setText(attri1Name);
		playAttr2Button.setText(attri2Name);
		playAttr3Button.setText(attri3Name);
		playAttr4Button.setText(attri4Name);
		playAttr5Button.setText(attri5Name);

	}

	/**
	 * Attempts to read deck information Strings from a text file, line by line
	 * and write them to an array; if unsuccessful, informs user with error
	 * message and performs a system exit.
	 *
	 * @return a String[], or null if unsuccessful
	 */
	private String[] readDeckInfoFromFile() {

		int arrayLength = NUM_CARDS + 1;
		String[] linesArray = new String[arrayLength];

		try {

			// Reading lines from file
			FileReader reader = new FileReader(this.DECK_FILE_NAME);
			Scanner in = new Scanner(reader);

			for (int i = 0; i < arrayLength; i++) {

				linesArray[i] = in.nextLine();
			}
			in.close();
		} catch (IOException e) {
			System.err.println("Exception: " + e.getMessage());
			JOptionPane.showMessageDialog(null, " Error reading from file: " + DECK_FILE_NAME + ".\n Error message: " 
											+ e.getMessage() + ". \n This programme will now exit automatically.\n " 
											+ "Please ensure a correctly named and formatted\n file is present, then " 
											+ "relauch the\n programme to procede.", "Error", JOptionPane.ERROR_MESSAGE);
			
			System.exit(-1); // System exit with error flag
		}
		return linesArray;
	}

	/**
	 * Generates an array of Cards from a given array of Strings.
	 *
	 * @param deckLineStrings,
	 *            card attribute information
	 * @return an array of Cards
	 */
	public Card[] generateCardsArray(String[] deckLineStrings) {

		Card[] CardsArray = new Card[deckLineStrings.length - 1];

		// Extract card attribute information from array
		// Start from 1 as first line contains name info
		for (int i = 1; i < deckLineStrings.length; i++) {

			// Extract data for new Card
			String numString = deckLineStrings[i];
			String[] numStringArray = numString.split(" +");
			String cardName = numStringArray[0];
			int attri1 = Integer.parseInt(numStringArray[1]);
			int attri2 = Integer.parseInt(numStringArray[2]);
			int attri3 = Integer.parseInt(numStringArray[3]);
			int attri4 = Integer.parseInt(numStringArray[4]);
			int attri5 = Integer.parseInt(numStringArray[5]);

			// Create new Card and add to Card array
			Card newCard = new Card(cardName, attri1, attri2, attri3, attri4, attri5);
			CardsArray[i - 1] = newCard;
		}
		return CardsArray;
	}

	/**
	 * Creates new Round, plays round, updates display text information and
	 * checks if game is over.
	 *
	 * @param trumpIndex,
	 *            the index of the trump for a given round: 1-5 if chosen by
	 *            user, 0 if computer choice.
	 */
	private void playRound(int trumpIndex) {
		
		Round CurrRound = new Round(currentPlayers, decidingPlayer, currentPile, 
					                trumpIndex, currentDeck, LINE_BREAK_STRING, 
					                NUM_CARDS);

		this.currentRound = CurrRound;		  // Assigned to instance variable
		this.currentRound.saveTrumpValues();  // Save previous Trump values
		this.currentRound.playRound();	      // Play a round within the game	
		currentPile = currentRound.getPile(); // Update cards in communal pile 
		resetDecidingPlayer();				  // Determine which player starts
		
		// Update GUI
		this.playButtonLabel.setText(generateCurrentTurnString());
		this.communalPileLabel.setText(this.currentRound.getCommunalPileString());
		this.prevRoundString = currentRound.getRoundString();
		this.generateAndSetDisplayText();
		setButtonsForNextRound();
		updateGameInfo();
		
		if (currentPlayers[0] != null) {
			userAttributes.setText("Cards left in hand: " + currentPlayers[0].getHandSize() 
					+ "\nCurrent card: " + generateCurrentCardString());
		} else {
			userAttributes.setText("Cards left in hand: 0");
		}

		setPlayersHandSize(); 				  // Update players' hand size post-round
		checkIfGameOver();
	}

	/**
	 * Checks to see if the game is over; updates game information and finishes
	 * game if it is.
	 */
	private void checkIfGameOver() {

		// Evaluates to true if the game is no longer in progress
		if (this.currentRound.boolUserWonGame() || this.currentRound.boolUserLostGame()) {
			setGameWinner();
			displayEndOfGamePopUp();
			finishGame();
		}
	}

	/**
	 * Displays a pop-up informing the user that the game is over, telling them
	 * the identity of the winner and asking them whether they wish to save the
	 * game data to database.
	 */
	private void displayEndOfGamePopUp() {

		String gameResult = "lost";

		if (this.currentRound.getGameWinner().getName().equals(this.USER_NAME)) {
			gameResult = "won";
		}

		int choice = JOptionPane.showConfirmDialog(null, "Game over, " + "you " + gameResult 
				   + "! \nDo you want to save the game " + "information?", "Game Over",
				     JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

		if (choice == JOptionPane.YES_OPTION) {
			saveGameDataToDB();
		}
	}

	/**
	 * Saves the current game data to the database.
	 */
	private void saveGameDataToDB() {

		this.DB.establishDbConnection();
		this.DB.addGameToDB(currentGame);
		this.DB.closeDbConnection();
	}

	/**
	 * Sets whether the winner of the current game was a human or the computer.
	 */
	private void setGameWinner() {

		if (this.currentRound.getGameWinner().getName().equals(this.USER_NAME)
				&& this.currentRound.getGameWinner() != null) {
			this.currentGame.setHumanWinner(true);
		} else {
			this.currentGame.setHumanWinner(false);
		}
	}

	/**
	 * If game is over, disables gameplay buttons and enables meta buttons.
	 */
	private void finishGame() {

		// Disable gameplay buttons
		playCompChooseButton.setEnabled(false);
		playAttr1Button.setEnabled(false);
		playAttr2Button.setEnabled(false);
		playAttr3Button.setEnabled(false);
		playAttr4Button.setEnabled(false);
		playAttr5Button.setEnabled(false);

		// Enable meta buttons and combo box
		newGameButton.setEnabled(true);
		showStatsButton.setEnabled(true);
		numPlayersComboBox.setEnabled(true);
	}

	/**
	 * Updates the current Game information to indicate whether the result of a
	 * game was a computer win, a human win or a draw and increments the number
	 * of rounds in the Game.
	 */
	private void updateGameInfo() {

		if (this.currentRound.isDraw()) {
			this.currentGame.incrementNumDraws();
		}
		
		// Call method in Game class to increment
		// number of rounds.
		this.currentGame.incrementNumRounds();
	}

	/**
	 * Updates the Game's deciding Player, in the case of a Round not ending in
	 * a draw.
	 */
	private void resetDecidingPlayer() {

		// If the current round is not a draw...
		if (!this.currentRound.isDraw()) {
			this.decidingPlayer = this.currentRound.getWinner();
		}
	}

	/**
	 * If the deciding Player is the user, enable the trump choice buttons,
	 * otherwise disables them and enables the computer choice button.
	 */
	private void setButtonsForNextRound() {

		if (this.decidingPlayer.getName().equals(this.USER_NAME)) {
			this.setButtonsForUserChoice();
		} else {
			this.setButtonsForComputerChoice();
		}
	}

	/**
	 * Instantiates Status Report object which queries a database to get data on
	 * previous games played, formats that data and displays it in a new window.
	 */
	public void displayStatsReport() {
		
		statsReport.buildReport(this.DB);
	}

	/**
	 * Carries out the corresponding actions when the user clicks a GUI button.
	 *
	 * @param e,
	 *            ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == newGameButton) {
			startNewGame();
		}
		if (e.getSource() == playCompChooseButton) {
			playRound(0);
		}
		if (e.getSource() == showStatsButton) {
			displayStatsReport();
		}
		if (e.getSource() == playAttr1Button) {
			playRound(1);
		}
		if (e.getSource() == playAttr2Button) {
			playRound(2);
		}
		if (e.getSource() == playAttr3Button) {
			playRound(3);
		}
		if (e.getSource() == playAttr4Button) {
			playRound(4);
		}
		if (e.getSource() == playAttr5Button) {
			playRound(5);
		}
	}

	/**
	 * Generates and returns a formatted String indicating which, if any, Cards
	 * the user has left.
	 *
	 * @return a String describing the User's hand of Cards
	 */
	private String generateCurrentCardString() {

		Player user = this.currentPlayers[0];

		if (user.getHandSize() == 0) {
			String s = String.format(this.USER_NAME + " have no cards left.\n\n");
			return s;
		}

		Card UserCurrentCard = user.viewTopCard();
		
		String CardDescription = String.format("%s%n", UserCurrentCard.getNameVal());
		
		String CardAttri1 = String.format("%s: %s   ", currentDeck.getAttriNameAtIndex(1),
				UserCurrentCard.getAttriValAtIndex(1));
		String CardAttri2 = String.format("%s: %s   ", currentDeck.getAttriNameAtIndex(2),
				UserCurrentCard.getAttriValAtIndex(2));
		String CardAttri3 = String.format("%s: %s   ", currentDeck.getAttriNameAtIndex(3),
				UserCurrentCard.getAttriValAtIndex(3));
		String CardAttri4 = String.format("%s: %s   ", currentDeck.getAttriNameAtIndex(4),
				UserCurrentCard.getAttriValAtIndex(4));
		String CardAttri5 = String.format("%s: %s   %n%n", currentDeck.getAttriNameAtIndex(5),
				UserCurrentCard.getAttriValAtIndex(5));
		
		String UserCardInfo = CardDescription + CardAttri1 + CardAttri2 
				            + CardAttri3 + CardAttri4 + CardAttri5;

		return UserCardInfo;
	}

	private String generateCurrentTurnString() {
		String WhoseTurn = String.format("Current player turn: %s%n%n", 
				           this.decidingPlayer.getName());
		return WhoseTurn;
	}

	/**
	 * Generates and sets the GUI output area to display information about the
	 * current and previous rounds of the game.
	 */
	private void generateAndSetDisplayText() {
		String displayText = this.prevRoundString;
		this.outputTextArea.setText(displayText);
	}

	/**
	 * Sets the buttons for a Round, disabling and enabling the user and
	 * computer choice buttons appropriately.
	 */
	private void setButtonsForRound() {

		if (this.decidingPlayer.getName().equals(this.USER_NAME)) {
			setButtonsForUserChoice();
		} else {
			setButtonsForComputerChoice();
		}
	}

	/**
	 * Sets gameplay buttons for around where a computer play chooses the trump.
	 */
	private void setButtonsForComputerChoice() {

		// Enable computer choice button
		this.playCompChooseButton.setEnabled(true);
		// Disable user choice buttons
		this.playAttr1Button.setEnabled(false);
		this.playAttr2Button.setEnabled(false);
		this.playAttr3Button.setEnabled(false);
		this.playAttr4Button.setEnabled(false);
		this.playAttr5Button.setEnabled(false);
	}

	/**
	 * Sets gameplay buttons for around where the user chooses the trump.
	 */
	private void setButtonsForUserChoice() {

		// Disable computer choice button
		this.playCompChooseButton.setEnabled(false);
		// Enable user choice buttons
		this.playAttr1Button.setEnabled(true);
		this.playAttr2Button.setEnabled(true);
		this.playAttr3Button.setEnabled(true);
		this.playAttr4Button.setEnabled(true);
		this.playAttr5Button.setEnabled(true);
	}

	/**
	 * Initialises a new Game object
	 *
	 */
	private void generateNewGame() {
		
		this.currentGame = new Game();
	}

	/**
	 * Starts a new game, disabling and enabling buttons, generating Players,
	 * selecting a starting Player, generating a CommunalPile of Cards,
	 * shuffling the Deck of Cards, dealing them to Players and generating GUI
	 * display text to inform the Player of the Game's progress.
	 */
	private void startNewGame() {

		// Clear text area of previous game info
		prevRoundString = "";		

		/**
		 * Debugging note:
		 *
		 * Comment out the line below to stop the deck from being shuffled,
		 * useful for testing.
		 */
		currentDeck.shuffleDeck();		

		// Call methods to generate and display new game.
		disableMetaButtons();
		generateNewGame();
		generatePlayers();
		setPlayerColours(getNumPlayers());
		
		player1.setText("");
		player2.setText("");
		player3.setText("");
		player4.setText("");
		chooseStartingPlayer();
		this.playButtonLabel.setText(generateCurrentTurnString());
		generateCommunalPile();
		dealCards();

		this.userAttributes.setText("Current card: " + generateCurrentCardString());

		setButtonsForRound();

		printShuffledDeck();
		printPlayerDecks();

		generateAndSetDisplayText();
	}

	/**
	 * Prints a formatted String representation of the Players' decks to the
	 * console.
	 */
	private void printPlayerDecks() {

		for (int i = 0; i < currentPlayers.length; i++) {

			Player P = currentPlayers[i];

			System.out.println(LINE_BREAK_STRING);
			System.out.println("");
			System.out.println("Cards belonging to: " + P.getName());

			printDeckAttributeNames();
			printPlayerHand(P);
		}
	}

	/**
	 * Prints a formatted String representation of a given Player's hand of
	 * Cards to the console.
	 *
	 * @param p,
	 *            Player
	 */
	private void printPlayerHand(Player p) {

		for (int i = 0; i < p.getHandSize(); i++) {
			printCard(p.getCardAtIndex(i));
		}
		System.out.println();
	}

	/**
	 * Prints a formatted String representation of an unshuffled Deck of Cards
	 * to the console.
	 */
	private void printUnshuffledDeck() {

		System.out.println(LINE_BREAK_STRING);
		System.out.println();
		System.out.println("Unshuffled deck:");
		System.out.println();
		printDeck();
		System.out.println(LINE_BREAK_STRING);
	}

	/**
	 * Prints a formatted String representation of a shuffled Deck of Cards to
	 * the console.
	 */
	private void printShuffledDeck() {

		System.out.println(LINE_BREAK_STRING);
		System.out.println();
		System.out.println("Shuffled deck:");
		System.out.println();
		printDeck();
		System.out.println(LINE_BREAK_STRING);
	}

	/**
	 * Prints a formatted String representation of the current Deck's attribute
	 * names to the console.
	 */
	private void printDeckAttributeNames() {

		System.out.println();

		String attribute1Name = currentDeck.getAttriNameAtIndex(1);
		String attribute2Name = currentDeck.getAttriNameAtIndex(2);
		String attribute3Name = currentDeck.getAttriNameAtIndex(3);
		String attribute4Name = currentDeck.getAttriNameAtIndex(4);
		String attribute5Name = currentDeck.getAttriNameAtIndex(5);

		String attributeNameString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", "",
				attribute1Name, attribute2Name, attribute3Name, attribute4Name, attribute5Name);

		System.out.println(attributeNameString);
	}

	/**
	 * Prints a formatted String representation of given Card's attribute values
	 * to the console.
	 *
	 * @param c
	 */
	private void printCard(Card c) {

		String nameValue = c.getNameVal();
		String att1Value = Integer.toString(c.getAttriValAtIndex(1));
		String att2Value = Integer.toString(c.getAttriValAtIndex(2));
		String att3Value = Integer.toString(c.getAttriValAtIndex(3));
		String att4Value = Integer.toString(c.getAttriValAtIndex(4));
		String att5Value = Integer.toString(c.getAttriValAtIndex(5));

		String attValString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", nameValue,
				att1Value, att2Value, att3Value, att4Value, att5Value);

		System.out.println(attValString);
	}

	/**
	 * Prints a formatted String representation of the current Deck to the
	 * console.
	 */
	private void printDeck() {

		printDeckAttributeNames();

		for (int i = 0; i < NUM_CARDS; i++) {
			Card CurrentCard = currentDeck.getCardAtIndex(i);
			printCard(CurrentCard);

		}
		System.out.println();
	}

	/**
	 * Deals all of the cards in the current Deck to the Players each in turn,
	 * starting with the user, some Players may end up with fewer cards than
	 * others.
	 */
	private void dealCards() {

		int numPlayers = getNumPlayers();
		for (int i = 0; i < this.NUM_CARDS; i++) {

			// Choose next player dealt to
			Player p = currentPlayers[i % numPlayers];
			// Deal card
			p.giveCard(this.currentDeck.getCardAtIndex(i));
		}
	}

	/**
	 * Selects a randomly selected Player as the deciding Player to choose the
	 * trump for the first round.
	 */
	private void chooseStartingPlayer() {

		Random rand = new Random();
		
		int decidingPlayerIndex = rand.nextInt(currentPlayers.length);
		this.decidingPlayer = currentPlayers[decidingPlayerIndex];
	}

	/**
	 * Generates a new CommunalPile and sets it as an instance variable.
	 */
	private void generateCommunalPile() {
		
		this.currentPile = new CommunalPile();
	}

	/**
	 * Disables the meta buttons, which control starting new games.
	 */
	private void disableMetaButtons() {

		this.showStatsButton.setEnabled(false);
		this.newGameButton.setEnabled(false);
		this.numPlayersComboBox.setEnabled(false);
		this.showStatsButton.setEnabled(false);
	}

	/**
	 * Generates the number of computer players requested by the user for the
	 * game and names them.
	 */
	private void generatePlayers() {

		// Calculating number of Players
		int numPlayers = getNumPlayers();
		Player[] NewPlayers = new Player[numPlayers];

		// Creating human Player
		Player Human = new Player(this.USER_NAME);
		NewPlayers[0] = Human;

		// Creating computer Players
		String[] CompPlayerNames = { "WatsonBot", "Amiga64Bot", "BabbageBot", "TuringBot" };

		for (int i = 1; i < numPlayers; i++) {

			NewPlayers[i] = new Player(CompPlayerNames[i - 1]);
		}

		// Creating new Players array
		this.currentPlayers = NewPlayers;
	}

	/**
	 * Returns the number of Players in the current Players array
	 *
	 * @return the number of Players in the Game, int
	 */
	private int getNumPlayers() {

		String numPlayersString = String.valueOf(numPlayersComboBox.getSelectedItem());
		String[] numPlayersArray = numPlayersString.split(" ");
		int numPlayers = Integer.parseInt(numPlayersArray[0]);
		return numPlayers;
	}
	
	/**
	 * Sets the textAreas of players currently playing in 
	 * colours
	 */
	private void setPlayerColours(int numPlayers) {

		if (numPlayers == 2) {
			player1.setBackground(new Color(255, 255, 105));
		} else if (numPlayers == 3) {
			player1.setBackground(new Color(255, 255, 105));
			player2.setBackground(new Color(255, 153, 153));
		} else if (numPlayers == 4) {
			player1.setBackground(new Color(255, 255, 105));
			player2.setBackground(new Color(255, 153, 153));
			player3.setBackground(new Color(153, 204, 255));
		} else {
			player1.setBackground(new Color(255, 255, 105));
			player2.setBackground(new Color(255, 153, 153));
			player3.setBackground(new Color(153, 204, 255));
			player4.setBackground(new Color(102, 255, 178));
		}
		userAttributes.setBackground(new Color(255, 178, 102));
	}

	/**
	 * Sets the textAreas of players currently playing with the number of cards
	 * in hand after each round
	 */
	private void setPlayersHandSize() {

		if (this.currentPlayers.length == 2) {
			if (currentPlayers[1] != null) {
				player1.setText("Cards left in hand:\n" + currentPlayers[1].getHandSize());
			} else {
				player1.setText("Cards left in hand: 0");
			}
		}
		if (currentPlayers.length == 3) {
			if (currentPlayers[1] != null) {
			player1.setText("Cards left in hand:\n" + currentPlayers[1].getHandSize());
			} else {
				player1.setText("Cards left in hand: 0");
			}
			if (currentPlayers[2] != null) {
			player2.setText("Cards left in hand:\n" + currentPlayers[2].getHandSize());
			} else {
				player2.setText("Cards left in hand: 0");
			}
		}
		if (currentPlayers.length == 4) {
			if (currentPlayers[1] != null) {
				player1.setText("Cards left in hand:\n" + currentPlayers[1].getHandSize());
			} else {
				player1.setText("Cards left in hand: 0");
			}
			if (currentPlayers[2] != null) {
				player2.setText("Cards left in hand:\n" + currentPlayers[2].getHandSize());
			} else {
				player2.setText("Cards left in hand: 0");
			}
			if (currentPlayers[3] != null) {
				player3.setText("Cards left in hand:\n" + currentPlayers[3].getHandSize());
			} else {
				player3.setText("Cards left in hand: 0");
			}
		}
		if (currentPlayers.length == 5) {
			if (currentPlayers[1] != null) {
				player1.setText("Cards left in hand:\n" + currentPlayers[1].getHandSize());
			} else {
				player1.setText("Cards left in hand: 0");
			}
			if (currentPlayers[2] != null) {
				player2.setText("Cards left in hand:\n" + currentPlayers[2].getHandSize());
			} else {
				player2.setText("Cards left in hand: 0");
			}
			if (currentPlayers[3] != null) {
				player3.setText("Cards left in hand:\n" + currentPlayers[3].getHandSize());
			} else {
				player3.setText("Cards left in hand: 0");
			}	
			if (currentPlayers[4] != null) {
				player4.setText("Cards left in hand:\n" + currentPlayers[4].getHandSize());
			} else {
				player4.setText("Cards left in hand: 0");
			}
		}
	}
}
