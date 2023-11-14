package view_controller;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Alien;
import model.Alien1;
import model.Alien2;
import model.Alien3;
import model.Bullet;
import model.Shield;
import model.SoundPlayer;
import model.SpaceShip;

public class SpaceInvadersGUI extends Application {
	private Pane pane = new Pane();
	private StackPane basePane = new StackPane();
	private TutorialPane tutorialPane = new TutorialPane();
	private Button tutorialButton;
	private int numPlayers = 2;
	private SpaceShip spaceship1;
	private SpaceShip spaceship2;
	private boolean fireBullet1 = false;
	private boolean fireBullet2 = false;
	private final double playerSpeed = 5;
	private final double bulletSpeed = 12;
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
	private Label scoreLabel = new Label("Score");
	private Label scoreValueLabel = new Label("0");
	private Label livesLabel = new Label("Lives");
	private ArrayList<ImageView> livesList = new ArrayList<ImageView>();
	private int score;
	private AnimationTimer timer;
	private MainMenuPane mainMenu;
	
	// screen dimensions
	private Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	private double screenWidth = screenBounds.getWidth();
	private double screenHeight = screenBounds.getHeight();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		pane.setStyle("-fx-background-color: black;");
		basePane.setStyle("-fx-background-color: black;");
				
		// score label setup
		Font scoreFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);
		scoreLabel.setFont(scoreFont);
		scoreLabel.setStyle("-fx-text-fill: #FFFFFF");
		scoreLabel.setLayoutX(50);
		scoreLabel.setLayoutY(20);
		
		scoreValueLabel.setFont(scoreFont);
		scoreValueLabel.setStyle("-fx-text-fill: #00ff5a");
		scoreValueLabel.setLayoutX(240);
		scoreValueLabel.setLayoutY(20);
		
		livesLabel.setFont(scoreFont);
		livesLabel.setStyle("-fx-text-fill: #FFFFFF");
		livesLabel.setLayoutX(screenWidth - 370);
		livesLabel.setLayoutY(20);

		tutorialButton = new Button("Tutorial");
		tutorialButton.setPrefSize(100, 40);
		tutorialButton.setLayoutX(screenWidth / 2 - tutorialButton.getPrefWidth() / 2);
		tutorialButton.setLayoutY(screenHeight * 0.95);
		initTutorialWindow(stage);

		// space ship initialization		
		Image spaceshipImage = new Image("file:images/Spaceship.png");
		spaceship1 = new SpaceShip(spaceshipImage);

		spaceship1.setFitWidth(50);
		spaceship1.setFitHeight(60);
		spaceship1.setPreserveRatio(true);
		spaceship1.setLayoutX(screenWidth / 2 - spaceship1.getFitWidth() / 2);
		spaceship1.setLayoutY(screenHeight * 0.80);
		pane.getChildren().add(spaceship1);

		if (numPlayers > 1) {
			spaceship2 = new SpaceShip(spaceshipImage);
	
			spaceship2.setFitWidth(50);
			spaceship2.setFitHeight(60);
			spaceship2.setPreserveRatio(true);
			spaceship2.setLayoutX(screenWidth / 2 - spaceship1.getFitWidth() / 2);
			spaceship2.setLayoutY(screenHeight * 0.85);
			pane.getChildren().add(spaceship2);
		}

		setLives();

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

		pane.getChildren().add(tutorialButton);
		pane.getChildren().add(scoreLabel);
		pane.getChildren().add(scoreValueLabel);
		pane.getChildren().add(livesLabel);
		
		Parent parent = basePane;
		
		Scene scene = new Scene(parent, screenWidth, screenHeight);

		mainMenu = new MainMenuPane();
		ColumnConstraints col1Constraints = new ColumnConstraints();
		col1Constraints.setPercentWidth(100);
		
		basePane.getChildren().add(mainMenu);
		mainMenu.getStartGameLabel().setOnMouseClicked(event -> {
			basePane.getChildren().remove(mainMenu);
			basePane.getChildren().add(pane);
			stage.setFullScreen(true);
		});
		mainMenu.getTutorialLabel().setOnMouseClicked(event -> {
			// TODO for tutorial pane
		});
		
		// Keyboard movement
		scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			if (!spaceship1.isDestroyed()) {
				switch (event.getCode()) {
				case A:
					spaceship1.moveLeft = true;
					break;
				
				case D:
					spaceship1.moveRight = true;
					break;
					
				case SHIFT:
					fireBullet1 = true;
					break;
				
				default:
					break;
				}
			}
			if (!spaceship2.isDestroyed()) {
				switch (event.getCode()) {
				case LEFT:
					spaceship2.moveLeft = true;
					break;

				case RIGHT:
					spaceship2.moveRight = true;
					break;
					
				case SPACE:
					fireBullet2 = true;
					break;

				default:
					break;
				}
			}
		});
		scene.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
			if (!spaceship1.isDestroyed()) {
				switch (event.getCode()) {
				case A:
					spaceship1.moveLeft = false;
					break;
				
				case D:
					spaceship1.moveRight = false;
					break;
					
				case SHIFT:
					fireBullet1 = false;
					break;
				
				default:
					break;
				}
			}
			if (!spaceship2.isDestroyed()) {
				switch (event.getCode()) {
				case LEFT:
					spaceship2.moveLeft = false;
					break;

				case RIGHT:
					spaceship2.moveRight = false;
					break;
					
				case SPACE:
					fireBullet2 = false;
					break;

				default:
					break;
				}
			}
		});
		timer = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				// spaceship 1 stuff
				if (spaceship1.moveLeft)
					spaceship1.setLayoutX(spaceship1.getLayoutX() - playerSpeed);
				if (spaceship1.moveRight)
					spaceship1.setLayoutX(spaceship1.getLayoutX() + playerSpeed);

				Bullet plyrBullet = spaceship1.getBullet();
				// Reset bullet position if out of bounds
				if (plyrBullet != null) {
					ImageView playerBullet = plyrBullet.getImageView();
					if (playerBullet.getLayoutY() < -1*playerBullet.getFitHeight() && fireBullet1 || bulletHitSomething(plyrBullet, stage)) {
						pane.getChildren().remove(plyrBullet.getImageView());
						spaceship1.deleteBullet();
					}
					else
						playerBullet.setLayoutY(playerBullet.getLayoutY() + plyrBullet.getSpeed());
				}
				else if (fireBullet1) {
					spaceship1.fireBullet();
					pane.getChildren().add(spaceship1.getBullet().getImageView());
					soundPlayer.playShootSound();
				}
				
				double x = spaceship1.getLayoutX();
				double width = spaceship1.getBoundsInLocal().getWidth();

				if (x < 0)
					spaceship1.setLayoutX(0);
				if (x + width > screenWidth)
					spaceship1.setLayoutX(screenWidth - width);
								
				// spaceship 2 stuff
				if (spaceship2 != null) {
					if (spaceship2.moveLeft)
						spaceship2.setLayoutX(spaceship2.getLayoutX() - playerSpeed);
					if (spaceship2.moveRight)
						spaceship2.setLayoutX(spaceship2.getLayoutX() + playerSpeed);
	
					plyrBullet = spaceship2.getBullet();
					// Reset bullet position if out of bounds
					if (plyrBullet != null) {
						ImageView playerBullet = plyrBullet.getImageView();
						if (playerBullet.getLayoutY() < -1*playerBullet.getFitHeight() && fireBullet2 || bulletHitSomething(plyrBullet, stage)) {
							pane.getChildren().remove(plyrBullet.getImageView());
							spaceship2.deleteBullet();
						}
						else
							playerBullet.setLayoutY(playerBullet.getLayoutY() + plyrBullet.getSpeed());
					}
					else if (fireBullet2) {
						spaceship2.fireBullet();
						pane.getChildren().add(spaceship2.getBullet().getImageView());
						soundPlayer.playShootSound();
					}
					
					x = spaceship2.getLayoutX();
					width = spaceship2.getBoundsInLocal().getWidth();
	
					if (x < 0)
						spaceship2.setLayoutX(0);
					if (x + width > screenWidth)
						spaceship2.setLayoutX(screenWidth - width);
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
	
	private void setLives()
	{
		Image lifeImage = new Image("file:images/Life.png");
		
		for (ImageView life : livesList)
		{
			pane.getChildren().remove(life);
		}
		livesList.clear();
		for (int i = 0; i < spaceship1.getLives(); i++)
		{
			ImageView life = new ImageView(lifeImage);
			life.setFitHeight(40);
			life.setFitWidth(40);
			life.setLayoutY(35);
			life.setLayoutX((screenWidth - (screenWidth * 0.06)) - (50 * i));
			pane.getChildren().add(life);
			livesList.add(life);
		}
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
			if (!spaceship1.isDisabled() && bullet.withinBounds(spaceship1.getLayoutX(), spaceship1.getLayoutY(), spaceship1.getLayoutX() + spaceship1.getFitWidth(), spaceship1.getLayoutY() + spaceship1.getFitHeight())) {
				spaceship1.hitShip();
				if (spaceship1.isDestroyed() && (spaceship2 != null && spaceship2.isDestroyed())) {
					soundPlayer.playExplosionSound();
					spaceship1.moveLeft = false;
					spaceship1.moveRight = false;
					spaceship1.setVisible(false);
					endGame(stage);
				}
				else if (spaceship1.isDestroyed()) {
					soundPlayer.playExplosionSound();
					spaceship1.moveLeft = false;
					spaceship1.moveRight = false;
					spaceship1.setVisible(false);
				}
				setLives();
				spaceship1.setLayoutX(screenWidth / 2 - spaceship1.getFitWidth() / 2);
				return true;
			}
			if (spaceship2 != null && !spaceship2.isDisabled() && bullet.withinBounds(spaceship2.getLayoutX(), spaceship2.getLayoutY(), spaceship2.getLayoutX() + spaceship2.getFitWidth(), spaceship2.getLayoutY() + spaceship2.getFitHeight())) {
				spaceship2.hitShip();
				if (spaceship1.isDestroyed() && spaceship2.isDestroyed()) {
					soundPlayer.playExplosionSound();
					spaceship2.moveLeft = false;
					spaceship2.moveRight = false;
					spaceship2.setVisible(false);
					endGame(stage);
				}
				else if (spaceship2.isDestroyed()) {
					soundPlayer.playExplosionSound();
					spaceship2.moveLeft = false;
					spaceship2.moveRight = false;
					spaceship2.setVisible(false);
				}
				setLives();
				spaceship2.setLayoutX(screenWidth / 2 - spaceship2.getFitWidth() / 2);
				return true;
			}
		}
		else {
	        for (int i = 0; i < aliens.length; i++) {
	            for (int j = 0; j < aliens[i].length; j++) {
	            	if (aliens[i][j] != null && aliens[i][j].isAlive()) {
	                    double alienX = alienGridPane.getLayoutX() + aliens[i][j].getLayoutX();
	                    double alienY = alienGridPane.getLayoutY() + aliens[i][j].getLayoutY();
	                    double alienSize = 50.0;
	                    if (bullet.withinBounds(alienX, alienY, alienX + alienSize, alienY + alienSize)) {
	                        System.out.println("Alien hit: " + alienX + " " + alienY);
	                        if (aliens[i][j].isAlive()) {
	                            aliens[i][j].explode(); // Change the alien to an explosion image and mark it as not alive
	                            soundPlayer.playExplosionSound(); // Play explosion sound
	                            
	                            if (i == 0)
	                            {
	                            	incrementScore(30);
	                            }else if (i == 1 || i == 2)
	                            {
	                            	incrementScore(20);
	                            }else{
	                            	incrementScore(10);
	                            }

	                            final int finalI = i;
	                            final int finalJ = j;
	                            
	                            Timeline explosionTimeline = new Timeline(new KeyFrame(
	                                Duration.millis(500), // Duration for explosion to show
	                                ae -> {
	                                    alienGridPane.getChildren().remove(aliens[finalI][finalJ]);
	                                    aliens[finalI][finalJ] = null;
	                                    if (allAliensDestroyed()) {
	                                        System.out.println("Increasing difficulty");
	                                        increaseDifficulty(stage);
	                                    }
	                                }
	                            ));
	                            explosionTimeline.setCycleCount(1);
	                            explosionTimeline.play();

	                            return true; 
	                        }
	                    }
	                }
	            }
	        }
	    }
		
		return false;
	}
	
	private boolean allAliensDestroyed()
	{
		return alienGridPane.getChildren().isEmpty();
	}
	
	private void incrementScore(int increment)
	{
		score += increment;
		scoreValueLabel.setText("" + score);
	}

	private void initTutorialWindow(Stage stage) {
		Scene tutorialScene = new Scene(tutorialPane, 400, 300);
		Stage tutorialWindow = new Stage();

		// Forces application focus on tutorial window
		tutorialWindow.initOwner(stage);
		tutorialWindow.initModality(Modality.WINDOW_MODAL);

		tutorialButton.setOnMouseClicked(event -> {
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
		final double alienDropDownDistance = 20;
		
	    Timeline moveAliensTimeline = new Timeline(new KeyFrame(Duration.seconds(pauseDuration), e -> {
	    	if (alienGridPane.getChildren().size() == 0)
	    		alienGrid(stage);
	    	for (Node node : alienGridPane.getChildren()) {
	    		if (node instanceof Alien && ((Alien) node).isAlive()) {
	                ((Alien) node).switchImage();
	            }
	        }
	    	for (int i = 0; i < 4; i++) {
	        	int num = randGen.nextInt(0, 200);
	        	if (num < alienGridPane.getChildren().size()) {
	        		Alien node = (Alien) alienGridPane.getChildren().get(num);
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
	                alienGridPane.setLayoutY(alienGridPane.getLayoutY() + alienDropDownDistance);
	            }
	        } else {
	            alienGridPane.setLayoutX(alienGridPane.getLayoutX() - alienMoveDistance);
	            if (alienGridPane.getLayoutX() <= 50) {
	                moveAliensRight = true;
	                alienGridPane.setLayoutY(alienGridPane.getLayoutY() + alienDropDownDistance);
	            }
	        }
	    }));

		moveAliensTimeline.setCycleCount(Timeline.INDEFINITE);
		moveAliensTimeline.play();
		return moveAliensTimeline;
	}
	
	private void endGame(Stage stage)
	{
		timer.stop();
		currTimeline.stop();
		
		Stage gameOverStage = new Stage();
		gameOverStage.initModality(Modality.WINDOW_MODAL);
		
		VBox vBox = new VBox(10);
		vBox.setAlignment(Pos.CENTER);
		Label gameOverLabel = new Label("Game Over!");
		Button retryButton = new Button("Try Again");
		Button closeButton = new Button("Close");
		closeButton.setOnAction((event) -> {
			gameOverStage.close();
			stage.close();
		});
		retryButton.setOnAction((event) -> {
			
		});
		vBox.getChildren().addAll(gameOverLabel, closeButton);
		
		Scene gameOverScene = new Scene(vBox, 300, 200);
		gameOverStage.setTitle("Game Over");
		gameOverStage.setScene(gameOverScene);
		gameOverStage.initOwner(stage);
		gameOverStage.setX(stage.getX() + stage.getWidth() / 2 - gameOverStage.getWidth() / 2);
		gameOverStage.setY(stage.getY() + stage.getHeight() / 2 - gameOverStage.getHeight() / 2);
		gameOverStage.show();
		gameOverStage.requestFocus();

	}
	
	private void increaseDifficulty(Stage stage)
	{
		pauseDuration = pauseDuration * 0.7;
		currTimeline.stop();
		currTimeline.getKeyFrames().clear();
		KeyFrame newKeyFrame = new KeyFrame(Duration.seconds(pauseDuration), e -> {
	    	if (alienGridPane.getChildren().size() == 0)
	    		alienGrid(stage);
	        for (Node node : alienGridPane.getChildren()) {
	            if (node instanceof Alien) {
	                ((Alien) node).switchImage();
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
		
		KeyFrame bulletKeyFrame = new KeyFrame(Duration.millis(500), e -> {
			for (int i = 0; i < 4; i++) {
	        	int num = randGen.nextInt(0, 200);
	        	if (num < alienGridPane.getChildren().size()) {
	        		Alien node = (Alien) alienGridPane.getChildren().get(num);
	            	int x = (int)(node.getLayoutBounds().getCenterX() + node.getLayoutX() + alienGridPane.getLayoutX());
	            	int y = (int)(node.getLayoutBounds().getCenterY() + node.getLayoutY() + alienGridPane.getLayoutY());
	            	Bullet bullet = new Bullet(x, y, (int)bulletSpeed, true);
	            	enemyBullets.add(bullet);
	            	pane.getChildren().add(bullet.getImageView());
	        	}
	        }
		});
		currTimeline.getKeyFrames().add(bulletKeyFrame);
		currTimeline.play();
		
	}

}
