package view_controller;

import java.io.Serializable;

/**
 * The {@code LeaderboardEntry} class represents an entry in a leaderboard. This
 * class stores the name of the player and their score. It implements
 * {@code Serializable} to allow leaderboard entries to be saved to and loaded
 * from a file.
 */
public class LeaderboardEntry implements Serializable {
	private static final long serialVersionUID = 1L; // Recommended for Serializable classes

	private String name;
	private int score;

	/**
	 * Constructs a new {@code LeaderboardEntry} with the specified name and score.
	 *
	 * @param name  The name of the player.
	 * @param score The score achieved by the player.
	 */
	public LeaderboardEntry(String name, int score) {
		this.name = name;
		this.score = score;
	}

	/**
	 * Returns the name of the player.
	 *
	 * @return The player's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the player.
	 *
	 * @param name The name to be set for the player.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the score of the player.
	 *
	 * @return The player's score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the score for the player.
	 *
	 * @param score The score to be set for the player.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Returns a string representation of the leaderboard entry, typically combining
	 * the player's name and score.
	 *
	 * @return A string representation of the leaderboard entry.
	 */
	@Override
	public String toString() {
		return name + ": " + score;
	}
}
