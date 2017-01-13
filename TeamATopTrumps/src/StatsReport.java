/*
 * Programme: a Top Trumps game featuring a GUI and a database connection to 
 * store data.
 * 
 * Class: Produces a statistics report window to describe historical top 
 * trumps game data.
 * 
 * @author Team A
 *         Faisal Ahsan 2242114a 
 *         Aidan Butler 2281611b 
 *         Stewart Brown 2276998b
 *         Jane Kennedy 2287767k 
 *         Svetoslava Nikolova 1004630n
 */
    
      
    
import java.awt.*;
import javax.swing.*;

/**
 * Programme:
 * A programme to load saved gym class and time table information from disk,
 * store than information in FitnessProgramme an FitnessClass objects,
 * add or delete gym-classes using a GUI and save info to disk again.  
 * 
 * Class:
 * Defines window in which attendance report is displayed.
 */
public class StatsReport extends JFrame {
    /**
     * A JFrame extension window to display attendance report. 
     */
    private JTextArea textArea;
    private TrumpsDBI DB;

    public StatsReport() {
        
        this.textArea = new JTextArea();
        this.DB = new TrumpsDBI();

        // Sets text area unenabled		
        textArea.setEnabled(false);
        textArea.setFont(new Font("monospaced", Font.PLAIN, 12));
        
        // Sets text colour
        Color blue = new Color(0, 0, 255);
        textArea.setDisabledTextColor(blue); 
        
        // Sets basic window parameters
        this.setTitle("Historical Game Statistics Report");
	this.setSize(600,150);
	this.setLocation(300,200);
	this.setVisible(true);

        // Adds textArea
        add(textArea);	

        // Updates display
	this.repaint();
	this.revalidate();
    }
    
    /**
     * Generates an attendance report using data from a Game - including data on
     * the number of games played, the number of computer wins, the number of 
     * human wins, the average number of draws and the highest number of rounds
     * played it a game -, formats the data as a string and sets it as the 
     * window text. 
     * 
     * @param DB, a TrumpsDBI database interaction object
     */
    public void buildReport(TrumpsDBI DB) {
        
        // Open database connection.
        DB.establishDbConnection();
        
        int numGames = DB.getNumGames();
        int numHumanW = DB.getNumHumanWins();
        int numCompW = numGames - numHumanW;
        double aveNumDraws = DB.getAveDraws();
        int maxNumR = DB.getMaxRounds();

        // Close database connection
        DB.closeDbConnection();        
        
        String numGamesPlayed = String.format("Number of games played "
                + "= %d%n%n", numGames);

        String numCompWins = String.format("Number of computer wins "
                + "= %d%n%n", numCompW);

        String numHumanWins = String.format("Number of human wins "
                + "= %d%n%n", numHumanW);
        
        String avNumDraws = String.format("Average number of draws "
                + "= %f%n%n", aveNumDraws);
        
        String maxNumRounds = String.format("Highest number of rounds played "
                + "in a single game "
                + "= %d%n%n", maxNumR);        
        
        String dispText = numGamesPlayed + numCompWins + numHumanWins + 
                avNumDraws + maxNumRounds;
        
        // Print report to screen
        this.textArea.setText(dispText);
        
    }
}    
    
