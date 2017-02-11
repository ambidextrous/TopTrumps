/**
 * Program: a Top Trumps game featuring a GUI and a database connection to 
 * store data.
 * 
 * Class: Represents a full deck top trumps Cards, with its associated
 * attribute descriptions, can be shuffled.
 * 
 * @author Team A
 *         Faisal Ahsan 2242114a 
 *         Aidan Butler 2281611b 
 *         Stewart Brown 2276998b
 *         Jane Kennedy 2287767k 
 *         Svetoslava Nikolova 1004630n
 */
public class Deck {
    
	// Instance variables
    private Card[] cards;
    private final String attri1Name;
    private final String attri2Name;
    private final String attri3Name;
    private final String attri4Name;
    private final String attri5Name;

    // Constructor
    public Deck(Card[] cards, String attri1Name, 
            String attri2Name, String attri3Name, String attri4Name, 
            String attri5Name) {
        
        this.cards = cards;
        this.attri1Name = attri1Name;
        this.attri2Name = attri2Name;
        this.attri3Name = attri3Name;
        this.attri4Name = attri4Name;
        this.attri5Name = attri5Name;
    }

    /**
     * Returns the name of the first attribute of the Deck.
     * 
     * @return name of attribute, String
     */
    private String getAttri1Name() {

        return attri1Name;
    }

    /**
     * Returns the name of the second attribute of the Deck.
     * 
     * @return name of attribute, String
     */    
    private String getAttri2Name() {

        return attri2Name;
    }

    /**
     * Returns the name of the third attribute of the Deck.
     * 
     * @return name of attribute, String
     */    
    private String getAttri3Name() {

        return attri3Name;
    }

    /**
     * Returns the name of the fourth attribute of the Deck.
     * 
     * @return name of attribute, String
     */    
    private String getAttri4Name() {

        return attri4Name;
    }

    /**
     * Returns the name of the fifth attribute of the Deck.
     * 
     * @return name of attribute, String
     */    
    private String getAttri5Name() {
        
        return attri5Name;
    }

    /**
     * Returns the name of a Deck's attribute at a given index.
     * 
     * @param index, int
     * @return name of attribute, String
     */
    public String getAttriNameAtIndex(int index) {
        
        switch (index) {
            
            case 1:
                
                return getAttri1Name();
            
            case 2:
            
                return getAttri2Name();
            
            case 3: 
            
                return getAttri3Name();
            
            case 4:
            
                return getAttri4Name();
            
            case 5:
            
                return getAttri5Name();
            
            default:
            
                return null;
        }
    }
    
    /**
     * Returns the number Cards in the Deck.
     * 
     * @return Deck length, int
     */
    public int getDeckLength() {
        
        return this.cards.length;
    }
    
    /**
     * Returns the Card at a given index.
     * 
     * @param index
     * @return 
     */
    public Card getCardAtIndex(int index) {
        
        return this.cards[index];
    }
    
    /**
     * Randomly shuffles the order the cards in the Deck by calling a method to
     * generate a array of shuffled ints and then matching the Cards' ordering
     * to that of the ints.
     */
    public void shuffleDeck() {
              
        int deckLength = this.getDeckLength();
        int arrayStart = 0;
        int[] shuffledIntArray = generateShuffledIntArray(deckLength);
        Card[] shuffledCards = new Card[deckLength];
        
        for (int i = arrayStart; i < deckLength; i++) {
            
            shuffledCards[shuffledIntArray[i]] = this.cards[i];
        }
        
        this.cards = shuffledCards;
    }
    
    /**
     * Generates an array containing integers from 0 up to a given size, 
     * shuffles it using a Fisher-Yates shuffles and returns it, 
     * 
     * @param arraySize, int
     * @return a randomly shuffled array of ints
     */
    private int[] generateShuffledIntArray(int arraySize) {

    	// Generate ordered integer array
    	int[] unshuffledArray = new int[arraySize];

    	for (int i = 0; i < arraySize; i++) {

    		unshuffledArray[i] = i;
    	}

    	// Shuffle ordered integer array using Fisher-Yates shuffle
    	for (int i = 0; i < arraySize; i++) {

    		int rand = i + (int) (Math.random() * (arraySize - i));
    		int randomElement = unshuffledArray[rand];
    		unshuffledArray[rand] = unshuffledArray[i];
    		unshuffledArray[i] = randomElement;
    	}

    	int[] shuffledArray = unshuffledArray;

    	return shuffledArray;
    }    
}
