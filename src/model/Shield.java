package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Shield {
	private int health;
	private static final int INITIAL_HEALTH = 4;
	private int x, y;
	private ImageView image;
	
	public Shield(int x, int y, int size) {
		health = INITIAL_HEALTH;
		image = new ImageView();
		updateImage();
		image.setLayoutX((double)x);
		image.setLayoutY((double)y);
		image.setFitWidth(size);
		image.setFitHeight(size);
		this.x = x;
		this.y = y;
	}

	public int getHealth() { return health; }
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public ImageView getImageView() { return image; }
	
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
		image.setImage(new Image("file:images/Shield" + health + ".png"));
	}
}
