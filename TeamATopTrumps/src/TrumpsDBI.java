import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Program: a Top Trumps game featuring a GUI and a database connection to store
 * data.
 * 
 * Class: database interaction class to read and write game data to a database.
 * 
 *
 * @author Team A Faisal Ahsan 2242114a Aidan Butler 2281611b Stewart Brown
 *         2276998b Jane Kennedy 2287767k Svetoslava Nikolova 1004630n
 */
public class TrumpsDBI {

	// Instance variable to initialise database connection
	private static Connection connection = null;

	// Default constructor
	public TrumpsDBI() {}

	/**
	 * Establishes connect with database and prints output to console indicating
	 * whether connect attempt successful.
	 * 
	 * @return boolean indicating whether the operation was successful or not.
	 */
	public boolean establishDbConnection() {

		// Authentication details for Jane Kennedy
		String dbname = "m_16_2287667k";
		String username = "m_16_2287667k";
		String password = "2287667k";

		boolean isConnected = false; // assume false until connection positively
										// made

		try {
			connection = DriverManager.getConnection("jdbc:post" + "gresql://yacata.dcs.gla.ac.uk:5432/" + dbname,
					username, password);
		}

		catch (SQLException e) {
			System.err.println("Connection Failed!");
			e.printStackTrace();
		}

		if (connection != null) {
			System.out.println("Connection successful");
			isConnected = true;
		}

		else
			System.err.println("Failed to make connection!");

		return isConnected;
	}

	/**
	 * Closes database connection Prints connection status information to
	 * console
	 */
	public void closeDbConnection() {

		try {

			connection.close();
			System.out.println("Connection closed");

		}

		catch (SQLException e) {
			System.out.println("Connection could not be closed (SQL exception)");
			e.printStackTrace();
		}
	}

	/**
	 * Formats game data from a Game object to a SQL query string and calls a
	 * method to execute it.
	 * 
	 * @param g,
	 *            a Game
	 */
	public boolean addGameToDB(Game g) {

		int gameID = getNumGames() + 1; // Unique ID based on number of games
										// currently saved
		String winner;
		if (g.isHumanWinner()) {
			winner = "'human'";
		} else {
			winner = "'computer'";
		}

		int numRounds = g.getNumRounds();
		int numDraws = g.getNumDraws();

		String updateString = "INSERT INTO TopTrumpStats VALUES (" + gameID + ", " + numRounds + ", " + numDraws + ", "
				+ winner + ");";

		if (updateDB(updateString)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Queries database to get total number of games played.
	 * 
	 * @return the number of games played, int (-1 if error)
	 */
	public int getNumGames() {

		Statement stmt = null;
		String query = "SELECT COUNT(gameID) FROM TopTrumpStats;";

		int numGames = -1; // Error return value

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next())
				numGames = rs.getInt(1); // Column index for COUNT result

			return numGames;
		}

		catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error executing query " + query);
		}

		return numGames;
	}

	/**
	 * Passes a given update instruction to the DBMS and prints connection
	 * status information to console.
	 * 
	 * @param an
	 *            update instruction
	 * @return an array of strings
	 */
	private static boolean updateDB(String update) {

		boolean isUpdated = false;
		Statement stmt = null;

		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(update);
			isUpdated = true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error executing update " + update);
		}

		return isUpdated;
	}

	/**
	 * Queries database to get highest number of rounds played.
	 * 
	 * @return highest number of games played, int (-1 if error)
	 */
	public int getMaxRounds() {

		Statement stmt = null;
		String query = "SELECT MAX(numRounds) AS maxNumRounds " + "FROM TopTrumpStats;";

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
		String query = "SELECT AVG(numDraws) AS aveDraws " + "FROM TopTrumpStats;";

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
		String query = "SELECT COUNT(gameID) FROM TopTrumpStats " + "WHERE winner = 'human';";

		int numHumanWins = -1; // Error return value

		try {

			stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {

				numHumanWins = rs.getInt(1);
			}

			return numHumanWins;

		} catch (SQLException e) {

			e.printStackTrace();
			System.err.println("Error executing query " + query);
		}

		return numHumanWins;
	}
}
