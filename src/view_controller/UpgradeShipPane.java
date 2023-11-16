package view_controller;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.SpaceShip;

public final class UpgradeShipPane extends GridPane {
	
	private Label promptLabel = new Label("Would you like to change your ship?");
	private Label nameLabel = new Label("Name: ");
	private Label livesLabel = new Label("Lives: ");
	private Label speedLabel = new Label("Speed: ");
	private Label nameLabel2 = new Label("Name: ");
	private Label livesLabel2 = new Label("Lives: ");
	private Label speedLabel2 = new Label("Speed: ");
	
	public UpgradeShipPane()
	{
		initializeGUI();
		registerHandlers();
	}
	
	private void initializeGUI() {
		Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 36);
		Font spaceFontLarge = Font.loadFont("file:fonts/space_invaders.ttf", 72);

		promptLabel.setFont(spaceFontLarge);
		promptLabel.setStyle("-fx-text-fill: #FFFFFF");
		nameLabel.setFont(spaceFont);
		nameLabel.setStyle("-fx-text-fill: #00ff5a");
		livesLabel.setFont(spaceFont);
		livesLabel.setStyle("-fx-text-fill: #00ff5a");
		speedLabel.setFont(spaceFont);
		speedLabel.setStyle("-fx-text-fill: #00ff5a");
		nameLabel2.setFont(spaceFont);
		nameLabel2.setStyle("-fx-text-fill: #00ff5a");
		livesLabel2.setFont(spaceFont);
		livesLabel2.setStyle("-fx-text-fill: #00ff5a");
		speedLabel2.setFont(spaceFont);
		speedLabel2.setStyle("-fx-text-fill: #00ff5a");

		GridPane.setHalignment(promptLabel, HPos.CENTER);
		GridPane.setHalignment(nameLabel, HPos.CENTER);
		GridPane.setHalignment(livesLabel, HPos.CENTER);
		GridPane.setHalignment(speedLabel, HPos.CENTER);
		GridPane.setHalignment(nameLabel2, HPos.CENTER);
		GridPane.setHalignment(livesLabel2, HPos.CENTER);
		GridPane.setHalignment(speedLabel2, HPos.CENTER);
		GridPane.setMargin(promptLabel, new Insets(-74, 0, 30, 0));

		ColumnConstraints col1Constraints = new ColumnConstraints();
		col1Constraints.setPercentWidth(100);

		RowConstraints row1Constraints = new RowConstraints();
		row1Constraints.setPercentHeight(50);

		Image logo = new Image("file:images/logo.png", 450, 195, true, true);
		ImageView logoImageView = new ImageView(logo);

		GridPane.setHalignment(logoImageView, HPos.CENTER);

		this.add(logoImageView, 0, 0);
		this.add(promptLabel, 0, 1);
		this.add(nameLabel, 0, 2);
		this.add(livesLabel, 0, 3);
		this.add(speedLabel, 0, 4);
		this.add(nameLabel2, 0, 5);
		this.add(livesLabel2, 0, 6);
		this.add(speedLabel2, 0, 7);
		this.setStyle("-fx-background-color: black;");
		this.getColumnConstraints().addAll(col1Constraints);
		this.getRowConstraints().addAll(row1Constraints);
	}
	
	public void setShipInfo(ArrayList<SpaceShip> shipList, SpaceShip currShip)
	{
		nameLabel.setText("Name: " + shipList.get(0).getName());
		livesLabel.setText("Lives: " + (shipList.get(0).getLives() - currShip.getLives()));
		speedLabel.setText("Speed: " + (shipList.get(0).getSpeed() - currShip.getSpeed()));
	}

	public Label getNameLabel() {
		return nameLabel;
	}

	public Label getLivesLabel() {
		return livesLabel;
	}

	public Label getSpeedLabel() {
		return speedLabel;
	}
	
	private void flashGameOverLabel() {
	    Timeline timeline = new Timeline(
	            new KeyFrame(Duration.seconds(0.75), new EventHandler<ActionEvent>() {
	                private boolean white = true;

	                @Override
	                public void handle(ActionEvent event) {
	                    if (white) {
	                        promptLabel.setStyle("-fx-text-fill: #000000");
	                    } else {
	                        promptLabel.setStyle("-fx-text-fill: #FFFFFF");
	                    }
	                    
	                    white = !white;
	                }
	            })
	    );
	    
	    timeline.setCycleCount(Timeline.INDEFINITE);
	    timeline.play();
	}

	private void registerHandlers() {
		nameLabel.setOnMouseEntered(event -> {
			nameLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		nameLabel.setOnMouseExited(event -> {
			nameLabel.setStyle("-fx-text-fill: #00ff5a");
		});
		livesLabel.setOnMouseEntered(event -> {
			livesLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		livesLabel.setOnMouseExited(event -> {
			livesLabel.setStyle("-fx-text-fill: #00ff5a");
		});
		speedLabel.setOnMouseEntered(event -> {
			speedLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		speedLabel.setOnMouseExited(event -> {
			speedLabel.setStyle("-fx-text-fill: #00ff5a");
		});
	}
}
