package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet {
	private double speed;
	private double startX;
	private double startY;
	private boolean fromEnemy;
	private ImageView image;
	
	public Bullet(double x, double y, double speed, boolean fromEnemy) {
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
	
	public double getStartX() { return startX; }
	
	public double getStartY() { return startY; }
	
	public double getSpeed() { return speed; }
	
	public boolean isFromEnemy() { return fromEnemy; }
	
	public boolean withinBounds(double x1, double y1, double x2, double y2) {
		return image.getLayoutX() + image.getFitWidth() / 2 >= x1 && image.getLayoutX() + image.getFitWidth() / 2 <= x2 
				&& image.getLayoutY() + image.getFitHeight() >= y1 && image.getLayoutY() <= y2;
	}
	
	public ImageView getImageView() { return image; }
}
