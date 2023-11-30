package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a bullet fired either by the player or enemy. A bullet from a
 * player uses a different image than one from an enemy.
 */
public class Bullet {
	private double speed;
	private double startX;
	private double startY;
	private boolean fromEnemy;
	private ImageView image;

	/**
	 * Default constructor for a Bullet. Defines starting position, speed, and
	 * whether or not it is from an enemy, and initializes the image based on who it
	 * is from.
	 * 
	 * @param x         X coordinate of starting position
	 * @param y         Y coordinate of starting position
	 * @param speed     Number of pixels to move every update
	 * @param fromEnemy Whether or not the bullet comes from an enemy
	 */
	public Bullet(double x, double y, double speed, boolean fromEnemy) {
		this.startX = x;
		this.startY = y;
		this.speed = speed;
		this.fromEnemy = fromEnemy;
		if (fromEnemy)
			image = new ImageView(new Image("file:images/enemylaser.png"));
		else
			image = new ImageView(new Image("file:images/Bullet.png"));
		image.setFitWidth(10);
		image.setFitHeight(25);
		image.setLayoutX(x);
		image.setLayoutY(y);
	}

	/**
	 * Returns the X coordinate of the starting position
	 * 
	 * @return Starting X coordinate of the bullet
	 */
	public double getStartX() {
		return startX;
	}

	/**
	 * Returns the Y coordinate of the starting position
	 * 
	 * @return Starting Y coordinate of the bullet
	 */
	public double getStartY() {
		return startY;
	}

	/**
	 * Returns the bullet's speed
	 * 
	 * @return Speed of the bullet
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Whether or not the bullet was shot by an alien
	 * 
	 * @return true if shot by an alien, false if shot by a player
	 */
	public boolean isFromEnemy() {
		return fromEnemy;
	}

	/**
	 * Determines if the bullet is inside the rectangle defined by the parameters
	 * 
	 * @param x1 X coordinate of top left corner of rectangle
	 * @param y1 Y coordinate of top left corner of rectangle
	 * @param x2 X coordinate of bottom right corner of rectangle
	 * @param y2 Y coordinate of bottom right corner of rectangle
	 * @return
	 */
	public boolean withinBounds(double x1, double y1, double x2, double y2) {
		return image.getLayoutX() + image.getFitWidth() / 2 >= x1 && image.getLayoutX() + image.getFitWidth() / 2 <= x2
				&& image.getLayoutY() + image.getFitHeight() >= y1 && image.getLayoutY() <= y2;
	}

	/**
	 * Returns the ImageView used by the bullet
	 * 
	 * @return The JavaFX ImageView representing the bullet
	 */
	public ImageView getImageView() {
		return image;
	}
}
