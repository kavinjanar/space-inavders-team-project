package view_controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code LeaderboardManager} class provides static methods to save and load
 * leaderboard data. It handles I/O operations to persist leaderboard entries to
 * a file and retrieve them.
 */
public class LeaderboardManager {

	/**
	 * Saves a list of {@code LeaderboardEntry} objects to a file. The method uses
	 * object serialization to write the leaderboard entries to the specified file.
	 *
	 * @param entries  The list of {@code LeaderboardEntry} objects to be saved.
	 * @param fileName The name of the file where the leaderboard data is to be
	 *                 saved.
	 */
	public static void saveLeaderboard(List<LeaderboardEntry> entries, String fileName) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
			oos.writeObject(entries);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads a list of {@code LeaderboardEntry} objects from a file. The method uses
	 * object deserialization to read the leaderboard entries from the specified
	 * file. In case of any I/O errors or if the class of a serialized object cannot
	 * be found, an empty list is returned.
	 *
	 * @param fileName The name of the file from which the leaderboard data is to be
	 *                 loaded.
	 * @return A list of {@code LeaderboardEntry} objects; returns an empty list if
	 *         an error occurs.
	 */
	@SuppressWarnings("unchecked")
	public static List<LeaderboardEntry> loadLeaderboard(String fileName) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
			return (List<LeaderboardEntry>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return new ArrayList<>(); // Return an empty list in case of an error
		}
	}
}
