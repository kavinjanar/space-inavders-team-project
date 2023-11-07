package view_controller;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

public class SpaceInvadersGUI extends Application {
	private Pane pane = new Pane();
	private TutorialPane tutorialPane = new TutorialPane();
	private Button tutorialButton;
	private SpaceShip spaceship;
	private boolean moveLeft = false;
	private boolean moveRight = false;
	private boolean fireBullet = false;
	private final double playerSpeed = 5;
	private final double bulletSpeed = 12;
	private Bullet plyrBullet;
	private ArrayList<Bullet> enemyBullets = new ArrayList<>();
	private ArrayList<Shield> shields = new ArrayList<>();
	private Pane alienGridPane = new Pane();
	private boolean moveAliensRight = true;
	private final double alienMoveDistance = 20;
	private Alien[][] aliens;
	private Random randGen;
	private  double pauseDuration = 1;
	private Timeline currTimeline;
	private SoundPlayer soundPlayer;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		pane.setStyle("-fx-background-color: black;");
		// screen parameters
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		double screenWidth = screenBounds.getWidth();
		double screenHeight = screenBounds.getHeight();

		tutorialButton = new Button("Tutorial");
		tutorialButton.setPrefSize(100, 40);
		tutorialButton.setLayoutX(screenWidth / 2 - tutorialButton.getPrefWidth() / 2);
		tutorialButton.setLayoutY(screenHeight * 0.95);
		initTutorialWindow(stage);

		// space ship initialization
		Image spaceshipImage = new Image("file:images/Spaceship.png");
		spaceship = new SpaceShip(spaceshipImage);
		System.out.println("Image loaded? " + spaceshipImage.isError());

		spaceship.setFitWidth(50);
		spaceship.setFitHeight(60);
		spaceship.setPreserveRatio(true);
		spaceship.setLayoutX(screenWidth / 2 - spaceship.getFitWidth() / 2);
		spaceship.setLayoutY(screenHeight * 0.80);

		// Add shields
		for (int y = (int) (screenHeight * 0.65); y <= (int) (screenHeight * 0.70); y += (int) (screenHeight * 0.05)) {
			for (int offset = -1 * (int) (screenHeight * 0.05); offset <= (int) (screenHeight
					* 0.05); offset += (int) (screenHeight * 0.05)) {
				for (int x = (int) screenWidth / 10; x <= 9 * (int) screenWidth / 10; x += (int) screenWidth / 5) {
					if (y > (int) (screenHeight * 0.65) && offset == 0)
						continue;
					Shield shield = new Shield(x + offset - 12, y, (int) (screenHeight * 0.05));
					shields.add(shield);
					pane.getChildren().add(shield);
				}
			}
		}

		pane.getChildren().add(spaceship);
		pane.getChildren().add(tutorialButton);

		Scene scene = new Scene(pane, 600, 400);
		// Keyboard movement
		scene.setOnKeyPressed((event) -> {
			switch (event.getCode()) {
			case A:
				moveLeft = true;
				break;

			case D:
				moveRight = true;
				break;

			case SHIFT:
				fireBullet = true;
				break;

			default:
				break;
			}
		});
		scene.setOnKeyReleased((event) -> {
			switch (event.getCode()) {
			case A:
				moveLeft = false;
				break;

			case D:
				moveRight = false;
				break;

			case SHIFT:
				fireBullet = false;
				break;

			default:
				break;
			}
		});
		AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				// TODO Auto-generated method stub
				int dx = 0;

				if (moveLeft)
					dx -= playerSpeed;
				if (moveRight)
					dx += playerSpeed;

				spaceship.setLayoutX(spaceship.getLayoutX() + dx);

				// Reset bullet position if out of bounds
				if (plyrBullet != null) {
					ImageView playerBullet = plyrBullet.getImageView();
					if (playerBullet.getLayoutY() < -1*playerBullet.getFitHeight() && fireBullet || bulletHitSomething(plyrBullet, stage)) {
						pane.getChildren().remove(plyrBullet.getImageView());
						plyrBullet = null;
					}
					else
						playerBullet.setLayoutY(playerBullet.getLayoutY() + plyrBullet.getSpeed());
				}
				else if (fireBullet) {
					plyrBullet = new Bullet(
							(int)(spaceship.getLayoutX() + spaceship.getFitWidth()/2 - 5), 
							(int)(spaceship.getLayoutY() - spaceship.getFitHeight()/2), 
							-1*(int)bulletSpeed, 
							false);
					pane.getChildren().add(plyrBullet.getImageView());
					soundPlayer.playShootSound();
				}
				
				int i = 0;
				while (i < enemyBullets.size()) {
					Bullet bullet = enemyBullets.get(i);
					if (bulletHitSomething(bullet, stage)) {
						pane.getChildren().remove(bullet.getImageView());
						enemyBullets.remove(bullet);
					}
					else {
						bullet.getImageView().setLayoutY(bullet.getImageView().getLayoutY() + bullet.getSpeed());
					}
					i++;
				}

				double x = spaceship.getLayoutX();
				double width = spaceship.getBoundsInLocal().getWidth();

				if (x < 0)
					spaceship.setLayoutX(0);
				if (x + width > screenWidth)
					spaceship.setLayoutX(screenWidth - width);
			}
		};
		timer.start();

		stage.setFullScreen(true);
		stage.setTitle("Space Invaders");
		stage.setScene(scene);
		stage.show();
		stage.requestFocus();
		
		soundPlayer = new SoundPlayer();
		soundPlayer.playThemeSongLoop();

		// alien stuff
		randGen = new Random(System.currentTimeMillis());
		alienGrid(stage);
		currTimeline = moveAlienGrid(stage);

	}
		
	private boolean bulletHitSomething(Bullet bullet, Stage stage) {
		for (Shield shield : shields) {
			double shieldX = shield.getLayoutX();
			double shieldY = shield.getLayoutY();
			double shieldWidth = shield.getFitWidth();
			double shieldHeight = shield.getFitHeight();
			if (bullet.withinBounds(shieldX, shieldY, shieldX + shieldWidth, shieldY + shieldHeight)
			&& shield.getHealth() > 0) {
				shield.hit();
				return true;
			}
		}
		if (bullet.isFromEnemy()) {
			if (bullet.withinBounds(spaceship.getLayoutX(), spaceship.getLayoutY(), spaceship.getLayoutX() + spaceship.getFitWidth(), spaceship.getLayoutY() + spaceship.getFitHeight())) {
				spaceship.hitShip();
				if (spaceship.isDestroyed()) {
					soundPlayer.playExplosionSound();
				}
				double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
				spaceship.setLayoutX(screenWidth / 2 - spaceship.getFitWidth() / 2);
				return true;
			}
		}
		else {
			for (int i = 0; i < aliens.length; i++) {
				for (int j = 0; j < aliens[i].length; j++) {
					if (aliens[i][j] != null) {
						double alienX = alienGridPane.getLayoutX() + aliens[i][j].getLayoutX();
						double alienY = alienGridPane.getLayoutY() + aliens[i][j].getLayoutY();
						double alienSize = 50.0;
						if (bullet.withinBounds(alienX, alienY, alienX + alienSize, alienY + alienSize)) {
							System.out.println("Alien destroyed: " + alienX + " " + alienY);
							alienGridPane.getChildren().remove(aliens[i][j]);
							aliens[i][j] = null;
							if (allAliensDestroyed())
							{
								System.out.println("Increasing difficulty");
								increaseDifficulty(stage);
							}
							soundPlayer.playInvaderKilledSound();
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean allAliensDestroyed()
	{
		if (alienGridPane.getChildren().isEmpty())
		{
			return true;
		}
		return false;
	}

	private void initTutorialWindow(Stage stage) {
		Scene tutorialScene = new Scene(tutorialPane, 400, 300);
		Stage tutorialWindow = new Stage();

		// Forces application focus on tutorial window
		tutorialWindow.initOwner(stage);
		tutorialWindow.initModality(Modality.WINDOW_MODAL);

		tutorialButton.setOnAction(event -> {
			tutorialWindow.setTitle("Tutorial");
			tutorialWindow.setScene(tutorialScene);
			tutorialWindow.setHeight(300);
			tutorialWindow.setWidth(400);
			tutorialWindow.setResizable(false);
			tutorialWindow.setX(stage.getX() + 50);
			tutorialWindow.setY(stage.getY() + 50);
			tutorialWindow.show();
		});
	}

	/**
	 * sets up the grid of aliens
	 * 
	 * @param stage
	 */
	private void alienGrid(Stage stage) {
	    int rows = 5;
	    int cols = 11;
	    
	    double alienWidth = 90; // Width of an individual alien (including spacing)
	    double alienHeight = 65; // Height of an individual alien (including spacing)
	    
	    // total width and height of the alien grid
	    double gridWidth = alienWidth * cols;
	    double gridHeight = alienHeight * rows;
	    
	    // starting x and y coordinates for the grid
	    double startX = (stage.getWidth() - gridWidth) / 2;
	    double startY = (stage.getHeight() - gridHeight) / 5;
	    
	    aliens = new Alien[rows][cols];
	    
	    // Reset and set up the alien grid pane
	    alienGridPane.getChildren().clear();
	    alienGridPane.setLayoutX(startX);
	    alienGridPane.setLayoutY(startY);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Alien alien;

				if (i == 0) {
					alien = new Alien3();
				} else if (i == 1 || i == 2) {
					alien = new Alien2();
				} else {
					alien = new Alien1();
				}

				alien.setLayoutX(j * alienWidth); // x-coordinate relative to alienGridPane
				alien.setLayoutY(i * alienHeight); // y-coordinate relative to alienGridPane

				aliens[i][j] = alien;

				alienGridPane.getChildren().add(alien);
			}
		}
		if (!pane.getChildren().contains(alienGridPane))
			pane.getChildren().add(alienGridPane);
	}

	/**
	 * moves the grid of aliens left and right TODO: make it move up and down as
	 * well
	 * 
	 * @param stage
	 */
	private Timeline moveAlienGrid(Stage stage) {
	    Timeline moveAliensTimeline = new Timeline(new KeyFrame(Duration.seconds(pauseDuration), e -> {
	    	if (alienGridPane.getChildren().size() == 0)
	    		alienGrid(stage);
	        for (Node node : alienGridPane.getChildren()) {
	            if (node instanceof Alien) {
	                ((Alien) node).switchImage();
	            }
	            int num = randGen.nextInt(0, alienGridPane.getChildren().size());
	            if (num == 0) {
	            	int x = (int)(node.getLayoutBounds().getCenterX() + node.getLayoutX() + alienGridPane.getLayoutX());
	            	int y = (int)(node.getLayoutBounds().getCenterY() + node.getLayoutY() + alienGridPane.getLayoutY());
	            	Bullet bullet = new Bullet(x, y, (int)bulletSpeed, true);
	            	enemyBullets.add(bullet);
	            	pane.getChildren().add(bullet.getImageView());
	            }
	        }

	        if (moveAliensRight) {
	            alienGridPane.setLayoutX(alienGridPane.getLayoutX() + alienMoveDistance);
	            if (alienGridPane.getLayoutX() + alienGridPane.getWidth() >= (stage.getWidth() - 50)) {
	                moveAliensRight = false;
	            }
	        } else {
	            alienGridPane.setLayoutX(alienGridPane.getLayoutX() - alienMoveDistance);
	            if (alienGridPane.getLayoutX() <= 50) {
	                moveAliensRight = true;
	            }
	        }
	    }));

		moveAliensTimeline.setCycleCount(Timeline.INDEFINITE);
		moveAliensTimeline.play();
		return moveAliensTimeline;
	}
	
	private void increaseDifficulty(Stage stage)
	{
		pauseDuration = pauseDuration / 3;
		currTimeline.stop();
		currTimeline.getKeyFrames().clear();
		KeyFrame newKeyFrame = new KeyFrame(Duration.seconds(pauseDuration), e -> {
	    	if (alienGridPane.getChildren().size() == 0)
	    		alienGrid(stage);
	        for (Node node : alienGridPane.getChildren()) {
	            if (node instanceof Alien) {
	                ((Alien) node).switchImage();
	            }
	            int num = randGen.nextInt(0, alienGridPane.getChildren().size());
	            if (num == 0) {
	            	int x = (int)(node.getLayoutBounds().getCenterX() + node.getLayoutX() + alienGridPane.getLayoutX());
	            	int y = (int)(node.getLayoutBounds().getCenterY() + node.getLayoutY() + alienGridPane.getLayoutY());
	            	Bullet bullet = new Bullet(x, y, (int)bulletSpeed, true);
	            	enemyBullets.add(bullet);
	            	pane.getChildren().add(bullet.getImageView());
	            }
	        }

	        if (moveAliensRight) {
	            alienGridPane.setLayoutX(alienGridPane.getLayoutX() + alienMoveDistance);
	            if (alienGridPane.getLayoutX() + alienGridPane.getWidth() >= (stage.getWidth() - 50)) {
	                moveAliensRight = false;
	            }
	        } else {
	            alienGridPane.setLayoutX(alienGridPane.getLayoutX() - alienMoveDistance);
	            if (alienGridPane.getLayoutX() <= 50) {
	                moveAliensRight = true;
	            }
	        }
	    });
		currTimeline.getKeyFrames().add(newKeyFrame);
		currTimeline.play();
		
	}

}
