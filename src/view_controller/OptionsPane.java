package view_controller;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class OptionsPane extends BorderPane {
	private Label backLabel = new Label("< Back");

	public OptionsPane() {
		layoutGUI();
		registerHandlers();
	}

	private void layoutGUI() {
		Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);

		backLabel.setFont(spaceFont);
		backLabel.setStyle("-fx-text-fill: #00ff5a");
		
		BorderPane.setMargin(backLabel, new Insets(20, 0, 0, 20));

		this.setStyle("-fx-background-color: black;");
		this.setTop(backLabel);
	}

	public Label getBackLabel() {
		return backLabel;
	}

	private void registerHandlers() {
		backLabel.setOnMouseEntered(event -> {
			backLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		backLabel.setOnMouseExited(event -> {
			backLabel.setStyle("-fx-text-fill: #00ff5a");
		});
	}
}
