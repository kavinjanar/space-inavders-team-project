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
	
	public void hitShip() {
		lives--;
	}
	
	public int getLives() { return lives; }
	
	public void addLife() {
		lives++;
	}
	
	public boolean isDestroyed() { return lives <= 0; }
}
