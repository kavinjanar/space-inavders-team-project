package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpaceShip extends ImageView {
	private int lives;
	public SpaceShip(Image image)
	{
		super(image);
		lives = 3;
	}
	
	public void hit() {
		lives--;
	}
	
	public int getLives() { return lives; }
	
	public boolean outOfLives() { return lives == 0; }
}
