package view_controller;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

/**
 * Sets up the GUI, including layout, animations, and style, for the pause menu
 * in the game.
 */
public class PauseMenuPane extends GridPane {
	private Label pausedLabel = new Label("Paused");
	private Label resumeLabel = new Label("Resume");
	private Label restartLabel = new Label("Restart");
	private Label mainMenuLabel = new Label("Main Menu");

	/**
	 * Main constructor for the PauseMenuPane class. Calls helper methods.
	 */
	public PauseMenuPane() {
		initializeGUI();
		registerHandlers();
	}

	/**
	 * Sets up the position and style of all buttons and labels
	 */
	private void initializeGUI() {
		Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);
		Font spaceFontLarge = Font.loadFont("file:fonts/space_invaders.ttf", 72);

		// Set fonts and styles
		pausedLabel.setFont(spaceFontLarge);
		pausedLabel.setStyle("-fx-text-fill: #ffffff;");
		resumeLabel.setFont(spaceFont);
		resumeLabel.setStyle("-fx-text-fill: #00ff5a;");
		restartLabel.setFont(spaceFont);
		restartLabel.setStyle("-fx-text-fill: #00ff5a;");
		mainMenuLabel.setFont(spaceFont);
		mainMenuLabel.setStyle("-fx-text-fill: #00ff5a;");

		// Separate big paused label from everything else
		GridPane.setMargin(pausedLabel, new Insets(-74, 0, 30, 0));

		// Align everything along the center
		GridPane.setHalignment(pausedLabel, HPos.CENTER);
		GridPane.setHalignment(resumeLabel, HPos.CENTER);
		GridPane.setHalignment(restartLabel, HPos.CENTER);
		GridPane.setHalignment(mainMenuLabel, HPos.CENTER);

		// Add everything to the pane
		this.add(pausedLabel, 0, 0);
		this.add(resumeLabel, 0, 1);
		this.add(restartLabel, 0, 2);
		this.add(mainMenuLabel, 0, 3);
		this.setStyle("-fx-background-color: black;");

		ColumnConstraints col1Constraints = new ColumnConstraints();
		col1Constraints.setPercentWidth(100);

		RowConstraints row1Constraints = new RowConstraints();
		row1Constraints.setPercentHeight(50);

		this.getColumnConstraints().addAll(col1Constraints);
		this.getRowConstraints().addAll(row1Constraints);
	}

	/**
	 * Adds handlers to indicate when the mouse is hovering over any of the buttons
	 */
	private void registerHandlers() {
		resumeLabel.setOnMouseEntered(e -> {
			resumeLabel.setStyle("-fx-text-fill: #79FFA8;");
		});
		resumeLabel.setOnMouseExited(e -> {
			resumeLabel.setStyle("-fx-text-fill: #00ff5a;");
		});
		restartLabel.setOnMouseEntered(e -> {
			restartLabel.setStyle("-fx-text-fill: #79FFA8;");
		});
		restartLabel.setOnMouseExited(e -> {
			restartLabel.setStyle("-fx-text-fill: #00ff5a;");
		});
		mainMenuLabel.setOnMouseEntered(e -> {
			mainMenuLabel.setStyle("-fx-text-fill: #79FFA8;");
		});
		mainMenuLabel.setOnMouseExited(e -> {
			mainMenuLabel.setStyle("-fx-text-fill: #00ff5a;");
		});
	}

	/**
	 * Getter method for resume button
	 * 
	 * @return a JavaFX Label that should resume the game when clicked
	 */
	public Label getResumeLabel() {
		return resumeLabel;
	}

	/**
	 * Getter method for restart button
	 * 
	 * @return a JavaFX Label that should restart the game when clicked
	 */
	public Label getRestartLabel() {
		return restartLabel;
	}

	/**
	 * Getter method for main menu button
	 * 
	 * @return a JavaFX Label that should go back to the main menu when clicked
	 */
	public Label getMainMenuLabel() {
		return mainMenuLabel;
	}
}
