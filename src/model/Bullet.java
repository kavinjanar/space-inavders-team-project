package model;

import javafx.scene.image.Image;

public class Bullet {
	private int speed;
	private int startX;
	private int startY;
	
	public Bullet(int x, int y, int speed) {
		this.startX = x;
		this.startY = y;
		this.speed = speed;
	}
	
	public int getStartX() { return startX; }
	
	public int getStartY() { return startY; }
	
	public int getSpeed() { return speed; }
}
