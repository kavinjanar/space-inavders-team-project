package view_controller;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class MainMenuPane extends GridPane {
	private Label startGameLabel = new Label("Start Game");
	private Label tutorialLabel = new Label("Tutorial");
	
	public MainMenuPane() {
		initializeGUI();
		registerHandlers();
	}
	
	private void initializeGUI() {
		Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);
		startGameLabel.setFont(spaceFont);
		startGameLabel.setStyle("-fx-text-fill: #00ff5a");
		startGameLabel.setLayoutX(50);
		startGameLabel.setLayoutY(50);
		
		tutorialLabel.setFont(spaceFont);
		tutorialLabel.setStyle("-fx-text-fill: #00ff5a");
		
		this.add(startGameLabel, 0, 0);
		this.add(tutorialLabel, 0, 1);
		this.setStyle("-fx-background-color: black;");
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
