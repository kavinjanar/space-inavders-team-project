package view_controller;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class PauseMenuPane extends GridPane {
	private Label pausedLabel = new Label("Paused");
	private Label resumeLabel = new Label("Resume");
	private Label restartLabel = new Label("Restart");
	private Label mainMenuLabel = new Label("Main Menu");
	
	public PauseMenuPane() {
		initializeGUI();
		registerHandlers();
	}
	
	private void initializeGUI() {
		Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);
		Font spaceFontLarge = Font.loadFont("file:fonts/space_invaders.ttf", 72);
		
		pausedLabel.setFont(spaceFontLarge);
		pausedLabel.setStyle("-fx-text-fill: #ffffff;");
		resumeLabel.setFont(spaceFont);
		resumeLabel.setStyle("-fx-text-fill: #00ff5a;");
		restartLabel.setFont(spaceFont);
		restartLabel.setStyle("-fx-text-fill: #00ff5a;");
		mainMenuLabel.setFont(spaceFont);
		mainMenuLabel.setStyle("-fx-text-fill: #00ff5a;");
		
		GridPane.setMargin(pausedLabel, new Insets(-74, 0, 30, 0));
		
		GridPane.setHalignment(pausedLabel, HPos.CENTER);
		GridPane.setHalignment(resumeLabel, HPos.CENTER);
		GridPane.setHalignment(restartLabel, HPos.CENTER);
		GridPane.setHalignment(mainMenuLabel, HPos.CENTER);
		
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
	
	public Label getResumeLabel() {
		return resumeLabel;
	}
	
	public Label getRestartLabel() {
		return restartLabel;
	}
	
	public Label getMainMenuLabel() {
		return mainMenuLabel;
	}
}
