package view_controller;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class MainMenuPane extends GridPane {
	private Label startGameLabel = new Label("Start Game");
	private Label tutorialLabel = new Label("Tutorial");
	private Image logo = new Image("file:images/logo.png", 450, 195, false, false);

	public MainMenuPane() {
		initializeGUI();
		registerHandlers();
	}

	private void initializeGUI() {
		Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);
		startGameLabel.setFont(spaceFont);
		startGameLabel.setStyle("-fx-text-fill: #00ff5a");
		tutorialLabel.setFont(spaceFont);
		tutorialLabel.setStyle("-fx-text-fill: #00ff5a");
		GridPane.setHalignment(startGameLabel, HPos.CENTER);
		GridPane.setHalignment(tutorialLabel, HPos.CENTER);

		ColumnConstraints col1Constraints = new ColumnConstraints();
		col1Constraints.setPercentWidth(100);

		RowConstraints row1Constraints = new RowConstraints();
		row1Constraints.setPercentHeight(50);
		
		ImageView logoImageView = new ImageView(logo);
		GridPane.setHalignment(logoImageView, HPos.CENTER);

		this.add(logoImageView, 0, 0);
		this.add(startGameLabel, 0, 1);
		this.add(tutorialLabel, 0, 2);
		this.setStyle("-fx-background-color: black;");
		this.getColumnConstraints().addAll(col1Constraints);
		this.getRowConstraints().addAll(row1Constraints);
	}

	public Label getStartGameLabel() {
		return startGameLabel;
	}

	public Label getTutorialLabel() {
		return tutorialLabel;
	}

	private void registerHandlers() {
		this.startGameLabel.setOnMouseEntered(event -> {
			this.startGameLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		this.startGameLabel.setOnMouseExited(event -> {
			this.startGameLabel.setStyle("-fx-text-fill: #00ff5a");
		});
		this.tutorialLabel.setOnMouseEntered(event -> {
			this.tutorialLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		this.tutorialLabel.setOnMouseExited(event -> {
			this.tutorialLabel.setStyle("-fx-text-fill: #00ff5a");
		});
	}
}
