/**
 * Program: a Top Trumps game featuring a GUI and a database connection to 
 * store data.
 * 
 * Class: Represents the communal pile of top trumps cards created when a round 
 * is tied and given away again when one is one. 
 * 
 * @author Team A
 *         Faisal Ahsan 2242114a 
 *         Aidan Butler 2281611b 
 *         Stewart Brown 2276998b
 *         Jane Kennedy 2287767k 
 *         Svetoslava Nikolova 1004630n
 */
public class CommunalPile {
	
    // Instance variable to store all cards in communal pile
    private Card[] cards; 
    
    // Constructor 
    public CommunalPile() {
        
        this.cards = new Card[0];
    }
    
    /**
     * Returns the number of Cards in the CommunalPile.
     * 
     * @return number of Cards in pile, int
     */
    public int getPileSize() {
        
        return this.cards.length;
    }
    
    /**
     * Adds a given card to the end of a players hand.
     * 
     * @param newCard 
     */
    public void giveCard(Card newCard) {
        
        Card[] newHand = new Card[this.cards.length+1];
        
        for (int i = 0; i < cards.length; i++) {
            newHand[i] = this.cards[i];
        }
        
        newHand[cards.length] = newCard;
        
        this.cards = newHand;
    }
    
    /**
     * Removes the first card from a player's hand and returns it.
     * 
     * @return Card, takenCard
     */
    public Card takeCard() {
        
        Card takenCard = this.cards[0];        
        Card[] newHand = new Card[this.cards.length-1];
        
        for (int i = 0; i < newHand.length; i++) {
            
            newHand[i] = this.cards[i+1];
        }
        this.cards = newHand;
        
        return takenCard;
    }    
    
    /**
     * Returns the Card at a given index.
     * 
     * @param index, int
     * @return Card at that index in CommunalPile
     */
    public Card getCardAtIndex(int index) {
        
        return this.cards[index];
    }
}
