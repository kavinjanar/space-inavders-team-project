package view_controller;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.*;

public class SpaceInvadersGUI extends Application {
	private Pane pane = new Pane();
	private TutorialPane tutorialPane = new TutorialPane();
	private Button tutorialButton;
	private ImageView spaceship;
	private boolean moveLeft = false;
	private boolean moveRight = false;
	private boolean fireBullet = false;
	private final double playerSpeed = 5;
	private final double bulletSpeed = 5;
	private ImageView playerBullet;
	private ArrayList<ImageView> enemyBullets = new ArrayList<>();
	private ArrayList<Shield> shields = new ArrayList<>();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		// screen parameters
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		double screenWidth = screenBounds.getWidth();
		double screenHeight = screenBounds.getHeight();
		
		tutorialButton = new Button("Tutorial");
		tutorialButton.setLayoutX(screenWidth / 2);
		tutorialButton.setLayoutY(screenHeight * 0.9);
		initTutorialWindow(stage);
		
		// space ship initialization
		Image spaceshipImage = new Image("file:images/Spaceship.png");
		spaceship = new ImageView(spaceshipImage);
		System.out.println("Image loaded? " + spaceshipImage.isError());
		
		spaceship.setFitWidth(50);
		spaceship.setFitHeight(60);
		spaceship.setPreserveRatio(true);
		spaceship.setLayoutX(screenWidth / 2);
		spaceship.setLayoutY(screenHeight * 0.80);
		
		// Player bullet initialization
		Bullet plyrBullet = new Bullet((int)spaceship.getLayoutX(), (int)spaceship.getLayoutY(), -1*(int)bulletSpeed);
		Image bulletImage = new Image("file:images/Bullet.png");
		playerBullet = new ImageView(bulletImage);
		playerBullet.setFitWidth(10);
		playerBullet.setFitHeight(25);
		
		// Add shields
		for (int y = (int)(screenHeight * 0.60); y <= (int)(screenHeight * 0.70); y += (int)(screenHeight * 0.05)) {
			for (int offset = -1*(int)(screenHeight * 0.05); offset <= (int)(screenHeight * 0.05); offset += (int)(screenHeight * 0.05)) {
				for (int x = (int)screenWidth / 10; x <= 9*(int)screenWidth / 10; x += (int)screenWidth / 5) {
					Shield shield = new Shield(x + offset - 12, y, (int)(screenHeight * 0.05));
					shields.add(shield);
					pane.getChildren().add(shield.getImageView());
				}
			}
		}
		
		pane.getChildren().add(spaceship);
		pane.getChildren().add(tutorialButton);
		pane.getChildren().add(playerBullet);
		

		Scene scene = new Scene(pane, 600, 400);
		// Keyboard movement
		scene.setOnKeyPressed((event)->{
			switch(event.getCode())
			{
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
		scene.setOnKeyReleased((event)->{
			switch(event.getCode())
			{
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
				
				if (moveLeft) dx -= playerSpeed;
				if (moveRight) dx += playerSpeed;
				
				spaceship.setLayoutX(spaceship.getLayoutX() + dx);
				
				// Reset bullet position if out of bounds
				if (playerBullet != null) {
					if (playerBullet.getLayoutY() < -1*playerBullet.getFitHeight() && fireBullet || bulletHitSomething(playerBullet)) {
						System.out.println("reset bullet");
						pane.getChildren().remove(playerBullet);
						playerBullet = null;
					}
					else
						playerBullet.setLayoutY(playerBullet.getLayoutY() + plyrBullet.getSpeed());
				}
				else if (fireBullet) {
					playerBullet = new ImageView(bulletImage);
					pane.getChildren().add(playerBullet);
					playerBullet.setFitWidth(10);
					playerBullet.setFitHeight(25);
					playerBullet.setLayoutX(spaceship.getLayoutX() + spaceship.getFitWidth() / 2 - playerBullet.getFitWidth() / 2);
					playerBullet.setLayoutY(spaceship.getLayoutY() - playerBullet.getFitHeight());
				}

				double x = spaceship.getLayoutX();
				double width = spaceship.getBoundsInLocal().getWidth();
				
				if (x < 0) spaceship.setLayoutX(0);
				if (x + width > screenWidth) spaceship.setLayoutX(screenWidth - width);
			}
		};
		timer.start();
		
		stage.setFullScreen(true);
		stage.setTitle("Space Invaders");
		stage.setScene(scene);
		stage.show();
		stage.requestFocus();
		
//		alienGrid(stage);
	}
	
	private boolean bulletHitSomething(ImageView bullet) {
		for (Shield shield : shields) {
			if (playerBullet != null && playerBullet.getLayoutY() <= shield.getY() + shield.getImageView().getFitHeight() 
			&& playerBullet.getLayoutX() >= shield.getImageView().getLayoutX() - playerBullet.getFitWidth() && playerBullet.getLayoutX() <= shield.getImageView().getLayoutX() + shield.getImageView().getFitWidth()
			&& shield.getHealth() > 0) {
				shield.hit();
				return true;
			}
		}
		return false;
	}

	private void initTutorialWindow(Stage stage) {
		tutorialButton.setOnAction(event -> {
			Scene tutorialScene = new Scene(tutorialPane, 400, 300);
			Stage tutorialWindow = new Stage();

			// Forces application focus on tutorial window
			tutorialWindow.initOwner(stage);
			tutorialWindow.initModality(Modality.WINDOW_MODAL);

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


	//Setup grid of aliens
	private void alienGrid(Stage stage) {
		int rows = 5;
		int cols = 10;

		Alien[][] aliens = new Alien[rows][cols];

		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				Alien alien;

				if (i % 3 == 0) {
					alien = new Alien1();
				} else if (i % 3 == 1) {
					alien = new Alien2();
				} else {
					alien = new Alien3();
				}

				alien.setLayoutX(j * 50);  // x-coordinate
				alien.setLayoutY(i * 50);  // y-coordinate

				aliens[i][j] = alien;

				pane.getChildren().add(alien);
			}
		}
	}
}
