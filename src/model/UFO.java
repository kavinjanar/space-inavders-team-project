package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class UFO extends Pane {
	protected ImageView imageView;
	protected Image image1;
	protected boolean usingFirstImage;
	protected Image explosionImage;
	private boolean isAlive = true;

    public UFO() {
        this.image1 = new Image("file:images/ufo.png");
        this.explosionImage = new Image("file:images/explosionred.png");
        
        // Initially set the image view to the first image
        this.imageView = new ImageView(image1);
        this.usingFirstImage = true;

        this.imageView.setFitWidth(100);
        this.imageView.setFitHeight(40);
        //this.imageView.setPreserveRatio(true);
        getChildren().add(this.imageView);
    }
        
    public boolean isAlive() {
        return isAlive;
    }
    
    public void revive() {
    	this.imageView.setImage(image1);
    	isAlive = true;
    }
    
    /**
     * switches alien image to explode
     */
    public void explode() {
        imageView.setImage(explosionImage);
        isAlive = false;
    }
}
