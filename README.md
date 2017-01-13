# TeamATopTrumps
Team A Top Trumps Programme for Group Project 

## Project description
A Top Trumps game featuring a GUI and a database connection to store data.

## Participants
+ Faisal Ahsan 2242114a 
+ Aidan Butler 2281611b 
+ Stewart Brown 2276998b
+ Jane Kennedy 2287767k 
+ Svetoslava Nikolova 1004630n

## Proposed course of action 
Move through the following list, ensuring that each step is completed before we move on to the next:

1. Thorough testing of the current version to ensure that it meets all given specifications.

2. All bugs / errors should be reported as GitHub issues and be marked resolved once they're fixed.

3. Incremental improvement.

4. Including repeated, thorough testing.

5. Gold plating.

## Debugging/Testing notes

To change to a different deck file, alter the values of `this.DECK_FILE_NAME = "deck.txt"` and the corresponding value of `this.NUM_CARDS = 40` at the top of `TopTrumpsGUI.java`.

To disable deck shuffling for testing, comment out the `this.currentDeck.shuffleDeck();` in `TopTrumpsGUI.generateDeck()`.

To set up the database for work on your computer, go to `TrumpsDBI.establishDbConnection()` and put in locally appropriate values for `String dbname = "m_16_2281611b";`, `String username = "m_16_2281611b";`, `String password = "2281611b";`.

## Towards consistent formatting:
+ Give each method an appropriate Java doc comment
+ Keep methods short: each method should do one thing and contain a maximum of 30 lines of code.
+ Be generous with vretical space
+ Stick to an 80 character width limit

## Specification notes
+ In the lab on Wednesday January 11, Rose said that it would it was not necessary for us to store information on separate computer players in the database.
