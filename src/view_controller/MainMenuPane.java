package view_controller;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

/**
 * The MainMenuPane class represents a custom JavaFX GridPane used as the main
 * menu for a game. It contains labels for starting the game, accessing the
 * tutorial, and quitting the application.
 */
public class MainMenuPane extends GridPane {
	private Label startGameLabel = new Label("Start Game");
	private Label tutorialLabel = new Label("Tutorial");
	private Label quitLabel = new Label("Quit");

	/**
	 * Construct the MainMenuPane object, initialize the GUI and register event
	 * handlers.
	 */
	public MainMenuPane() {
		initializeGUI();
		registerHandlers();
	}

	/**
	 * Initializes the GUI components, such as labels/buttons, fonts, and layout
	 * constraints.
	 */
	private void initializeGUI() {
		Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);
		startGameLabel.setFont(spaceFont);
		startGameLabel.setStyle("-fx-text-fill: #00ff5a");
		tutorialLabel.setFont(spaceFont);
		tutorialLabel.setStyle("-fx-text-fill: #00ff5a");
		quitLabel.setFont(spaceFont);
		quitLabel.setStyle("-fx-text-fill: #00ff5a");
		GridPane.setHalignment(startGameLabel, HPos.CENTER);
		GridPane.setHalignment(tutorialLabel, HPos.CENTER);
		GridPane.setHalignment(quitLabel, HPos.CENTER);

		ColumnConstraints col1Constraints = new ColumnConstraints();
		col1Constraints.setPercentWidth(100);

		RowConstraints row1Constraints = new RowConstraints();
		row1Constraints.setPercentHeight(50);

		Image logo = new Image("file:images/logo.png", 450, 195, true, true);
		ImageView logoImageView = new ImageView(logo);
		GridPane.setHalignment(logoImageView, HPos.CENTER);

		this.add(logoImageView, 0, 0);
		this.add(startGameLabel, 0, 1);
		this.add(tutorialLabel, 0, 2);
		this.add(quitLabel, 0, 3);
		this.setStyle("-fx-background-color: black;");
		this.getColumnConstraints().addAll(col1Constraints);
		this.getRowConstraints().addAll(row1Constraints);
	}

	/**
	 * Returns the "Start" label from the main menu.
	 *
	 * @return The Label representing the "Start" option.
	 */
	public Label getStartGameLabel() {
		return startGameLabel;
	}

	/**
	 * Returns the "Tutorial" label from the main menu.
	 *
	 * @return The Label representing the "Tutorial" option.
	 */
	public Label getTutorialLabel() {
		return tutorialLabel;
	}

	/**
	 * Returns the "Quit" label from the main menu.
	 *
	 * @return The Label representing the "Quit" option.
	 */
	public Label getQuitLabel() {
		return quitLabel;
	}

	/**
	 * Registers event handlers for mouse hover effects on menu labels. Changes
	 * label text fill color when the mouse enters or exits the label area.
	 */
	private void registerHandlers() {
		startGameLabel.setOnMouseEntered(event -> {
			startGameLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		startGameLabel.setOnMouseExited(event -> {
			startGameLabel.setStyle("-fx-text-fill: #00ff5a");
		});
		tutorialLabel.setOnMouseEntered(event -> {
			tutorialLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		tutorialLabel.setOnMouseExited(event -> {
			tutorialLabel.setStyle("-fx-text-fill: #00ff5a");
		});
		quitLabel.setOnMouseEntered(event -> {
			quitLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		quitLabel.setOnMouseExited(event -> {
			quitLabel.setStyle("-fx-text-fill: #00ff5a");
		});
	}
}
