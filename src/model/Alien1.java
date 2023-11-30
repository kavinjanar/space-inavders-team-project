package model;

/**
 * The {@code Alien1} class represents a specific type of alien, extending the general {@code Alien} class.
 * It initializes an alien with pre-defined images and size, specific to the {@code Alien1} type.
 */
public class Alien1 extends Alien {
	
	/**
     * Constructs an {@code Alien1} object with pre-defined images for its normal and explosion states,
     * and sets its size.
     * The images are defined as follows:
     * - Normal state images: "file:images/enemy1_1.png" and "file:images/enemy1_2.png"
     * - Explosion state image: "file:images/explosiongreen.png"
     * The width of the alien is set to 70 pixels.
     */
    public Alien1() {
        super("file:images/enemy1_1.png", "file:images/enemy1_2.png", "file:images/explosiongreen.png",70);
    }
}
