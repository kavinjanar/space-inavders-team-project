package model;

import java.io.File;
import java.net.URI;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The SoundPlayer class provides methods for playing various sounds in the
 * Space Invaders game. It includes functionality to play the theme song on
 * loop, as well as individual sounds for shooting, explosions, and invader
 * kills.
 */
public class SoundPlayer {
	private MediaPlayer currentMediaPlayer;
	private MediaPlayer shootSoundPlayer;
	private MediaPlayer explosionSoundPlayer;
	private MediaPlayer invaderKilledSoundPlayer;
	private static Media bulletSound;
	private static Media explosionSound;
	private static Media invaderKilledSound;

	/**
	 * Construct the SoundPlayer object, initializing sound files and beginning the
	 * theme song loop.
	 */
	public SoundPlayer() {
		initSounds();
		playThemeSongLoop();
	}

	/**
	 * Load and play the Space Invaders theme song on repeat.
	 */
	public void playThemeSongLoop() {
		String path = ("sounds/SpaceInvadersTheme.mp3");
		File file = new File(path);
		URI uri = file.toURI();
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

	/**
	 * Initialize the sounds by loading their respective files as Media objects.
	 */
	public void initSounds() {
		File shootFile = new File("sounds/shoot.wav");
		URI shootURI = shootFile.toURI();
		File explosionFile = new File("sounds/explosion.wav");
		URI explosionURI = explosionFile.toURI();
		File invaderKilledFile = new File("sounds/invaderkilled.wav");
		URI invaderKilledURI = invaderKilledFile.toURI();

		bulletSound = new Media(shootURI.toString());
		explosionSound = new Media(explosionURI.toString());
		invaderKilledSound = new Media(invaderKilledURI.toString());
	}

	/**
	 * Play the "shoot" sound.
	 */
	public void playShootSound() {
		shootSoundPlayer = new MediaPlayer(bulletSound);
		shootSoundPlayer.play();
	}

	/**
	 * Play the "explosion" sound.
	 */
	public void playExplosionSound() {
		explosionSoundPlayer = new MediaPlayer(explosionSound);
		explosionSoundPlayer.play();
	}

	/**
	 * Play the "invader killed" sound.
	 */
	public void playInvaderKilledSound() {
		invaderKilledSoundPlayer = new MediaPlayer(invaderKilledSound);
		invaderKilledSoundPlayer.play();
	}

	/**
	 * Play a sound file given the name of a file in the sounds directory.
	 * 
	 * @param name The name of the file in the sounds directory.
	 */
	public void playSoundFile(String name) {
		String path = ("sounds/" + name + ".mp3");
		File file = new File(path);
		URI uri = file.toURI();
		Media media = new Media(uri.toString());

		currentMediaPlayer = new MediaPlayer(media);
		currentMediaPlayer.play();
	}
}
