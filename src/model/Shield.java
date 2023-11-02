package model;

public class Shield {
	private int health;
	private static final int INITIAL_HEALTH = 4;
	private int x, y;
	
	public Shield(int x, int y) {
		health = INITIAL_HEALTH;
		this.x = x;
		this.y = y;
	}

	public int getHealth() { return health; }
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public void hit() {
		health--;
	}
		
	public void resetHealth() {
		health = INITIAL_HEALTH;
	}
}
