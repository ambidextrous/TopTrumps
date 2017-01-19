/**
 *
 * Program: a Top Trumps game featuring a GUI and a database connection to 
 * store data.
 * 
 * Class: represents a game of top trumps, which is composed of multiple rounds
 * and finishes when won player has won all of the cards.
 * 
 * @author Team A
 *         Faisal Ahsan 2242114a 
 *         Aidan Butler 2281611b 
 *         Stewart Brown 2276998b
 *         Jane Kennedy 2287767k 
 *         Svetoslava Nikolova 1004630n
 */
public class Game {
    
    private int numDraws;
    private int numRounds;
    private int numHumanRoundWins;
    private int numCompRoundWins;
    private boolean humanGameWinner;

    public Game() {
        
        this.numDraws = 0;
        this.numRounds = 0;
        this.humanGameWinner = false;
    }

    /**
     * Returns the number of draws in the game.
     * 
     * @return the number of draws, int
     */
    public int getNumDraws() {

        return numDraws;
    }

    /**
     * Increments the number of draws in the game by one.
     * 
     */
    public void incrementNumDraws() {
        
        this.numDraws++;
    }
    
    /**
     * Returns the number of rounds played in the game.
     * 
     * @return the number of rounds, int
     */
    public int getNumRounds() {

        return numRounds;
    }
    
    /**
     * Increments the number of rounds in the game by one.
     */
    public void incrementNumRounds() {
        
        this.numRounds++;
    }

    /**
     * Returns the number of rounds won by the user.
     * 
     * @return rounds won by user, int
     */
    public int getNumHumanRoundWins() {

        return numHumanRoundWins;
    }

    /**
     * Increments the number of rounds won by the user by one.
     */
    public void incrementHumanRoundWins() {
        
        this.numHumanRoundWins++;
    }
    
    /**
     * Returns the number of rounds won by a computer player.
     * 
     * @return rounds won by computer, int
     */
    public int getNumCompRoundWins() {

        return numCompRoundWins;
    }
    
    /**
     * Increments the number of rounds won by a human player by one.
     */
    public void incrementeCompRoundWins() {
        
        this.numHumanRoundWins++;
    }

    /**
     * Returns whether the winner of the game is human.
     * 
     * @return whether the game winner is human.
     */
    public boolean isHumanWinner() {

        return humanGameWinner;
    }

    /**
     * Sets whether the game winner is human.
     * 
     * @param humanWinner, boolean 
     */
    public void setHumanWinner(boolean humanWinner) {
        
        this.humanGameWinner = humanWinner;
    }
    
}
