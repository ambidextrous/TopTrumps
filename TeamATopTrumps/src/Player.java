/**
 * Program: a Top Trumps game featuring a GUI and a database connection to 
 * store data.
 * 
 * Class: respresents a top trumps player, who can be human (the user) or one
 * of multiple possible computer players. Holds a number of cards.
 * 
 * @author Team A
 *         Faisal Ahsan 2242114a 
 *         Aidan Butler 2281611b 
 *         Stewart Brown 2276998b
 *         Jane Kennedy 2287767k 
 *         Svetoslava Nikolova 1004630n
 */
public class Player {
    
	// Instance variables
    private Card[] hand; // All of the cards held by a player
    private final String name;
    private boolean stillInGame;

    // Constructor
    public Player(String name) {
        
        this.name = name;
        this.hand = new Card[0];
        this.stillInGame = true;
    }

    /**
     * Returns the name of the Player.
     * 
     * @return Player's name, String
     */
    public String getName() {

        return name;
    }
    
    /**
     * Returns the number of Cards the Player has.
     * 
     * @return number Player's Cards, int
     */
    public int getHandSize() {
        
        return this.hand.length;
    }

    /**
     * Returns whether Player is still in Game.
     * 
     * @return whether Player is still in Game, boolean
     */
    public boolean isStillInGame() {

        return stillInGame;
    }

    /**
     * Sets whether the Player is still in a game.
     * 
     * @param stillInGame, boolean 
     */
    public void setStillInGame(boolean stillInGame) {

        this.stillInGame = stillInGame;
    }
    
    /**
     * Returns the Card a Player has at a given index in their array of Cards.
     * 
     * @param index, int
     * @return the Card at that index
     */
    public Card getCardAtIndex(int index) {      
        return this.hand[index];
    }
    
    
    /**
     * Adds a given card to the end of a players hand.
     * 
     * @param newCard 
     */
    public void giveCard(Card newCard) {
        
        Card[] newHand = new Card[this.hand.length+1];
        
        for (int i = 0; i < hand.length; i++) {
            
            newHand[i] = this.hand[i];
        }
        
        newHand[hand.length] = newCard;
        
        this.hand = newHand;
    }
    
    /**
     * Removes the first card from a player's hand and returns it.
     * 
     * @return Card, takenCard
     */
    public Card takeCard() {
        
        Card takenCard = this.hand[0];        
        Card[] newHand = new Card[this.hand.length-1];
        
        for (int i = 0; i < newHand.length; i++) {
            
            newHand[i] = this.hand[i+1];
            
        }
        this.hand = newHand;
        
        if (this.getHandSize() == 0) {
            
            this.stillInGame = false;
        }      
        return takenCard;
    }
    
    /**
     * Returns the Player's top card, without removing that card from 
     * the ones they hold
     * 
     * @return the top Card
     */
    public Card viewTopCard() {
        
        return this.hand[0];
    }
}
