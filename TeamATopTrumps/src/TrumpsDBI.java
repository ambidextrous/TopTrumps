import java.sql.*;

/**
 * Programme: a Top Trumps game featuring a GUI and a database connection to 
 * store data.
 * 
 * Class: database interaction class to read and write game data to a database.
 * 
 * Database creation script:
 * 
 * CREATE TABLE TopTrumpsStats(ID SERIAL PRIMARY KEY, numRounds INT, 
 * numDraws INT, numHumanWins INT, humanWinner BOOLEAN, 
 * CONSTRAINT numRounds_isPos CHECK (numRounds > 0), 
 * CONSTRAINT numHumanWins_isNotNeg CHECK (numHumanWins >= 0), 
 * CONSTRAINT numDraws_is_not_neg CHECK (numDraws >= 0));
 * 
 * Sample addition to database
 * 
 * INSERT INTO TopTrumpsStats (numRounds, numDraws, numHumanWins, humanWinner) 
 * VALUES (20, 1, 12, false);
 * 
 * @author Team A
 *         Faisal Ahsan 2242114a 
 *         Aidan Butler 2281611b 
 *         Stewart Brown 2276998b
 *         Jane Kennedy 2287767k 
 *         Svetoslava Nikolova 1004630n
 */
public class TrumpsDBI {
    
    private final int DB_ID = 1;

    // Initialise database connection
    private Connection connection = null;

    public TrumpsDBI() {
            // TODO Auto-generated constructor stub
    }

    /**
     * Establishes connect with database and prints output to console 
     * indicating whether connect attempt successful.
     */
    public void establishDbConnection() {

        String dbname = "m_16_2281611b"; // Sample value: "m_16_2281611b"
        String username = "m_16_2281611b"; // Sample value: "m_16_2281611b"
        String password = "2281611b"; // Sample value: "2281611b"

        try {

            connection = DriverManager.getConnection("jdbc:post"
                    + "gresql://yacata.dcs.gla.ac.uk:5432/" 
                    + dbname, username, password);

        } catch (SQLException e) {

            System.err.println("Connection Failed!");
            e.printStackTrace();

            return;
        }
        if (connection != null) {

            System.out.println("Connection successful");

        } else {

            System.err.println("Failed to make connection!");
        }
    }

    /**
     * Closes database connection
     * Prints connection status information to console
     */
    public void closeDbConnection() {

        try {

            connection.close();
            System.out.println("Connection closed");

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Connection could not be "
                    + "closed an SQL exception");
        }
    }    
        
    /**
     * Passes a given update instruction to the DBMS and prints connection 
     * status information to console.
     * 
     * @param an update instruction
     * @return an array of strings
     */
    public void updateDB(String update) {

        Statement stmt = null;

        try {

            stmt = connection.createStatement();
            stmt.executeUpdate(update);

        } catch (SQLException e) {

            e.printStackTrace();
            System.err.println("Error executing update " + update);
        }
    }

    /**
     * Formats game data from a Game object to a SQL query string and 
     * calls a method to execute it.
     * 
     * @param g, a Game
     */
    public void addGameToDB(Game g) {

        boolean humanWinner = g.isHumanWinner();
        int numRounds = g.getNumRounds();
        int numHumanWins = g.getNumHumanRoundWins();
        int numDraws = g.getNumDraws();
               
        String updateString = "INSERT INTO TopTrumpsStats "
                + "(numRounds, numDraws, numHumanWins, humanWinner) "
                + "VALUES ("+numRounds+", "+numDraws+", "
                + ""+numHumanWins+", "+humanWinner+");"; 

        updateDB(updateString);
    }    

    /**
     * Queries database to get total number of games played.
     * 
     * @return the number of games played, int (-1 if error)
     */
    public int getNumGames() {

        Statement stmt = null;
        String query = "SELECT COUNT(ID) AS numGames FROM TopTrumpsStats;"; 
        
        int numGames = -1; // Error return value

        try {

            stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
            
                numGames = rs.getInt("numGames");
            }
            
            return numGames;

        } catch (SQLException e) {

            e.printStackTrace();
            System.err.println("Error executing query " + query);
        }

        return numGames;
    }
    
    /**
     * Queries database to get highest number of rounds played.
     * 
     * @return highest number of games played, int (-1 if error)
     */
    public int getMaxRounds() {

        Statement stmt = null;
        String query = "SELECT MAX(numRounds) AS maxNumRounds "
                + "FROM TopTrumpsStats;"; 
        
        int maxNumRounds = -1; // Error return value

        try {

            stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
            
                maxNumRounds = rs.getInt("maxNumRounds");
            }
            
            return maxNumRounds;

        } catch (SQLException e) {

            e.printStackTrace();
            System.err.println("Error executing query " + query);
        }

        return maxNumRounds;
    }    

    /**
     * Queries database to get average number of draws in the games played.
     * 
     * @return average number of draws in games played, double (-1.0 if error)
     */
    public double getAveDraws() {

        Statement stmt = null;
        String query = "SELECT AVG(numDraws) AS aveDraws "
                + "FROM TopTrumpsStats;";
        
        double aveDraws = 0.0; 

        try {

            stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                
                aveDraws = rs.getDouble("aveDraws");
            }
                        
            return aveDraws;

        } catch (SQLException e) {

            e.printStackTrace();
            System.err.println("Error executing query " + query);
        }

        return aveDraws;
    }     

    /**
     * Queries database to get total number of human wins in games played.
     * 
     * @return number of games won by humans, int (-1 if error)
     */
    public int getNumHumanWins() {

        Statement stmt = null;
        String query = "SELECT COUNT(ID) AS numHumanWins FROM TopTrumpsStats "
                + "WHERE humanWinner = 'true';";
        
        int numHumanWins = -1; // Error return value

        try {

            stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
            
                numHumanWins = rs.getInt("numHumanWins");
            }
            
            return numHumanWins;

        } catch (SQLException e) {

            e.printStackTrace();
            System.err.println("Error executing query " + query);
        }

        return numHumanWins;
    } 
}
