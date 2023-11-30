package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The SpaceShip class represents a player's spaceship in a Space Invaders game.
 * It extends ImageView to display the spaceship as an image.
 */
public class SpaceShip extends ImageView {
	private int lives;
	private int originalLives;
	private int speed;
	private Bullet bullet;
	private String name;
	private static final double bulletSpeed = 12;
	public boolean moveLeft;
	public boolean moveRight;

	/**
	 * Constructs a SpaceShip object with the specified image, name, speed, and
	 * initial lives.
	 *
	 * @param image The image representing the spaceship.
	 * @param name  The name of the spaceship.
	 * @param speed The speed of the spaceship.
	 * @param lives The initial number of lives for the spaceship.
	 */
	public SpaceShip(Image image, String name, int speed, int lives) {
		super(image);
		this.lives = lives;
		this.speed = speed;
		this.name = name;
		this.originalLives = lives;
	}

	/**
	 * Decreases the number of lives when the spaceship is hit.
	 */
	public void hitShip() {
		lives--;
	}

	/**
	 * Gets the current number of lives of the spaceship.
	 *
	 * @return The number of lives.
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Gets the speed of the spaceship.
	 *
	 * @return The speed of the spaceship.
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Gets the name of the spaceship.
	 *
	 * @return The name of the spaceship.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Increases the number of lives for the spaceship.
	 */
	public void addLife() {
		lives++;
	}

	/**
	 * Gets the original number of lives the spaceship had when initialized.
	 *
	 * @return The original number of lives.
	 */
	public int getOriginalLives() {
		return originalLives;
	}

	/**
	 * Checks if the spaceship is destroyed (has no lives remaining).
	 *
	 * @return True if the spaceship is destroyed, false otherwise.
	 */
	public boolean isDestroyed() {
		return lives <= 0;
	}

	/**
	 * Gets the bullet fired by the spaceship.
	 *
	 * @return The bullet object.
	 */
	public Bullet getBullet() {
		return bullet;
	}

	/**
	 * Fires a bullet from the spaceship at the current position.
	 */
	public void fireBullet() {
		bullet = new Bullet((int) (this.getLayoutX() + this.getFitWidth() / 2 - 5),
				(int) (this.getLayoutY() - this.getFitHeight() / 2), -1 * (int) bulletSpeed, false);
	}

	/**
	 * Deletes the bullet object, indicating that the bullet is no longer active on
	 * the screen.
	 */
	public void deleteBullet() {
		bullet = null;
	}

	/**
	 * Changes the spaceship to a "Speedy Ship" with adjusted speed and lives.
	 */
	public void changeToSpeedyShip() {
		name = "Speedy Ship";
		speed = 8;
		lives = 2;
		originalLives = 2;
		Image speedyShipImage = new Image("file:images/Spaceship2.png");
		this.setImage(speedyShipImage);
	}

	/**
	 * Changes the spaceship to a "Chunky Ship" with adjusted speed and lives.
	 */
	public void changeToBulkyShip() {
		name = "Chunky Ship";
		speed = 2;
		lives = 5;
		originalLives = 5;
		Image chunkyShipImage = new Image("file:images/Spaceship3.png");
		this.setImage(chunkyShipImage);
	}

	/**
	 * Changes the spaceship to a "Balanced Ship" (default/starter ship) with
	 * adjusted speed and lives.
	 */
	public void changeToBalancedShip() {
		name = "Balanced Ship";
		speed = 5;
		lives = 3;
		originalLives = 3;
		Image balancedShipImage = new Image("file:images/Spaceship.png");
		this.setImage(balancedShipImage);
	}
}
