package model;

/**
 * The {@code Alien2} class represents a specific type of alien, extending the
 * {@code Alien} class. It initializes an alien with a distinct set of images
 * and size, which are unique to the {@code Alien2} type.
 */
public class Alien2 extends Alien {
	/**
	 * Constructs an {@code Alien2} object with predefined images for its normal and
	 * explosion states, and sets its size. The images are defined as follows: -
	 * Normal state images: "file:images/enemy2_1.png" and
	 * "file:images/enemy2_2.png" - Explosion state image:
	 * "file:images/explosionblue.png" The width of the alien is set to 60 pixels.
	 */
	public Alien2() {
		super("file:images/enemy2_1.png", "file:images/enemy2_2.png", "file:images/explosionblue.png", 60);
	}
}
