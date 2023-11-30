package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * The {@code Alien} class represents an alien entity in the game.
 * It extends {@code Pane} and manages images representing different states of the alien,
 * such as its normal appearance and an explosion state when it is destroyed.
 */
public class Alien extends Pane {
    protected ImageView imageView;
    protected Image image1;
    protected Image image2;
    protected boolean usingFirstImage;
    protected Image explosionImage;
    private boolean isAlive = true;

    /**
     * Constructs an Alien object with specified images and size.
     * 
     * @param imageUrl1          The URL of the first image for the alien.
     * @param imageUrl2          The URL of the second image for the alien.
     * @param explosionImageUrl  The URL of the explosion image for the alien.
     * @param width              The width to set for the alien images.
     */
    public Alien(String imageUrl1, String imageUrl2, String explosionImageUrl, int width) {
        this.image1 = new Image(imageUrl1);
        this.image2 = new Image(imageUrl2);
        this.explosionImage = new Image(explosionImageUrl);
        
        // Initially set the image view to the first image
        this.imageView = new ImageView(image1);
        this.usingFirstImage = true;

        this.imageView.setFitWidth(width);
        this.imageView.setFitHeight(50);
        getChildren().add(this.imageView);
    }
    
    /**
     * Switches between the first and second image of the alien.
     * If the alien is not alive, this method does nothing.
     */
    public void switchImage() {
    	if (!isAlive) return;
        if (usingFirstImage) {
            imageView.setImage(image2);
            usingFirstImage = false;
        } else {
            imageView.setImage(image1);
            usingFirstImage = true;
        }
    }
    
    /**
     * Changes the alien's image to an explosion image and sets its state as not alive.
     */
    public void explode() {
        imageView.setImage(explosionImage);
        isAlive = false;
    }
    
    /**
     * Returns the alive status of the alien.
     * 
     * @return {@code true} if the alien is alive, {@code false} otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }
}
