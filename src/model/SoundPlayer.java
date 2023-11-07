package model;

import java.io.File;
import java.net.URI;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayer {
	private MediaPlayer currentMediaPlayer;
	private MediaPlayer shootSoundPlayer;
	private MediaPlayer explosionSoundPlayer;
	private MediaPlayer invaderKilledSoundPlayer;
	private static Media bulletSound;
	private static Media explosionSound;
	private static Media invaderKilledSound;

	public SoundPlayer() {
		initSounds();
		playThemeSongLoop();
	}

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

	public void playShootSound() {
		shootSoundPlayer = new MediaPlayer(bulletSound);
		shootSoundPlayer.play();
	}

	public void playExplosionSound() {
		explosionSoundPlayer = new MediaPlayer(explosionSound);
		explosionSoundPlayer.play();
	}

	public void playInvaderKilledSound() {
		invaderKilledSoundPlayer = new MediaPlayer(invaderKilledSound);
		invaderKilledSoundPlayer.play();
	}

	public void playSoundFile(String name) {
		String path = ("sounds/" + name + ".mp3");
		File file = new File(path);
		URI uri = file.toURI();
		Media media = new Media(uri.toString());

		currentMediaPlayer = new MediaPlayer(media);
		currentMediaPlayer.play();
	}
}
