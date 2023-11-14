package view_controller;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class PlayerSelectionPane extends GridPane {
	private Label singleplayerLabel = new Label("Singleplayer");
	private Label multiplayerLabel = new Label("Multiplayer");
	private Label backLabel = new Label("Back");

	public PlayerSelectionPane() {
		initializeGUI();
		registerHandlers();
	}

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

	public Label getSinglePlayerLabel() {
		return singleplayerLabel;
	}

	public Label getMultiplayerLabel() {
		return multiplayerLabel;
	}

	public Label getBackLabel() {
		return backLabel;
	}

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
