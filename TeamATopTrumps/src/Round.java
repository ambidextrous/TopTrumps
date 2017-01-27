/**
 *
 * Program: a Top Trumps game featuring a GUI and a database connection to store
 * data.
 * 
 * Class: represents a Round of Top Trumps, in which cards are played and d
 * distributed to the winner in the case of a win or added to the CommunalPile
 * in the case of a draw.
 * 
 * @author Team A
 *  Faisal Ahsan 2242114a 
 *  Aidan Butler 2281611b 
 *  Stewart Brown 2276998b 
 *  Jane Kennedy 2287767k 
 *  Svetoslava Nikolova 1004630n
 */
public class Round {
	private final int NUM_ATTRIBUTES;
	private Player[] players;
	private Card[] cardsInPlay;
	private String LINE_BREAK;
	private int trumpIndex;
	private CommunalPile pile;
	private Player winner;
	private Deck deck;
	private boolean draw;
	private int[] prevTrumpValues;

	public Round(Player[] playersArray, Player CurrentDecidingPlayer, CommunalPile CP, int trumpInd, Deck d,
			String lineBreak, int numCards) {

		this.NUM_ATTRIBUTES = 5;
		this.LINE_BREAK = lineBreak;
		this.players = playersArray;
		this.deck = d;
		this.pile = CP;
		this.winner = null;
		this.draw = false;

		int userChoseTrump = 0;

		prevTrumpValues = new int[players.length];

		// NOTE: SHOULD THESE VALUES BE INITIALISED TO 0 IN THE CONSTRUCTOR?

		// User player choice or algorithm to select trump for round
		if (trumpInd == userChoseTrump) {

			this.trumpIndex = getCompTrumpIndex(CurrentDecidingPlayer);

		} else {

			this.trumpIndex = trumpInd;
		}
	}

	/**
	 * AI method to have the computer player whose turn it is choose to play the
	 * highest valued attribute on their card.
	 * 
	 * @return the index of the computer Card attribute with the highest value
	 */
	private int getCompTrumpIndex(Player DecidingP) {

		Card topCard = DecidingP.viewTopCard();

		int highestAttInd = 0;
		int highestAttVal = 0;

		for (int i = 0; i < NUM_ATTRIBUTES; i++) {

			if (topCard.getAttriValAtIndex(i) > highestAttVal) {

				highestAttVal = topCard.getAttriValAtIndex(i);
				highestAttInd = i;
			}
		}
		return highestAttInd;
	}

	/**
	 * Plays the Round of top trumps calculating and recording the results and
	 * redistributing cards according, results are output to both the GUI and
	 * the console.
	 */
	public void playRound() {

		System.out.println("Player hands pre-round: \n");
		printPlayerHands();

		int pileSizeAtStart = this.pile.getPileSize();

		this.draw = checkIfDraw();

		Player winner;

		winner = calculateWinner();

		this.winner = winner;

		Card [] cardsInPlay = takeCards();

		distributeCards(cardsInPlay, winner);

		int pileSizeAtEnd = this.pile.getPileSize();

		System.out.println("Player hands post-round: \n");
		printPlayerHands();

		if (pileSizeAtStart != pileSizeAtEnd) {

			this.printCardsInCommonPile();
		}
	}

	/**
	 * Method to return an integer array containing the Trump values from the
	 * previous round.
	 */

	public int[] saveTrumpValues() {

		Card[] cards = new Card[players.length];

		for (int i = 0; i < cards.length; i++) {

			if (players[i].getHandSize() != 0) {

				cards[i] = players[i].viewTopCard();
				prevTrumpValues[i] = cards[i].getAttriValAtIndex(trumpIndex);
			}
		}
		return prevTrumpValues;
	}

	/**
	 * Distributes Card: in the case of a win, all Cards in the CommunalPile go
	 * to the winner as do all of the cards in play; in the case of a draw all
	 * cards in play go to the communal pile
	 * 
	 * @param cardsInPlay,
	 *            Card[]
	 * @param winner,
	 *            Player
	 */
	private void distributeCards(Card[] cardsInPlay, Player winner) {

		if (winner == null) {

			// Add cards to CommunalPile

			for (int i = 0; i < cardsInPlay.length; i++) {

				Card c = cardsInPlay[i];

				if (c != null) {

					this.pile.giveCard(c);
				}
			}
		} else {
			// Give cards in play to winner
			for (int i = 0; i < cardsInPlay.length; i++) {
				Card c = cardsInPlay[i];
				if (c != null) {
					winner.giveCard(c);
				}
			}

			// Give cards in pile to winner

			for (int i = 0; i < pile.getPileSize(); i++) {

				Card c = pile.getCardAtIndex(i);

				if (c != null) {

					winner.giveCard(c);

				}
			}
			pile = new CommunalPile();
		}
	}

	/**
	 * Takes the top Card from each Player's hand, provided that they have at
	 * least one card.
	 * 
	 * @return the taken Cards, Card[]
	 */
	private Card[] takeCards() {

		Card[] cardsArray = { null, null, null, null, null };

		for (int i = 0; i < players.length; i++) {

			if (players[i].getHandSize() != 0) {

				Card c = players[i].takeCard();
				cardsArray[i] = c;
			}
		}
		return cardsArray;
	}

	/**
	 * Returns whether the Round was a draw: creates zeroed integer array and
	 * increments each element if it for each player that has that score for
	 * their trump; returns whether the highest number in that array is greater
	 * than 1.
	 * 
	 * @return whether the round was a draw, boolean
	 */
	private boolean checkIfDraw() {

		boolean draw = false;
		int maxScore = 50;

		int[] playerScores = new int[maxScore];

		for (int i = 0; i < players.length; i++) {

			if (players[i].getHandSize() != 0) {

				Card c = players[i].getCardAtIndex(0);
				int score = c.getAttriValAtIndex(this.trumpIndex);
				playerScores[score - 1]++;
			}
		}

		for (int i = maxScore - 1; i >= 0; i--) {
			
			if (playerScores[i] == 1)
				break;
			else if (playerScores[i] > 1) {
				draw = true;
				break;
			}

		}

		return draw;
	}

	/**
	 * Returns the Player with the highest score for the trump of the given
	 * round.
	 * 
	 * @return the winning Player
	 */
	private Player calculateWinner() {

		Player winningPlayer = null;

		int topScore = 0;

		for (int i = 0; i < this.players.length; i++) {

			if (players[i].getHandSize() > 0) {

				// Look at player's top card
				Card c = players[i].getCardAtIndex(0);
				int playerScore = c.getAttriValAtIndex(this.trumpIndex);

				if (playerScore == topScore) {
					winningPlayer = null; // Draw scenario, reset winner to null
				} else if (playerScore > topScore) {
					topScore = playerScore;
					winningPlayer = players[i];
				}
			}
		}

		return winningPlayer;
	}

	/**
	 * Returns the Player who won the round.
	 * 
	 * @return the winner of the Round, Player
	 */
	public Player getWinner() {

		return winner;
	}

	/**
	 * Returns whether the user has won the game (has all of the cards in the
	 * pack).
	 * 
	 * @return whether user won game (has all the cards), boolean
	 */
	public boolean boolUserWonGame() {

		int playerIndex = 0;
		Player user = this.players[playerIndex];

		return user.getHandSize() == this.deck.getDeckLength();
	}

	/**
	 * Returns whether the user has lost the game (has no cards).
	 * 
	 * @return whether user lost game (has no cards), boolean
	 */
	public boolean boolUserLostGame() {

		int playerIndex = 0;
		Player user = this.players[playerIndex];

		return user.getHandSize() == 0;
	}

	/**
	 * Returns whether or not the round was a draw.
	 * 
	 * @return whether or not the round was a draw.
	 */
	public boolean isDraw() {

		return this.draw;
	}

	/**
	 * Generates a returns a formatted String giving information on the Round,
	 * including its trump, scores, if it resulted in a won or lost game,
	 * whether it resulted in a draw, the cards each player has left and the
	 * size of the communal pile.
	 * 
	 * @return formatted String giving containing Round information
	 */
	public String getRoundString() {

		String roundString = String.format("");

		roundString += getRoundTrumpString();
		roundString += getRoundScoresString();
		roundString += getGameWonLostString();
		roundString += getWinnerDrawString();
		roundString += getPlayerCardsLeftString();
		roundString += getCommunalPileString();

		return roundString;
	}

	/**
	 * Generates and returns a formatted String indicating the number of Cards
	 * in the CommunalPile.
	 * 
	 * @return formatted String indicating size of CommunalPile
	 */
	private String getCommunalPileString() {

		String s = String.format("Cards in common pile:");
		s += String.format("%d", this.pile.getPileSize());

		return s;
	}

	/**
	 * Generates and returns a formatted String indicating the Cards each Player
	 * has left.
	 * 
	 * @return formatted String indicating cards each player has left
	 */
	private String getPlayerCardsLeftString() {

		String s = String.format("Player cards left:%n");

		for (int i = 0; i < this.players.length; i++) {

			Player p = players[i];

			s += String.format(p.getName());
			s += String.format(": ");
			s += String.format("%d", p.getHandSize());
			s += String.format("   ");
		}

		s += String.format("%n%n");

		return s;
	}

	/**
	 * Generates and returns a formatted String indicating either the identity
	 * of the winner or the round, or the fact that it was a draw.
	 * 
	 * @return formatted String indicating whether round won or drawn
	 */
	private String getWinnerDrawString() {

		String s = String.format("");

		if (this.draw) {
			s += String.format("This round was a draw.%n%n");
		} else {
			s += String.format("%s won the previous round%n%n", this.winner.getName());
		}

		return s;
	}

	/**
	 * If user won or lost game during round, generates and returns a formatted
	 * String indicating that.
	 * 
	 * @return a formatted String indicating if the user won or lost the game,
	 *         or an empty String
	 */
	private String getGameWonLostString() {

		String s = String.format("");

		if (this.boolUserWonGame()) {

			s += String.format("YOU WON THE GAME!%n%n");
			printWinner();

		} else if (this.boolUserLostGame()) {

			s += String.format("YOU LOST THE GAME!%n%n");
			printWinner();
		}

		return s;
	}

	/**
	 * Generates and returns a formatted String indicating the trump scores of
	 * the players participating in the round.
	 * 
	 * @return formatted String indicating player trump scores
	 */
	private String getRoundScoresString() {

		String s = String.format("");

		for (int i = 0; i < this.players.length; i++) {

			Player p = players[i];

			if (p.getHandSize() > 0) {

				Card c = p.viewTopCard();

				s += String.format("%s: ", players[i].getName());
				s += String.format("%d    ", prevTrumpValues[i]);
			}
		}
		s += String.format("%n%n");
		return s;
	}

	/**
	 * Generates and returns a formatted String indicating the trump of the
	 * round.
	 * 
	 * @return formatted String indicating trump of round
	 */
	private String getRoundTrumpString() {

		String s = String.format("Previous round attribute: %s%n", this.deck.getAttriNameAtIndex(trumpIndex));

		return s;
	}

	/**
	 * Print a formatted String containing information on a given Card's
	 * attributes to the console./
	 * 
	 * @param C,
	 *            a Card
	 */
	private void printCard(Card C) {

		String nameValue = C.getNameVal();
		String att1Value = Integer.toString(C.getAttriValAtIndex(1));
		String att2Value = Integer.toString(C.getAttriValAtIndex(2));
		String att3Value = Integer.toString(C.getAttriValAtIndex(3));
		String att4Value = Integer.toString(C.getAttriValAtIndex(4));
		String att5Value = Integer.toString(C.getAttriValAtIndex(5));

		String attValString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", nameValue,
				att1Value, att2Value, att3Value, att4Value, att5Value);

		System.out.println(attValString);

		System.out.println("");

	}

	/**
	 * Prints information on the attributes of cards in a given deck to the
	 * command line.
	 */
	private void printDeckAttributeNames() {

		System.out.println("");

		String attribute1Name = this.deck.getAttriNameAtIndex(1);
		String attribute2Name = this.deck.getAttriNameAtIndex(2);
		String attribute3Name = this.deck.getAttriNameAtIndex(3);
		String attribute4Name = this.deck.getAttriNameAtIndex(4);
		String attribute5Name = this.deck.getAttriNameAtIndex(5);

		String attributeNameString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", "",
				attribute1Name, attribute2Name, attribute3Name, attribute4Name, attribute5Name);

		System.out.println(attributeNameString);

	}

	/**
	 * Prints out information on the Cards in the CommunalPile to the command
	 * line, calling a method to print each Card in turn.
	 */
	private void printCardsInCommonPile() {

		System.out.println(this.LINE_BREAK);
		System.out.println("");
		System.out.println("Common Pile:");
		System.out.println("");

		printDeckAttributeNames();

		for (int i = 0; i < this.pile.getPileSize(); i++) {

			printCard(this.pile.getCardAtIndex(i));
		}

		System.out.println(this.LINE_BREAK);
	}

	/**
	 * Calls a method to print information on the Cards of all of the Players
	 * still holding any to the console.
	 */
	private void printPlayerHands() {

		for (int i = 0; i < this.players.length; i++) {

			Player p = this.players[i];

			if (p.getHandSize() > 0) {

				printPlayerHand(p);
			}
		}
	}

	/**
	 * Prints information on the Cards in the hand of a given Player to the
	 * console, calling a method to print the Cards of each Player in turn.
	 * 
	 * @param p,
	 *            a Player
	 */
	private void printPlayerHand(Player p) {

		System.out.println(this.LINE_BREAK);
		System.out.println("");
		System.out.println("Cards in hand belonging to: " + p.getName());
		System.out.println("");

		printDeckAttributeNames();

		for (int i = 0; i < p.getHandSize(); i++) {

			printCard(p.getCardAtIndex(i));
		}

		System.out.println(this.LINE_BREAK);
	}

	/**
	 * Prints the cards in play in the current Round, calling a method to print
	 * each one in turn.
	 */
	private void printCardsInPlay() {

		System.out.println(this.LINE_BREAK);
		System.out.println("");
		System.out.println("Cards in Play:");
		System.out.println("");

		printDeckAttributeNames();

		for (int i = 0; i < this.cardsInPlay.length; i++) {

			printCard(this.cardsInPlay[i]);
		}

		System.out.println(this.LINE_BREAK);
	}

	/**
	 * Prints information on the current round trump and the players'
	 * corresponding scores to the command line
	 */
	private void printTrumpInfo() {

		System.out.println(this.LINE_BREAK);
		System.out.println("");
		System.out.println("Trump for this round:");
		System.out.println("");

		System.out.println(this.deck.getAttriNameAtIndex(trumpIndex));

		System.out.println("");
		System.out.println("Trump scores for this round:");
		System.out.println("");

		for (int i = 0; i < this.players.length; i++) {

			Player p = this.players[i];

			if (p.getHandSize() > 0) {

				Card c = p.getCardAtIndex(0);

				System.out.println(p.getName() + " trump score:" + " " + c.getAttriValAtIndex(this.trumpIndex) + "\n");
			}
		}
		System.out.println("");

		System.out.println(this.LINE_BREAK);
	}

	/**
	 * Prints information on the winner of a game to the Console.
	 */
	private void printWinner() {

		System.out.println(this.LINE_BREAK);
		System.out.println("");
		System.out.println("The winner of the game is");
		System.out.println("");

		System.out.println(this.winner.getName());

		System.out.println("");
		System.out.println(this.LINE_BREAK);

	}


public CommunalPile getPile(){
	
	return pile;
	}
}
