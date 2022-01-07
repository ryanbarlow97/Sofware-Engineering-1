package swan.group7.dragon;

import java.util.ArrayList;

/**
 * LeaderBoard.java Represents the highscore tables/leaderboards.
 * 
 * @author Mollie Stamp (JavaDoc Mollie Stamp)
 * @version 1.2
 */
public class LeaderBoard {

	private ArrayList<Integer> position;
	private ArrayList<String> name; // player's name
	private ArrayList<Integer> highScore; // stores the best time of each completed level as the game progresses

	/**
	 * Constructor to create leaderboard, with a list of positions, players names
	 * and their scores.
	 * 
	 * @param position  ArrayList of ints containing 1st, 2nd etc.
	 * @param name      ArrayList of strings containing players names.
	 * @param highScore ArrayList of ints contatining highscores.
	 */
	public LeaderBoard(ArrayList<Integer> position, ArrayList<String> name, ArrayList<Integer> highScore) {
		super();
		this.position = position;
		this.name = name;
		this.highScore = highScore;
	}

	/**
	 * This method returns list of positions.
	 * 
	 * @return position ArrayList of ints containing 1st, 2nd etc.
	 */
	public ArrayList<Integer> getPosition() {
		return position;
	}

	/**
	 * This method sets positions.
	 * 
	 * @param position ArrayList of ints containing 1st, 2nd etc.
	 */
	public void setPosition(ArrayList<Integer> position) {
		this.position = position;
	}

	/**
	 * This method gets the list of names.
	 * 
	 * @return name ArrayList of strings containing players names.
	 */
	public ArrayList<String> getName() {
		return name;
	}

	/**
	 * This method sets the names.
	 * 
	 * @param name ArrayList of strings containing players names.
	 */
	public void setName(ArrayList<String> name) {
		this.name = name;
	}

	/**
	 * This returns the list of highscores.
	 * 
	 * @return highScore ArrayList of ints contatining highscores.
	 */
	public ArrayList<Integer> getHighScore() {
		return highScore;
	}

	/**
	 * This sets the highscores.
	 * 
	 * @param highScore ArrayList of ints contatining highscores.
	 */
	public void setHighScore(ArrayList<Integer> highScore) {
		this.highScore = highScore;
	}

}
