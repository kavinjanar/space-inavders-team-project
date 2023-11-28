package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Alien extends Pane {
    protected ImageView imageView;
    protected Image image1;
    protected Image image2;
    protected boolean usingFirstImage;
    protected Image explosionImage;
    private boolean isAlive = true;

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
     * switches between image1 and image2, and vice versa
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
     * switches alien image to explode
     */
    public void explode() {
        imageView.setImage(explosionImage);
        isAlive = false;
    }
    
    public boolean isAlive() {
        return isAlive;
    }
}
