package view_controller;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.SpaceShip;

public final class UpgradeShipPane extends GridPane {
	
	private Label promptLabel = new Label("Do you want to change your ship?");
	private Label nameLabel = new Label("Name: ");
	private Label livesLabel = new Label("Lives: ");
	private Label speedLabel = new Label("Speed: ");
	private Label nameLabel2 = new Label("Name: ");
	private Label livesLabel2 = new Label("Lives: ");
	private Label speedLabel2 = new Label("Speed: ");
	private Label confirmLabel = new Label("Confirm");
	private Label cancelLabel = new Label("Cancel");
	private SpaceShip spaceShip;
	
	public UpgradeShipPane()
	{
		initializeGUI();
		registerHandlers();
	}
	
	private void initializeGUI() {
	    Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 36);
	    Font spaceFontLarge = Font.loadFont("file:fonts/space_invaders.ttf", 72);

	    // Set fonts and styles for labels
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
	    confirmLabel.setFont(spaceFont);
	    confirmLabel.setStyle("-fx-text-fill: #00ff5a");
	    cancelLabel.setFont(spaceFont);
	    cancelLabel.setStyle("-fx-text-fill: #00ff5a");

	    // Set alignments for labels
	    GridPane.setHalignment(promptLabel, HPos.CENTER);
	    GridPane.setHalignment(nameLabel, HPos.CENTER);
	    GridPane.setHalignment(livesLabel, HPos.CENTER);
	    GridPane.setHalignment(speedLabel, HPos.CENTER);
	    GridPane.setHalignment(nameLabel2, HPos.CENTER);
	    GridPane.setHalignment(livesLabel2, HPos.CENTER);
	    GridPane.setHalignment(speedLabel2, HPos.CENTER);
	    GridPane.setHalignment(confirmLabel, HPos.CENTER);
	    GridPane.setHalignment(cancelLabel, HPos.CENTER);

	    // Set margins for labels
	    GridPane.setMargin(promptLabel, new Insets(20, 0, 20, 0)); // Top, Right, Bottom, Left
	    GridPane.setMargin(nameLabel, new Insets(10, 20, 10, 20)); // Add margins to prevent touching sides
	    GridPane.setMargin(livesLabel, new Insets(10, 20, 10, 20));
	    GridPane.setMargin(speedLabel, new Insets(10, 20, 10, 20));
	    GridPane.setMargin(nameLabel2, new Insets(10, 20, 10, 20));
	    GridPane.setMargin(livesLabel2, new Insets(10, 20, 10, 20));
	    GridPane.setMargin(speedLabel2, new Insets(10, 20, 10, 20));
	    GridPane.setMargin(confirmLabel, new Insets(20, 0, 20, 0));
	    GridPane.setMargin(cancelLabel, new Insets(20, 0, 20, 0));

	    // Define column constraints
	    ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(50); // Adjust as needed
	    ColumnConstraints column2 = new ColumnConstraints();
	    column2.setPercentWidth(50); // Adjust as needed
	    this.getColumnConstraints().addAll(column1, column2);

	    // Add components to grid
	    this.add(promptLabel, 0, 0, 2, 1); // Spanning two columns
	    this.add(nameLabel, 0, 1);
	    this.add(livesLabel, 0, 2);
	    this.add(speedLabel, 0, 3);
	    this.add(nameLabel2, 1, 1);
	    this.add(livesLabel2, 1, 2);
	    this.add(speedLabel2, 1, 3);
	    this.add(confirmLabel, 0, 4, 2, 1); // Spanning two columns
	    this.add(cancelLabel, 0, 5, 2, 1); // Spanning two columns

	    // Set grid pane style and padding
	    this.setStyle("-fx-background-color: black;");
	    this.setPadding(new Insets(20, 20, 20, 20)); // Padding around the entire grid
	}

	
	public void setShipInfo(SpaceShip currShip)
	{
		
		spaceShip = currShip;
		
		if (currShip.getName().equals("Balanced Ship"))
		{
			nameLabel.setText("Name: Chunky Ship");
			livesLabel.setText("Lives: 5");
			speedLabel.setText("Speed: 3");
			
			nameLabel2.setText("Name: Speedy Ship");
			livesLabel2.setText("Lives: 2");
			speedLabel2.setText("Speed: 8");
		}
		if (currShip.getName().equals("Speedy Ship"))
		{
			nameLabel.setText("Name: Chunky Ship");
			livesLabel.setText("Lives: 5");
			speedLabel.setText("Speed: 3");
			
			nameLabel2.setText("Name: Balanced Ship");
			livesLabel2.setText("Lives: 3");
			speedLabel2.setText("Speed: 5");
		}
		if (currShip.getName().equals("Chunky Ship"))
		{
			nameLabel.setText("Name: Speedy Ship");
			livesLabel.setText("Lives: 2");
			speedLabel.setText("Speed: 8");
			
			nameLabel2.setText("Name: Balanced Ship");
			livesLabel2.setText("Lives: 3");
			speedLabel2.setText("Speed: 5");
		}
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
	
	public Label getConfirmLabel()
	{
		return confirmLabel;
	}
	public Label getCancelLabel()
	{
		return cancelLabel;
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
		nameLabel.setOnMouseClicked(new ShipSelectionHandler());
		
		nameLabel2.setOnMouseEntered(event -> {
			nameLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		nameLabel2.setOnMouseExited(event -> {
			nameLabel.setStyle("-fx-text-fill: #00ff5a");
		});
		
		nameLabel2.setOnMouseClicked(new ShipSelectionHandler());
		
	}
	
	public class ShipSelectionHandler implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Label chosenShip = (Label) arg0.getSource();
			if (chosenShip.getText().equals("Name: Speedy Ship"))
			{
				spaceShip.changeToSpeedyShip();
				System.out.println("label clicked");
			}
			if (chosenShip.getText().equals("Name: Chunky Ship"))
			{
				spaceShip.changeToBulkyShip();
				System.out.println("label clicked");
			}
			if (chosenShip.getText().equals("Name: Balanced Ship"))
			{
				spaceShip.changeToBalancedShip();
			}
		}
		
	}
}
