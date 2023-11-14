package view_controller;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class TutorialPane extends BorderPane {
	private Label prevLabel = new Label("Previous");
	private Label nextLabel = new Label("Next");
	private Label backLabel = new Label("< Back");
	private Label tutorialText = new Label();
	private ArrayList<String> tutorialTextList = new ArrayList<String>();
	private int tutorialPageIndex;

	public TutorialPane() {
		tutorialPageIndex = 0;
		initTutorialTexts();
		layoutGUI();
		registerHandlers();
		updateButtonState();
	}

	private void layoutGUI() {
		Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);

		backLabel.setFont(spaceFont);
		backLabel.setStyle("-fx-text-fill: #00ff5a");
		tutorialText.setFont(spaceFont);
		tutorialText.setStyle("-fx-text-fill: #00ff5a");
		tutorialText.setText(tutorialTextList.get(tutorialPageIndex));
		tutorialText.setWrapText(true);
		prevLabel.setFont(spaceFont);
		prevLabel.setStyle("-fx-text-fill: #00ff5a");
		nextLabel.setFont(spaceFont);
		nextLabel.setStyle("-fx-text-fill: #00ff5a");

		GridPane buttonGrid = new GridPane();
		buttonGrid.add(prevLabel, 0, 0);
		buttonGrid.add(nextLabel, 1, 0);
		buttonGrid.setAlignment(Pos.CENTER);
		buttonGrid.setHgap(40);

		BorderPane.setMargin(tutorialText, new Insets(20, 20, 0, 20));
		BorderPane.setMargin(backLabel, new Insets(20, 0, 0, 20));
		GridPane.setMargin(prevLabel, new Insets(0, 0, 20, 0));
		GridPane.setMargin(nextLabel, new Insets(0, 0, 20, 0));

		this.setStyle("-fx-background-color: black;");
		this.setTop(backLabel);
		this.setCenter(tutorialText);
		this.setBottom(buttonGrid);
	}

	public Label getBackLabel() {
		return backLabel;
	}

	private void updateButtonState() {
		prevLabel.setDisable(tutorialPageIndex == 0);
		nextLabel.setDisable(tutorialPageIndex == tutorialTextList.size() - 1);
	}

	private void registerHandlers() {
		prevLabel.setOnMouseClicked(event -> {
			tutorialPageIndex--;
			tutorialText.setText(tutorialTextList.get(tutorialPageIndex));
			updateButtonState();
		});
		prevLabel.setOnMouseEntered(event -> {
			prevLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		prevLabel.setOnMouseExited(event -> {
			prevLabel.setStyle("-fx-text-fill: #00ff5a");
		});
		nextLabel.setOnMouseClicked(event -> {
			tutorialPageIndex++;
			tutorialText.setText(tutorialTextList.get(tutorialPageIndex));
			updateButtonState();
		});
		nextLabel.setOnMouseEntered(event -> {
			nextLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		nextLabel.setOnMouseExited(event -> {
			nextLabel.setStyle("-fx-text-fill: #00ff5a");
		});
		backLabel.setOnMouseEntered(event -> {
			backLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		backLabel.setOnMouseExited(event -> {
			backLabel.setStyle("-fx-text-fill: #00ff5a");
		});
	}

	private void initTutorialTexts() {
		tutorialTextList.add(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
		tutorialTextList.add("Tutorial page 2");
		tutorialTextList.add("Tutorial page 3");
		tutorialTextList.add("Tutorial page 4, etc.");
	}
}
