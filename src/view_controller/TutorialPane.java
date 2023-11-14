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
		buttonGrid.setHgap(10);

		BorderPane.setMargin(tutorialText, new Insets(10, 10, 0, 10));
		GridPane.setMargin(prevLabel, new Insets(0, 0, 10, 0));
		GridPane.setMargin(nextLabel, new Insets(0, 0, 10, 0));

		this.setStyle("-fx-background-color: black;");
		this.setTop(tutorialText);
		this.setBottom(buttonGrid);
	}

	private void updateButtonState() {
		if (tutorialPageIndex == 0)
			prevLabel.setDisable(true);
		else
			prevLabel.setDisable(false);

		if (tutorialPageIndex == tutorialTextList.size() - 1)
			nextLabel.setDisable(true);
		else
			nextLabel.setDisable(false);
	}

	private void registerHandlers() {
		prevLabel.setOnMouseClicked(event -> {
			tutorialPageIndex--;
			tutorialText.setText(tutorialTextList.get(tutorialPageIndex));
			updateButtonState();
		});
		
		nextLabel.setOnMouseClicked(event -> {
			tutorialPageIndex++;
			tutorialText.setText(tutorialTextList.get(tutorialPageIndex));
			updateButtonState();
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
