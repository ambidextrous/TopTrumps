import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;

import javafx.event.ActionEvent;

/**
 * Program: A program to load saved gym class and time table information from
 * disk, store than information in FitnessProgramme an FitnessClass objects, add
 * or delete gym-classes using a GUI and save info to disk again.
 * 
 * Class: Defines window in which attendance report is displayed.
 */
public class StatsReport extends JFrame implements ActionListener {
	/**
	 * A JFrame extension window to display attendance report.
	 */

	// Instance variables
	private JTextArea textArea;
	private JButton saveExit, exit;
	private TrumpsDBI DB;
	private String displayText;

	// Constructor
	public StatsReport() {

		this.textArea = new JTextArea();
		this.DB = new TrumpsDBI();

		// Sets text area disabled
		textArea.setEnabled(false);
		textArea.setFont(new Font("monospaced", Font.PLAIN, 12));

		// creating the two buttons for exit and/or save
		saveExit = new JButton("Save and Exit");
		exit = new JButton("Exit");
		saveExit.addActionListener(this);
		exit.addActionListener(this);

		// panel for buttons
		JPanel pan = new JPanel();
		pan.add(saveExit);
		pan.add(exit);

		// Sets text colour
		Color textCol = new Color(200, 25, 127);
		textArea.setDisabledTextColor(textCol);

		// Sets basic window parameters
		this.setTitle("Historical Game Statistics Report");
		this.setSize(600, 400);
		this.setLocation(300, 200);
		this.setVisible(false);

		// Adds textArea and buttons
		GridLayout grid = new GridLayout(2, 1);
		JPanel bigPan = new JPanel(grid);

		bigPan.add(textArea);
		bigPan.add(pan);

		add(bigPan);

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
	 * @param DB,
	 *            a TrumpsDBI database interaction object
	 */
	public void buildReport(TrumpsDBI DB) {

		// Open database connection.
		boolean connected = DB.establishDbConnection();

		if (!connected) {
			JOptionPane.showMessageDialog(null, "Connection to database failed");
			this.setVisible(false);
		}

		else {

			this.setVisible(true);
			int numGames = DB.getNumGames();
			int numHumanW = DB.getNumHumanWins();
			int numCompW = numGames - numHumanW;
			double aveNumDraws = DB.getAveDraws();
			int maxNumR = DB.getMaxRounds();

			// Close database connection
			DB.closeDbConnection();

			String numGamesPlayed = String.format("Number of games played " + "= %d%n%n", numGames);

			String numCompWins = String.format("Number of computer wins " + "= %d%n%n", numCompW);

			String numHumanWins = String.format("Number of human wins " + "= %d%n%n", numHumanW);

			String avNumDraws = String.format("Average number of draws " + "= %3.2f%n%n", aveNumDraws);

			String maxNumRounds = String.format("Highest number of rounds played " + "in a single game " + "= %d%n%n",
					maxNumR);

			this.displayText = numGamesPlayed + numCompWins + numHumanWins + avNumDraws + maxNumRounds;

			// Print report to screen
			this.textArea.setText(displayText);
		}
	}

	/**
	 * Saves the generated report into a .txt file
	 */
	public void saveReport() {
		try {
			FileWriter writer = new FileWriter("TopTrumpsReport.txt");
			System.out.println(displayText);

			writer.write(displayText);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carries out the corresponding actions when the user clicks a the save and
	 * exit/exit buttons.
	 *
	 * @param e,
	 *            ActionEvent
	 */
	public void actionPerformed(java.awt.event.ActionEvent e) {
		if (e.getSource() == saveExit) {
			saveReport();
			this.setVisible(false);
		} else {
			this.setVisible(false);
		}
	}
}
