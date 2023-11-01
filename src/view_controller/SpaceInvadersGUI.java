package view_controller;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SpaceInvadersGUI extends Application {
	private BorderPane pane = new BorderPane();
	private TutorialPane tutorialPane = new TutorialPane();
	private Button tutorialButton;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		tutorialButton = new Button("Tutorial");
		BorderPane.setAlignment(tutorialButton, Pos.CENTER);
		BorderPane.setMargin(tutorialButton, new Insets(0, 0, 10, 0));
		pane.setBottom(tutorialButton);

		initTutorialWindow(stage);

		Scene scene = new Scene(pane, 600, 400);
		stage.setTitle("Space Invaders");
		stage.setScene(scene);
		stage.show();
	}

	private void initTutorialWindow(Stage stage) {
		tutorialButton.setOnAction(event -> {
			Scene tutorialScene = new Scene(tutorialPane, 400, 300);
			Stage tutorialWindow = new Stage();

			// Forces application focus on tutorial window
			tutorialWindow.initOwner(stage);
			tutorialWindow.initModality(Modality.WINDOW_MODAL);

			tutorialWindow.setTitle("Tutorial");
			tutorialWindow.setScene(tutorialScene);
			tutorialWindow.setHeight(300);
			tutorialWindow.setWidth(400);
			tutorialWindow.setResizable(false);
			tutorialWindow.setX(stage.getX() + 50);
			tutorialWindow.setY(stage.getY() + 50);
			tutorialWindow.show();
		});
	}
}
