package model;

import java.io.File;
import java.net.URI;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer {
	private MediaPlayer currentMediaPlayer; // Store the current MediaPlayer
	private static String menuSong = "SpaceInvadersTheme";

	public SoundPlayer() {
		playThemeSongLoop();
	}

	public void playThemeSongLoop() {
		String path = ("sounds/" + menuSong + ".mp3");
		File file = new File(path);
		URI uri = file.toURI();

		System.out.println(uri);

		Media media = new Media(uri.toString());
		currentMediaPlayer = new MediaPlayer(media);
		currentMediaPlayer.play();
		currentMediaPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				playThemeSongLoop();
			}
		});
	}

	public void playSoundFile(String name) {
		String path = ("sounds/" + name + ".mp3");
		File file = new File(path);
		URI uri = file.toURI();

		System.out.println(uri);

		Media media = new Media(uri.toString());
		currentMediaPlayer = new MediaPlayer(media);
		currentMediaPlayer.play();
		currentMediaPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				// TODO
			}
		});
	}
}
