package view_controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUI extends Application {
	
	private BorderPane pane = new BorderPane();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		Scene scene = new Scene(pane, 600, 400);
		stage.setScene(scene);
		stage.show();
	}

}
