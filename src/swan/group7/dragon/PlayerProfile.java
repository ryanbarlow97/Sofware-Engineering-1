package swan.group7.dragon;

import java.util.ArrayList;

/**
 * PlayerProfile.java Used to store and update data relevant to the current and
 * past players.
 * 
 * @author Mollie Stamp, Corben Thompson (JavaDoc MollieStamp)
 * @version 1.5
 */
public class PlayerProfile {

	private String name; // player's name
	private ArrayList<Integer> lvlsHighScore; // stores the best time of each completed level as the game progresses
	private int maxLevel; // the highest unlocked level for the player

	/**
	 * Constructor for a brand new player, and assigns maxlevel as 0 as they have
	 * never completed a level before.
	 * 
	 * @param name String of player's name.
	 */
	public PlayerProfile(String name) {
		ArrayList<Integer> lvl = new ArrayList<>();
		this.lvlsHighScore = lvl;
		this.name = name;
		this.maxLevel = 1;
	}

	/**
	 * Constructor for a player who has already played at least one level.
	 * 
	 * @param name          : Player's name
	 * @param maxLevel      : Maximum unlocked level
	 */
	public PlayerProfile(String name, int maxLevel) {
		this.name = name;
		this.maxLevel = maxLevel;
	}

	/**
	 * To get the name of the player.
	 * 
	 * @return name String of player's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method is used to get the player's highest unlocked level.
	 * 
	 * @return maxLevel Highest level achieved.
	 */
	public int getMaxlvl() {
		return maxLevel;
	}

	/**
	 * This method is used to get the list of players highscores on levels.
	 * 
	 * @return lvlsHighScore Highest score on levels.
	 */
	public ArrayList<Integer> getHighScores() {
		return lvlsHighScore;
	}

	/**
	 * This gets the players fastest time on a given level.
	 * 
	 * @param lvl The level being queried.
	 * @return lvlsHighScore Th best time of level.
	 */
	public int getBestTime(int lvl) {
		if (lvl - 1 > lvlsHighScore.size() || lvl - 1 < lvlsHighScore.size()) {
			return -1;
		} else {
			return lvlsHighScore.get(lvl);
		}
	}

	/**
	 * This is used to update the current best time of a given level.
	 * 
	 * @param lvl   The level being queried.
	 * @param score The score to compare and possibly replace.
	 */
	public void updateLvl(int lvl, int score) {
		if (lvl - 1 > lvlsHighScore.size() || lvl - 1 < lvlsHighScore.size()) { // if level is less than or more than
																				// the levels stored, don't proceed
			System.out.println("Impossible level number for current player");
		} else {
			if (lvlsHighScore.get(lvl) >= score) {
				// don't change time
			} else {
				lvlsHighScore.set(lvl, score);
			}
		}
		if (lvl > maxLevel) {
			maxLevel = lvl;
			lvlsHighScore.add(score);
		}
	}

	/**
	 * Forms a toString with name and level achieved.
	 */
	@Override
	public String toString() {
		String output = name + " " + maxLevel;
		if (lvlsHighScore.size() != 0) {
			for (int i = 0; i < lvlsHighScore.size(); i++) {
				output += " " + lvlsHighScore.get(i);
			}
		}
		return output;
	}
}