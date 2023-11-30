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
 * The PlayerSelectionPane class represents a custom JavaFX GridPane used for
 * player selection in a game menu. It includes options for singleplayer,
 * multiplayer, and a back button.
 */
public class PlayerSelectionPane extends GridPane {
	private Label singleplayerLabel = new Label("Singleplayer");
	private Label multiplayerLabel = new Label("Multiplayer");
	private Label backLabel = new Label("Back");

	/**
	 * Construct the PlayerSelectionPane object, initialize the GUI and register
	 * event handlers.
	 */
	public PlayerSelectionPane() {
		initializeGUI();
		registerHandlers();
	}

	/**
	 * Initializes the GUI components, such as labels/buttons, fonts, and layout
	 * constraints.
	 */
	private void initializeGUI() {
		Image logo = new Image("file:images/logo.png", 450, 195, true, true);
		ImageView logoImageView = new ImageView(logo);
		Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);

		singleplayerLabel.setFont(spaceFont);
		singleplayerLabel.setStyle("-fx-text-fill: #00ff5a");
		multiplayerLabel.setFont(spaceFont);
		multiplayerLabel.setStyle("-fx-text-fill: #00ff5a");
		backLabel.setFont(spaceFont);
		backLabel.setStyle("-fx-text-fill: #00ff5a");

		GridPane.setHalignment(singleplayerLabel, HPos.CENTER);
		GridPane.setHalignment(multiplayerLabel, HPos.CENTER);
		GridPane.setHalignment(backLabel, HPos.CENTER);

		ColumnConstraints col1Constraints = new ColumnConstraints();
		col1Constraints.setPercentWidth(100);

		RowConstraints row1Constraints = new RowConstraints();
		row1Constraints.setPercentHeight(50);

		GridPane.setHalignment(logoImageView, HPos.CENTER);

		this.add(logoImageView, 0, 0);
		this.add(singleplayerLabel, 0, 1);
		this.add(multiplayerLabel, 0, 2);
		this.add(backLabel, 0, 3);
		this.getColumnConstraints().addAll(col1Constraints);
		this.getRowConstraints().addAll(row1Constraints);
	}

	/**
	 * Returns the "Singeplayer" label from the main menu.
	 *
	 * @return The Label representing the "Singeplayer" option.
	 */
	public Label getSinglePlayerLabel() {
		return singleplayerLabel;
	}

	/**
	 * Returns the "Multiplayer" label from the main menu.
	 *
	 * @return The Label representing the "Multiplayer" option.
	 */
	public Label getMultiplayerLabel() {
		return multiplayerLabel;
	}

	/**
	 * Returns the "Back" label from the main menu.
	 *
	 * @return The Label representing the "Back" option.
	 */
	public Label getBackLabel() {
		return backLabel;
	}

	/**
	 * Registers event handlers for mouse hover effects on menu labels. Changes
	 * label text fill color when the mouse enters or exits the label area.
	 */
	private void registerHandlers() {
		singleplayerLabel.setOnMouseEntered(event -> {
			singleplayerLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		singleplayerLabel.setOnMouseExited(event -> {
			singleplayerLabel.setStyle("-fx-text-fill: #00ff5a");
		});
		multiplayerLabel.setOnMouseEntered(event -> {
			multiplayerLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		multiplayerLabel.setOnMouseExited(event -> {
			multiplayerLabel.setStyle("-fx-text-fill: #00ff5a");
		});
		backLabel.setOnMouseEntered(event -> {
			backLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		backLabel.setOnMouseExited(event -> {
			backLabel.setStyle("-fx-text-fill: #00ff5a");
		});
	}
}
