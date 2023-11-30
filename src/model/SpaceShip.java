package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpaceShip extends ImageView {
	private int lives;
	private int originalLives;
	private Bullet bullet;
	private int speed;
	private String name;
	private static final double bulletSpeed = 12;
	
	public boolean moveLeft;
	public boolean moveRight;
	
	public SpaceShip(Image image, String name, int speed, int lives)
	{
		super(image);
		this.lives = lives;
		this.speed = speed;
		this.name = name;
		this.originalLives = lives;
		
	}
	
	public void hitShip() {
		lives--;
	}
	
	public int getLives() { return lives; }
	
	public int getSpeed() { return speed; }
	
	public String getName() { return name; }
	
	public void addLife() {
		lives++;
	}
	
	public int getOriginalLives() { return originalLives; }
		
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
	
	public void changeToSpeedyShip()
	{
		name = "Speedy Ship";
		speed = 8;
		lives = 2;
		originalLives = 2;
		Image speedyShipImage = new Image("file:images/Spaceship2.png");
		this.setImage(speedyShipImage);
	}
	
	public void changeToBulkyShip()
	{
		name = "Chunky Ship";
		speed = 2;
		lives = 5;
		originalLives = 5;
		Image chunkyShipImage = new Image("file:images/Spaceship3.png");
		this.setImage(chunkyShipImage);
	}
	
	public void changeToBalancedShip()
	{
		name = "Balanced Ship";
		speed = 5;
		lives = 3;
		originalLives = 3;
		Image balancedShipImage = new Image("file:images/Spaceship.png");
		this.setImage(balancedShipImage);
	}
	
}
