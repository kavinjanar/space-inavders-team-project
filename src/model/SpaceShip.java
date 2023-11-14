package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpaceShip extends ImageView {
	private int lives;
	private Bullet bullet;
	private static final double bulletSpeed = 12;
	
	public boolean moveLeft;
	public boolean moveRight;
	
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
	
	public Bullet getBullet() { return bullet; }
	
	public void fireBullet() {
		bullet = new Bullet(
				(int)(this.getLayoutX() + this.getFitWidth()/2 - 5), 
				(int)(this.getLayoutY() - this.getFitHeight()/2), 
				-1*(int)bulletSpeed, 
				false);
	}
	
	public void deleteBullet() {
		bullet = null;
	}
}
