package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Alien extends Pane {
    protected ImageView imageView;

    public Alien(String imageUrl) {
        Image image = new Image(imageUrl);
        this.imageView = new ImageView(image);
        getChildren().add(this.imageView);
    }
}
