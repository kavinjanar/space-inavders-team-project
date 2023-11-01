package view_controller;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class TutorialPane extends BorderPane {
	private Button prevButton;
	private Button nextButton;
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
		prevButton = new Button("Previous");
		nextButton = new Button("Next");

		tutorialText.setText(tutorialTextList.get(tutorialPageIndex));
		tutorialText.setWrapText(true);

		GridPane buttonGrid = new GridPane();
		buttonGrid.add(prevButton, 0, 0);
		buttonGrid.add(nextButton, 1, 0);
		buttonGrid.setAlignment(Pos.CENTER);
		buttonGrid.setHgap(10);

		BorderPane.setMargin(tutorialText, new Insets(10, 10, 0, 10));
		GridPane.setMargin(prevButton, new Insets(0, 0, 10, 0));
		GridPane.setMargin(nextButton, new Insets(0, 0, 10, 0));

		this.setTop(tutorialText);
		this.setBottom(buttonGrid);
	}

	private void updateButtonState() {
		if (tutorialPageIndex == 0)
			prevButton.setDisable(true);
		else
			prevButton.setDisable(false);

		if (tutorialPageIndex == tutorialTextList.size() - 1)
			nextButton.setDisable(true);
		else
			nextButton.setDisable(false);
	}

	private void registerHandlers() {
		nextButton.setOnAction(event -> {
			tutorialPageIndex++;
			tutorialText.setText(tutorialTextList.get(tutorialPageIndex));
			updateButtonState();
		});

		prevButton.setOnAction(event -> {
			tutorialPageIndex--;
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
