package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet {
	private int speed;
	private int startX;
	private int startY;
	private boolean fromEnemy;
	private ImageView image;
	
	public Bullet(int x, int y, int speed, boolean fromEnemy) {
		this.startX = x;
		this.startY = y;
		this.speed = speed;
		this.fromEnemy = fromEnemy;
		if (fromEnemy)
			image = new ImageView(new Image("file:images/enemylaser.png"));
		else
			image = new ImageView(new Image("file:images/Bullet.png"));
		image.setFitWidth(10);
		image.setFitHeight(25);
		image.setLayoutX(x);
		image.setLayoutY(y);
	}
	
	public int getStartX() { return startX; }
	
	public int getStartY() { return startY; }
	
	public int getSpeed() { return speed; }
	
	public boolean isFromEnemy() { return fromEnemy; }
	
	public boolean withinBounds(double x1, double y1, double x2, double y2) {
		return image.getLayoutX() + image.getFitWidth() / 2 >= x1 && image.getLayoutX() + image.getFitWidth() / 2 <= x2 
				&& image.getLayoutY() + image.getFitHeight() >= y1 && image.getLayoutY() <= y2;
	}
	
	public ImageView getImageView() { return image; }
}
