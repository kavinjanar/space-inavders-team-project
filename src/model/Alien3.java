package model;

/**
 * The {@code Alien3} class represents a specific type of alien, extending the {@code Alien} class.
 * This class initializes an alien with a unique set of images and size, specific to the {@code Alien3} type.
 */
public class Alien3 extends Alien {

    /**
     * Constructs an {@code Alien3} object with predefined images for its normal and explosion states,
     * and sets its size.
     * The images are defined as follows:
     * - Normal state images: "file:images/enemy3_1.png" and "file:images/enemy3_2.png"
     * - Explosion state image: "file:images/explosionpurple.png"
     * The width of the alien is set to 50 pixels.
     */
    public Alien3() {
        super("file:images/enemy3_1.png", "file:images/enemy3_2.png", "file:images/explosionpurple.png", 50);
    }
}
