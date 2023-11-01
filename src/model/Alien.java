package model;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view_controller.SpaceInvadersGUI;

public class Alien extends SpaceInvadersGUI {
    protected ImageView imageView;

    public Alien(String imageUrl) {
        Image image = new Image(imageUrl);
        this.imageView = new ImageView(image);
        getChildren().add(this.imageView);
    }
}

public class Alien1 extends Alien {
    public Alien1() {
        super("alien1.png");
       
    }
}

public class Alien2 extends Alien {
    public Alien2() {
        super("alien2.png");
    }
}

public class Alien3 extends Alien {
    public Alien3() {
        super("alien3.png");
    }
}
