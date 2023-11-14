/**
 * 
 */
package view_controller;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public final class GameOverPane extends GridPane {
	private Label gameOverLabel = new Label("Game Over");
	private Label restartLabel = new Label("Restart");
	private Label mainMenuLabel = new Label("Main Menu");
	private Label quitLabel = new Label("Quit");

	public GameOverPane() {
		initializeGUI();
		registerHandlers();
	}

	private void initializeGUI() {
		Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);
		Font spaceFontLarge = Font.loadFont("file:fonts/space_invaders.ttf", 72);

		gameOverLabel.setFont(spaceFontLarge);
		gameOverLabel.setStyle("-fx-text-fill: #FFFFFF");
		restartLabel.setFont(spaceFont);
		restartLabel.setStyle("-fx-text-fill: #00ff5a");
		mainMenuLabel.setFont(spaceFont);
		mainMenuLabel.setStyle("-fx-text-fill: #00ff5a");
		quitLabel.setFont(spaceFont);
		quitLabel.setStyle("-fx-text-fill: #00ff5a");

		GridPane.setHalignment(gameOverLabel, HPos.CENTER);
		GridPane.setHalignment(restartLabel, HPos.CENTER);
		GridPane.setHalignment(mainMenuLabel, HPos.CENTER);
		GridPane.setHalignment(quitLabel, HPos.CENTER);
		GridPane.setMargin(gameOverLabel, new Insets(0, 0, 30, 0));

		ColumnConstraints col1Constraints = new ColumnConstraints();
		col1Constraints.setPercentWidth(100);

		RowConstraints row1Constraints = new RowConstraints();
		row1Constraints.setPercentHeight(50);

		Image logo = new Image("file:images/logo.png", 450, 195, true, true);
		ImageView logoImageView = new ImageView(logo);

		GridPane.setHalignment(logoImageView, HPos.CENTER);

		this.add(logoImageView, 0, 0);
		this.add(gameOverLabel, 0, 1);
		this.add(restartLabel, 0, 2);
		this.add(mainMenuLabel, 0, 3);
		this.add(quitLabel, 0, 4);
		this.setStyle("-fx-background-color: black;");
		this.getColumnConstraints().addAll(col1Constraints);
		this.getRowConstraints().addAll(row1Constraints);
	}

	public Label getRestartLabel() {
		return restartLabel;
	}

	public Label getMainMenuLabel() {
		return mainMenuLabel;
	}

	public Label getQuitLabel() {
		return quitLabel;
	}

	private void registerHandlers() {
		restartLabel.setOnMouseEntered(event -> {
			restartLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		restartLabel.setOnMouseExited(event -> {
			restartLabel.setStyle("-fx-text-fill: #00ff5a");
		});
		mainMenuLabel.setOnMouseEntered(event -> {
			mainMenuLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		mainMenuLabel.setOnMouseExited(event -> {
			mainMenuLabel.setStyle("-fx-text-fill: #00ff5a");
		});
		quitLabel.setOnMouseEntered(event -> {
			quitLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		quitLabel.setOnMouseExited(event -> {
			quitLabel.setStyle("-fx-text-fill: #00ff5a");
		});
	}
}
