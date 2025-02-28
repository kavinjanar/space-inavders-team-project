package view_controller;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
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
import model.UFO;

/**
 * Main GUI for the Space Invaders Game that contains all the different panes
 */
public class SpaceInvadersGUI extends Application {
	private Pane pane = new Pane();
	private StackPane basePane = new StackPane();
	private TutorialPane tutorialPane = new TutorialPane();
	private UpgradeShipPane upgradeShipPane = new UpgradeShipPane();
	private GameOverPane gameOverPane = new GameOverPane();
	private PlayerSelectionPane playerSelectionPane = new PlayerSelectionPane();
	private PauseMenuPane pauseMenuPane = new PauseMenuPane();
	private SpaceShip spaceship1;
	private SpaceShip spaceship2;
	private boolean fireBullet1 = false;
	private boolean fireBullet2 = false;
	private final double bulletSpeed = 12;
	private ArrayList<Bullet> enemyBullets = new ArrayList<>();
	private ArrayList<Shield> shields = new ArrayList<>();
	private Pane alienGridPane = new Pane();
	private boolean moveAliensRight = true;
	private final double alienMoveDistance = 20;
	private Alien[][] aliens;
	private Random randGen;
	private double pauseDuration = 1;
	private Timeline currTimeline;
	private SoundPlayer soundPlayer;
	private Label scoreLabel = new Label("Score");
	private Label scoreValueLabel = new Label("0");
	private Label p1LivesLabel = new Label("Lives (P1):");
	private Label p2LivesLabel = new Label("Lives (P2):");
	private ArrayList<ImageView> livesList = new ArrayList<ImageView>();
	private ArrayList<ImageView> livesList2 = new ArrayList<ImageView>();
	private int score;
	private AnimationTimer timer;
	private MainMenuPane mainMenu;
	private UFO ufo;
	private Timeline ufoMovement;
	private Timeline ufoMoveTimeline;

	// screen dimensions
	private Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	private double screenWidth = screenBounds.getWidth();
	private double screenHeight = screenBounds.getHeight();

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Starts the main Space Invaders Game screen
	 */
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

		Font livesFont = Font.loadFont("file:fonts/space_invaders.ttf", 25);
		p1LivesLabel.setFont(livesFont);
		p1LivesLabel.setStyle("-fx-text-fill: #FFFFFF");
		p1LivesLabel.setLayoutX(screenWidth - 490);
		p1LivesLabel.setLayoutY(20);
		
		p2LivesLabel.setFont(livesFont);
		p2LivesLabel.setStyle("-fx-text-fill: #FFFFFF");
		p2LivesLabel.setLayoutX(screenWidth - 490);
		p2LivesLabel.setLayoutY(60);

		// space ship initialization
		Image spaceshipImage = new Image("file:images/Spaceship.png");
		spaceship1 = new SpaceShip(spaceshipImage, "Balanced Ship", 5, 3);

		spaceship1.setFitWidth(50);
		spaceship1.setFitHeight(60);
		spaceship1.setPreserveRatio(true);
		spaceship1.setLayoutX(screenWidth / 2 - spaceship1.getFitWidth() / 2);
		spaceship1.setLayoutY(screenHeight * 0.80);
		pane.getChildren().add(spaceship1);

		setLives();

		addShields();

		pane.getChildren().add(scoreLabel);
		pane.getChildren().add(scoreValueLabel);
		pane.getChildren().add(p1LivesLabel);

		Scene scene = new Scene(basePane, screenWidth, screenHeight);

		ColumnConstraints col1Constraints = new ColumnConstraints();
		col1Constraints.setPercentWidth(100);

		// Keyboard movement
		scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			if (event.getCode().equals(KeyCode.ESCAPE) && basePane.getChildren().contains(pane)) {
				basePane.getChildren().remove(pane);
				pauseGame(stage);
				basePane.getChildren().add(pauseMenuPane);
			}
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
			if (spaceship2 != null && !spaceship2.isDestroyed()) {
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
			if (spaceship2 != null && !spaceship2.isDestroyed()) {
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
					spaceship1.setLayoutX(spaceship1.getLayoutX() - spaceship1.getSpeed());
				if (spaceship1.moveRight)
					spaceship1.setLayoutX(spaceship1.getLayoutX() + spaceship1.getSpeed());

				Bullet plyrBullet = spaceship1.getBullet();
				// Reset bullet position if out of bounds
				if (plyrBullet != null) {
					ImageView playerBullet = plyrBullet.getImageView();
					if (playerBullet.getLayoutY() < -1 * playerBullet.getFitHeight() && fireBullet1
							|| bulletHitSomething(plyrBullet, stage)) {
						pane.getChildren().remove(plyrBullet.getImageView());
						spaceship1.deleteBullet();
					} else
						playerBullet.setLayoutY(playerBullet.getLayoutY() + plyrBullet.getSpeed());
				} else if (fireBullet1) {
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
						spaceship2.setLayoutX(spaceship2.getLayoutX() - spaceship2.getSpeed());
					if (spaceship2.moveRight)
						spaceship2.setLayoutX(spaceship2.getLayoutX() + spaceship2.getSpeed());

					plyrBullet = spaceship2.getBullet();
					// Reset bullet position if out of bounds
					if (plyrBullet != null) {
						ImageView playerBullet = plyrBullet.getImageView();
						if (playerBullet.getLayoutY() < -1 * playerBullet.getFitHeight() && fireBullet2
								|| bulletHitSomething(plyrBullet, stage)) {
							pane.getChildren().remove(plyrBullet.getImageView());
							spaceship2.deleteBullet();
						} else
							playerBullet.setLayoutY(playerBullet.getLayoutY() + plyrBullet.getSpeed());
					} else if (fireBullet2) {
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
					} else {
						bullet.getImageView().setLayoutY(bullet.getImageView().getLayoutY() + bullet.getSpeed());
					}
					i++;
				}
			}
		};

		stage.setFullScreen(true);
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
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

		//ufo
		ufo = new UFO();
		ufo.setVisible(false); // Initially hidden
		pane.getChildren().add(ufo); // Add UFO to the main pane
		setupUFOMovement();

		mainMenu = new MainMenuPane();
		basePane.getChildren().add(mainMenu);
		registerMenuHandlers(stage);
		
		
		
	}

	/**
	 * Registers the handlers for all the buttons in the various menus. Main navigation algorithm
	 * @param stage - the stage of the GUI
	 */
	private void registerMenuHandlers(Stage stage) {
		mainMenu.getStartGameLabel().setOnMouseClicked(event -> {
			basePane.getChildren().remove(mainMenu);
			basePane.getChildren().add(playerSelectionPane);
		});
		mainMenu.getTutorialLabel().setOnMouseClicked(event -> {
			basePane.getChildren().remove(mainMenu);
			basePane.getChildren().add(tutorialPane);
		});
		mainMenu.getQuitLabel().setOnMouseClicked(event -> {
			stage.close();
		});
		playerSelectionPane.getSinglePlayerLabel().setOnMouseClicked(event -> {
			basePane.getChildren().remove(playerSelectionPane);
			basePane.getChildren().add(pane);
			// make sure there is no player 2
			if (spaceship2 != null) {
				pane.getChildren().remove(spaceship2);
				spaceship2 = null;
				for (ImageView life : livesList2) {
					pane.getChildren().remove(life);
				}
				livesList2.clear();
				pane.getChildren().remove(p2LivesLabel);
			}
			// start the game
			currTimeline.play();
			timer.start();
			ufoMovement.play();
		});
		playerSelectionPane.getMultiplayerLabel().setOnMouseClicked(event -> {
			basePane.getChildren().remove(playerSelectionPane);
			basePane.getChildren().add(pane);
			pane.getChildren().add(p2LivesLabel);
			// add player 2
			if (spaceship2 == null) {
				spaceship2 = new SpaceShip(new Image("file:images/Spaceship.png"), "Balanced Ship", 5, 3);
				pane.getChildren().add(spaceship2);
			}
			spaceship2.setFitWidth(50);
			spaceship2.setFitHeight(60);
			spaceship2.setPreserveRatio(true);
			spaceship2.setLayoutX(screenWidth / 2 - spaceship1.getFitWidth() / 2);
			spaceship2.setLayoutY(screenHeight * 0.85);
			setLives();
			// start the game
			currTimeline.play();
			timer.start();
			ufoMovement.play();
		});
		playerSelectionPane.getBackLabel().setOnMouseClicked(event -> {
			basePane.getChildren().remove(playerSelectionPane);
			basePane.getChildren().add(mainMenu);
		});
		tutorialPane.getBackLabel().setOnMouseClicked(event -> {
			basePane.getChildren().remove(tutorialPane);
			basePane.getChildren().add(mainMenu);
		});
		gameOverPane.getRestartLabel().setOnMouseClicked(event -> {
			basePane.getChildren().remove(gameOverPane);
			resetGame(stage);
			basePane.getChildren().add(pane);
			resumeGame(stage);
		});
		gameOverPane.getMainMenuLabel().setOnMouseClicked(event -> {
			basePane.getChildren().remove(gameOverPane);
			resetGame(stage);
			basePane.getChildren().add(mainMenu);
		});
		gameOverPane.getQuitLabel().setOnMouseClicked(event -> {
			stage.close();
		});
		pauseMenuPane.getResumeLabel().setOnMouseClicked(event -> {
			basePane.getChildren().remove(pauseMenuPane);
			basePane.getChildren().add(pane);
			resumeGame(stage);
		});
		pauseMenuPane.getRestartLabel().setOnMouseClicked(event -> {
			basePane.getChildren().remove(pauseMenuPane);
			resetGame(stage);
			basePane.getChildren().add(pane);
			resumeGame(stage);
		});
		pauseMenuPane.getMainMenuLabel().setOnMouseClicked(event -> {
			basePane.getChildren().remove(pauseMenuPane);
			resetGame(stage);
			basePane.getChildren().add(mainMenu);
		});
	}

	/**
	 * Sets the lives counter on the top right of the game for both
	 * single and multiplayer
	 */
	private void setLives() {
		Image lifeImage = new Image("file:images/Life.png");

		for (ImageView life : livesList) {
			pane.getChildren().remove(life);
		}
		livesList.clear();
		for (int i = 0; i < spaceship1.getLives(); i++) {
			ImageView life = new ImageView(lifeImage);
			life.setFitHeight(40);
			life.setFitWidth(40);
			life.setLayoutY(20);
			life.setLayoutX((screenWidth - (screenWidth * 0.06)) - (50 * i));
			pane.getChildren().add(life);
			livesList.add(life);
		}
		
		if (spaceship2 != null)
		{
			for (ImageView life : livesList2) {
				pane.getChildren().remove(life);
			}
			livesList2.clear();
			for (int i = 0; i < spaceship2.getLives(); i++) {
				ImageView life = new ImageView(lifeImage);
				life.setFitHeight(40);
				life.setFitWidth(40);
				life.setLayoutY(60);
				life.setLayoutX((screenWidth - (screenWidth * 0.06)) - (50 * i));
				pane.getChildren().add(life);
				livesList2.add(life);
			}
		}
	}

	/**
	 * Algorithm that deterimines what happens when a Bullet hits anything in the game
	 * @param bullet - the Bullet object that needs to be checked
	 * @param stage - the stage of the GUI
	 * @return
	 */
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
			if (bullet.withinBounds(spaceship1.getLayoutX(), spaceship1.getLayoutY(),
					spaceship1.getLayoutX() + spaceship1.getFitWidth(),
					spaceship1.getLayoutY() + spaceship1.getFitHeight())) {
				spaceship1.hitShip();
				if (spaceship1.isDestroyed() && (spaceship2 != null && spaceship2.isDestroyed())) {
					soundPlayer.playExplosionSound();
					spaceship1.moveLeft = false;
					spaceship1.moveRight = false;
					spaceship1.setVisible(false);
					endGame(stage);
				} else if (spaceship1.isDestroyed()) {
					soundPlayer.playExplosionSound();
					spaceship1.moveLeft = false;
					spaceship1.moveRight = false;
					spaceship1.setVisible(false);
					if (spaceship2 == null)
						endGame(stage);
				}
				setLives();
				spaceship1.setLayoutX(screenWidth / 2 - spaceship1.getFitWidth() / 2);
				return true;
			}
			if (spaceship2 != null && bullet.withinBounds(spaceship2.getLayoutX(), spaceship2.getLayoutY(),
					spaceship2.getLayoutX() + spaceship2.getFitWidth(),
					spaceship2.getLayoutY() + spaceship2.getFitHeight())) {
				spaceship2.hitShip();
				if (spaceship1.isDestroyed() && spaceship2.isDestroyed()) {
					soundPlayer.playExplosionSound();
					spaceship2.moveLeft = false;
					spaceship2.moveRight = false;
					spaceship2.setVisible(false);
					endGame(stage);
				} else if (spaceship2.isDestroyed()) {
					soundPlayer.playExplosionSound();
					spaceship2.moveLeft = false;
					spaceship2.moveRight = false;
					spaceship2.setVisible(false);
				}
				setLives();
				spaceship2.setLayoutX(screenWidth / 2 - spaceship2.getFitWidth() / 2);
				return true;
			}
		} else {
			for (int i = 0; i < aliens.length; i++) {
				for (int j = 0; j < aliens[i].length; j++) {
					if (aliens[i][j] != null && aliens[i][j].isAlive()) {
						double alienX = alienGridPane.getLayoutX() + aliens[i][j].getLayoutX();
						double alienY = alienGridPane.getLayoutY() + aliens[i][j].getLayoutY();
			            double alienWidth = aliens[i][j].getBoundsInLocal().getWidth();
			            double alienHeight = aliens[i][j].getBoundsInLocal().getHeight();
			            if (bullet.withinBounds(alienX, alienY, alienX + alienWidth, alienY + alienHeight)) {
							System.out.println("Alien hit: " + alienX + " " + alienY);
							if (aliens[i][j].isAlive()) {
								aliens[i][j].explode(); // Change the alien to an explosion image and mark it as not
								// alive
								soundPlayer.playExplosionSound(); // Play explosion sound

								if (i == 0) {
									incrementScore(30);
								} else if (i == 1 || i == 2) {
									incrementScore(20);
								} else {
									incrementScore(10);
								}

								final int finalI = i;
								final int finalJ = j;

								Timeline explosionTimeline = new Timeline(new KeyFrame(Duration.millis(500), // Duration
										// for
										// explosion
										// to
										// show
										ae -> {
											alienGridPane.getChildren().remove(aliens[finalI][finalJ]);
											aliens[finalI][finalJ] = null;
											if (allAliensDestroyed()) {
												System.out.println("Increasing difficulty");
												//increaseDifficulty(stage);
												if (!spaceship1.isDestroyed())
												{
													upgradeShip(stage, "Player 1", spaceship1);
												}else {
													upgradeShip(stage, "Player 2", spaceship2);
												}
											}
										}));
								explosionTimeline.setCycleCount(1);
								explosionTimeline.play();

								return true;
							}
						}
					}
				}
			}
			if (ufo.isVisible() && bullet.withinBounds(ufo.getLayoutX(), ufo.getLayoutY(), ufo.getLayoutX() + ufo.getWidth(), ufo.getLayoutY() + ufo.getHeight())) {
				incrementScore(randGen.nextInt(1,7) * 50);	// Add random multiple of 50 from 50 to 300 (both inclusive) to score
				ufo.explode();
				ufoMoveTimeline.stop();
				Timeline explosionTimeline = new Timeline(new KeyFrame(Duration.millis(500), // Duration for explosion to show
						ae -> {
							ufo.setVisible(false);
							ufo.setLayoutX(-ufo.getWidth());
						}));
				explosionTimeline.setCycleCount(1);
				explosionTimeline.play();
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if all aliens are destroyed
	 * @return - a Boolean that indicates if all aliens are destroyed
	 */
	private boolean allAliensDestroyed() {
		return alienGridPane.getChildren().isEmpty();
	}

	/**
	 * Increments the score earned
	 * @param increment - the amount that the score must be incremented
	 */
	private void incrementScore(int increment) {
		System.out.print("New score = " + score + " + " + increment);
		score += increment;
		System.out.println(" = " + score);
		scoreValueLabel.setText("" + score);
	}
	
	/**
	 * Adds the shields to the game screen
	 */
	private void addShields() {
		// Add shields
		for (int y = (int) (screenHeight * 0.65); y <= (int) (screenHeight * 0.70); y += (int) (screenHeight * 0.05)) {
			for (int offset = -1 * (int) (screenHeight * 0.05); offset <= (int) (screenHeight
					* 0.05); offset += (int) (screenHeight * 0.05)) {
				for (int x = (int) screenWidth / 10; x <= 9 * (int) screenWidth / 10; x += (int) screenWidth / 5) {
					if (y > (int) (screenHeight * 0.65) && offset == 0)
						continue;
					int size = (int) (screenHeight * 0.05);
					Shield shield = new Shield(x + offset - size/2, y, size);
					shields.add(shield);
					pane.getChildren().add(shield);
				}
			}
		}
	}

	/**
	 * sets up the grid of aliens
	 * 
	 * @param stage - the stage of the GUI
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

				double alienActualWidth = alien.getBoundsInLocal().getWidth();
				double centerX = (alienWidth - alienActualWidth) / 2; 

				alien.setLayoutX(j * alienWidth + centerX); 
				alien.setLayoutY(i * alienHeight); 

				aliens[i][j] = alien;

				alienGridPane.getChildren().add(alien);
			}
		}

		if (!pane.getChildren().contains(alienGridPane))
			pane.getChildren().add(alienGridPane);
	}

	/**
	 * moves the grid of aliens left and right
	 * well
	 * 
	 * @param stage - the stage of the GUI
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
					int x = (int) (node.getLayoutBounds().getCenterX() + node.getLayoutX()
					+ alienGridPane.getLayoutX());
					int y = (int) (node.getLayoutBounds().getCenterY() + node.getLayoutY()
					+ alienGridPane.getLayoutY());
					Bullet bullet = new Bullet(x, y, (int) bulletSpeed, true);
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
		return moveAliensTimeline;
	}
	
	/**
	 * Resumes the game after the game is stopped when paused
	 * @param stage - the stage of the current GUI
	 */
	private void resumeGame(Stage stage) {
		currTimeline.play();
		timer.start();
		ufoMovement.play();
		if (ufoMoveTimeline != null)
			ufoMoveTimeline.play();
	}
	
	/**
	 * Pauses the game by stopping all timelines
	 * @param stage - the current stage of the GUI
	 */
	private void pauseGame(Stage stage) {
		currTimeline.stop();
		timer.stop();
		ufoMovement.stop();
		if (ufoMoveTimeline != null)
			ufoMoveTimeline.pause();
	}

	/**
	 * Ends the game and displays the game over window
	 * @param stage - the stage of the GUI
	 */
	private void endGame(Stage stage) {
		timer.stop();
		currTimeline.stop();
		ufoMovement.stop();
		if (ufoMoveTimeline != null)
			ufoMoveTimeline.stop();
		basePane.getChildren().remove(pane);
		gameOverPane.setScore(score);
		basePane.getChildren().add(gameOverPane);
	}
	
	/**
	 * Resets the entire game if a player chooses to restart the game
	 * @param stage - the current stage of the GUI
	 */
	private void resetGame(Stage stage) {
		pauseGame(stage);
		score = 0;
		incrementScore(0);	// update score label
		pauseDuration = 1;
		increaseDifficulty(stage);
		moveAlienGrid(stage);
		
		for (Shield shield : shields)
			shield.resetHealth();
		
		spaceship1.setLayoutX((screenWidth - spaceship1.getFitWidth()) / 2.0);
		spaceship1.changeToBalancedShip();
		fireBullet1 = false;
		spaceship1.moveLeft = false;
		spaceship1.moveRight = false;
		spaceship1.setVisible(true);
		if (spaceship1.getBullet() != null) {
			pane.getChildren().remove(spaceship1.getBullet().getImageView());
			spaceship1.deleteBullet();
		}
		if (spaceship2 != null) {
			spaceship2.setLayoutX((screenWidth - spaceship2.getFitWidth()) / 2.0);
			spaceship2.changeToBalancedShip();
			fireBullet2 = false;
			spaceship2.moveLeft = false;
			spaceship2.moveRight = false;
			spaceship2.setVisible(true);
			if (spaceship2.getBullet() != null) {
				pane.getChildren().remove(spaceship2.getBullet().getImageView());
				spaceship2.deleteBullet();
			}
		}
		setLives();
		
		for (Bullet bullet : enemyBullets) {
			pane.getChildren().remove(bullet.getImageView());
		}
		enemyBullets.clear();
		
		alienGrid(stage);
		
		ufo.explode();
		ufo.setVisible(false);
		ufo.setLayoutX(-ufo.getWidth());
		gameOverPane = new GameOverPane();
		registerMenuHandlers(stage);
	}
	
	/**
	 * Stops the game and prompts the Upgrade Ship Pane to upgrade a ship after the completion of a round
	 * @param stage - the current stage of the GUI
	 * @param player - a String that indicates whether player 1 or 2 needs their ship upgraded
	 * @param ship - the SpaceShip object that needs to be upgraded
	 */
	private void upgradeShip(Stage stage, String player, SpaceShip ship) {
		SpaceShip currShip = new SpaceShip(spaceship1.getImage(), spaceship1.getName(), spaceship1.getSpeed(), spaceship1.getOriginalLives());
		pauseGame(stage);
		ufo.explode();
		ufo.setVisible(false);
		ufo.setLayoutX(-ufo.getWidth());
		if (ufoMoveTimeline != null)
			ufoMoveTimeline.pause();
		basePane.getChildren().remove(pane);
		basePane.getChildren().add(upgradeShipPane);
		upgradeShipPane.setShipInfo(ship, player);
		upgradeShipPane.getConfirmLabel().setOnMouseClicked((event) -> {
			basePane.getChildren().remove(upgradeShipPane);
		    basePane.getChildren().add(pane);
		    System.out.println("Modified ship speed: " + spaceship1.getSpeed());
		    System.out.println("Modified ship lives: " + spaceship1.getLives());
		    timer.start();
		    currTimeline = moveAlienGrid(stage); // Reset currTimeline with the new timeline
		    ufoMovement.play();
		    if (ufoMoveTimeline != null)
		    	ufoMoveTimeline.play();
		    pauseDuration = pauseDuration * 0.7;
		    increaseDifficulty(stage);
		    currTimeline.play(); // Start the new timeline for alien movement
		    setLives();
		    upgradeShipPane = new UpgradeShipPane();
		    if (player.equals("Player 1"))
		    {
		    	if (spaceship2 != null && !spaceship2.isDestroyed())
		    	{
		    		upgradeShip(stage, "Player 2", spaceship2);
		    	}
		    }
		    
		});
		upgradeShipPane.getCancelLabel().setOnMouseClicked((event) -> {
			basePane.getChildren().remove(upgradeShipPane);
			basePane.getChildren().add(pane);
			if (currShip.getName() == "Balanced Ship")
			{
				spaceship1.changeToBalancedShip();
			}
			if (currShip.getName() == "Speedy Ship")
			{
				spaceship1.changeToSpeedyShip();
			}
			if (currShip.getName() == "Chunky Ship")
			{
				spaceship1.changeToSpeedyShip();
			}
			
			timer.start();
		    currTimeline = moveAlienGrid(stage); // Reset currTimeline with the new timeline
		    ufoMovement.play();
		    if (ufoMoveTimeline != null)
		    	ufoMoveTimeline.play();
		    pauseDuration = pauseDuration * 0.7;
		    increaseDifficulty(stage);
		    currTimeline.play(); // Start the new timeline for alien movement
			setLives();
			
			upgradeShipPane = new UpgradeShipPane();
		});
	}

	/**
	 * Increases the difficulty of the game by a set factor
	 * @param stage - the stage of the GUI
	 */
	private void increaseDifficulty(Stage stage) {
		System.out.println("entering method");
		System.out.println("New update duration: " + pauseDuration);
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

		KeyFrame bulletKeyFrame = new KeyFrame(Duration.millis(Math.max(100, pauseDuration*1000)), e -> {
			for (int i = 0; i < 4; i++) {
				int num = randGen.nextInt(0, 200);
				if (num < alienGridPane.getChildren().size()) {
					Alien node = (Alien) alienGridPane.getChildren().get(num);
					int x = (int) (node.getLayoutBounds().getCenterX() + node.getLayoutX()
					+ alienGridPane.getLayoutX());
					int y = (int) (node.getLayoutBounds().getCenterY() + node.getLayoutY()
					+ alienGridPane.getLayoutY());
					Bullet bullet = new Bullet(x, y, (int) bulletSpeed, true);
					enemyBullets.add(bullet);
					pane.getChildren().add(bullet.getImageView());
				}
			}
		});
		currTimeline.getKeyFrames().add(bulletKeyFrame);
	}

	/**
	 * Sets up and moves the big red UFO across the screen during certain time intervals
	 */
	private void setupUFOMovement() {
	    ufoMovement = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
	        if (randGen.nextBoolean()) { // Randomly decide to show the UFO
	            System.out.println("UFO is about to appear."); // Debug statement

	            ufo.setVisible(true);
	            ufo.revive();
	            ufo.setLayoutX(-ufo.getWidth()); // Starting position (left side, off-screen)
	            ufo.setLayoutY(80); // Top of the screen

	            System.out.println("UFO width: " + ufo.getWidth()); // Debug statement
	            System.out.println("UFO initial X position: " + ufo.getLayoutX()); // Debug statement

	            // Animate the UFO to move across the screen
	            KeyValue keyValue = new KeyValue(ufo.layoutXProperty(), pane.getWidth());
	            KeyFrame keyFrame = new KeyFrame(Duration.seconds(7), keyValue); // Adjust duration as needed
	            ufoMoveTimeline = new Timeline(keyFrame);
	            ufoMoveTimeline.setOnFinished(ev -> {
	                ufo.setVisible(false); // Hide UFO after finishing the move
	                System.out.println("UFO animation finished."); // Debug statement
	            });
	            ufoMoveTimeline.play();
	        }
	    }));
	    ufoMovement.setCycleCount(Timeline.INDEFINITE);
	}
}
