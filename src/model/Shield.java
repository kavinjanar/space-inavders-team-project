package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Shield extends ImageView {
	private int health;
	private static final int INITIAL_HEALTH = 4;
	
	public Shield(double x, double y, int size) {
		health = INITIAL_HEALTH;
		updateImage();
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.setFitWidth(size);
		this.setFitHeight(size);
	}

	public int getHealth() { return health; }
				
	public boolean hit() {
		if (health == 0)
			return false;
		health--;
		updateImage();
		return true;
	}
		
	public void resetHealth() {
		health = INITIAL_HEALTH;
		updateImage();
	}
	
	private void updateImage() {
		this.setImage(new Image("file:images/newShield" + health + ".png"));
	}
}
