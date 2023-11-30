package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a shield in Space Invaders. Gets slowly destroyed and loses health
 * each time it is hit, and disappears when health becomes 0.
 */
public class Shield extends ImageView {
	private int health;
	private static final int INITIAL_HEALTH = 4;

	/**
	 * Constructor for the Shield class. Takes in an x and y coordinate and size,
	 * and initializes all instance variables
	 * 
	 * @param x    X position on the main pane
	 * @param y    Y position on the main pane
	 * @param size Size in pixels of the Shield
	 */
	public Shield(double x, double y, int size) {
		health = INITIAL_HEALTH;
		updateImage();
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setFitWidth(size);
		this.setFitHeight(size);
	}

	/**
	 * Getter method for shield health
	 * 
	 * @return int Hits left until destroyed
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Called when something hits the shield and damages it
	 * 
	 * @return true if not destroyed and image is updated, false if shield is
	 *         already destroyed
	 */
	public boolean hit() {
		if (health == 0)
			return false;
		health--;
		updateImage();
		return true;
	}

	/**
	 * Sets health back to what it was when initialized
	 */
	public void resetHealth() {
		health = INITIAL_HEALTH;
		updateImage();
	}

	/**
	 * Updates the image used based on the health of the shield
	 */
	private void updateImage() {
		this.setImage(new Image("file:images/newShield" + health + ".png"));
	}
}
