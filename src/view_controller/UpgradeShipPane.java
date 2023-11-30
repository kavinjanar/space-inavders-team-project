package view_controller;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.SpaceShip;

/**
 * Constructs the window that appears after each round where
 * the player has the option to choose a different space ship
 */
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
	private ImageView shipImage1 = new ImageView();
	private ImageView shipImage2 = new ImageView();
	private SpaceShip spaceShip;

	public UpgradeShipPane() {
		initializeGUI();
		registerHandlers();
	}

	/**
	 * Initalizes the GUI for the Upgrade Ship Pane
	 */
	private void initializeGUI() {
		Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 36);
		Font spaceFontLarge = Font.loadFont("file:fonts/space_invaders.ttf", 60);

		Image img1 = new Image("file:images/Spaceship.png"); // Replace with your image path
		shipImage1.setImage(img1);
		shipImage1.setFitHeight(100); // Adjust size as needed
		shipImage1.setFitWidth(100); // Adjust size as needed
		shipImage1.setPreserveRatio(true);

		Image img2 = new Image("file:images/Spaceship2.png"); // Replace with your image path
		shipImage2.setImage(img2);
		shipImage2.setFitHeight(100); // Adjust size as needed
		shipImage2.setFitWidth(100); // Adjust size as needed
		shipImage2.setPreserveRatio(true);

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
		GridPane.setHalignment(shipImage1, HPos.CENTER);
		GridPane.setHalignment(shipImage2, HPos.CENTER);
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
		GridPane.setMargin(shipImage1, new Insets(10, 20, 0, 20)); // Image above nameLabel
		GridPane.setMargin(shipImage2, new Insets(10, 20, 0, 20)); // Image above nameLabel2
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
		this.add(shipImage1, 0, 1);
		this.add(nameLabel, 0, 2);
		this.add(livesLabel, 0, 3);
		this.add(speedLabel, 0, 4);
		this.add(shipImage2, 1, 1);
		this.add(nameLabel2, 1, 2);
		this.add(livesLabel2, 1, 3);
		this.add(speedLabel2, 1, 4);
		this.add(confirmLabel, 0, 5, 2, 1); // Spanning two columns
		this.add(cancelLabel, 0, 6, 2, 1); // Spanning two columns

		// Set grid pane style and padding
		this.setStyle("-fx-background-color: black;");
		this.setPadding(new Insets(20, 20, 20, 20)); // Padding around the entire grid
	}

	/**
	 * Sets the info of the upgradable ships based on the current ship for each player
	 * @param currShip - the SpaceShip object that the upgrade ship pane will modify
	 * @param player - a String that represents if the player is player 1 or player 2
	 */
	public void setShipInfo(SpaceShip currShip, String player) {
		promptLabel.setText("Do you want to change your ship, " + player + "?");
		spaceShip = currShip;

		if (currShip.getName().equals("Balanced Ship")) {
			shipImage1.setImage(new Image("file:images/Spaceship3.png"));
			nameLabel.setText("Name: Chunky Ship");
			livesLabel.setText("Lives: 5");
			speedLabel.setText("Speed: 3");

			shipImage2.setImage(new Image("file:images/Spaceship2.png"));
			nameLabel2.setText("Name: Speedy Ship");
			livesLabel2.setText("Lives: 2");
			speedLabel2.setText("Speed: 8");
		}
		if (currShip.getName().equals("Speedy Ship")) {
			shipImage1.setImage(new Image("file:images/Spaceship3.png"));
			nameLabel.setText("Name: Chunky Ship");
			livesLabel.setText("Lives: 5");
			speedLabel.setText("Speed: 3");

			shipImage2.setImage(new Image("file:images/Spaceship.png"));
			nameLabel2.setText("Name: Balanced Ship");
			livesLabel2.setText("Lives: 3");
			speedLabel2.setText("Speed: 5");
		}
		if (currShip.getName().equals("Chunky Ship")) {
			shipImage1.setImage(new Image("file:images/Spaceship2.png"));
			nameLabel.setText("Name: Speedy Ship");
			livesLabel.setText("Lives: 2");
			speedLabel.setText("Speed: 8");

			shipImage2.setImage(new Image("file:images/Spaceship.png"));
			nameLabel2.setText("Name: Balanced Ship");
			livesLabel2.setText("Lives: 3");
			speedLabel2.setText("Speed: 5");
		}
	}

	/**
	 * returns nameLabel
	 * @return - nameLabel
	 */
	public Label getNameLabel() {
		return nameLabel;
	}

	/**
	 * returns livesLabel
	 * @return - livesLabel
	 */
	public Label getLivesLabel() {
		return livesLabel;
	}

	/**
	 * returns speedLabel
	 * @return - speedLabel
	 */
	public Label getSpeedLabel() {
		return speedLabel;
	}

	/**
	 * returns nameLabel2
	 * @return - nameLabel2
	 */
	public Label getNameLabel2() {
		return nameLabel2;
	}

	/**
	 * returns livesLabel
	 * @return - livesLabel
	 */
	public Label getLivesLabel2() {
		return livesLabel2;
	}

	/**
	 * returns speedLabel2
	 * @return - speedLabel2
	 */
	public Label getSpeedLabel2() {
		return speedLabel2;
	}

	/**
	 * returns confirmLabel
	 * @return - confirmLabel
	 */
	public Label getConfirmLabel() {
		return confirmLabel;
	}

	/**
	 * returns cancelLabel
	 * @return - cancelLabel
	 */
	public Label getCancelLabel() {
		return cancelLabel;
	}

	/**
	 * Registers the handler for the confirmLabel, cancelLabel, and the nameLabels for both ships
	 */
	private void registerHandlers() {
		nameLabel.setOnMouseClicked(new ShipSelectionHandler());
		nameLabel2.setOnMouseClicked(new ShipSelectionHandler());

		confirmLabel.setOnMouseEntered(event -> {
			confirmLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		confirmLabel.setOnMouseExited(event -> {
			confirmLabel.setStyle("-fx-text-fill: #00ff5a");
		});

		cancelLabel.setOnMouseEntered(event -> {
			cancelLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		cancelLabel.setOnMouseExited(event -> {
			cancelLabel.setStyle("-fx-text-fill: #00ff5a");
		});
	}

	/**
	 * Mouse Event Handler that modifies the ship based on the player's choice
	 */
	public class ShipSelectionHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Label chosenShip = (Label) arg0.getSource();
			chosenShip.setStyle("-fx-text-fill: #79FFA8");
			if (chosenShip.equals(nameLabel))
				nameLabel2.setStyle("-fx-text-fill: #00ff5a");
			else if (chosenShip.equals(nameLabel2))
				nameLabel.setStyle("-fx-text-fill: #00ff5a");
			if (chosenShip.getText().equals("Name: Speedy Ship")) {
				spaceShip.changeToSpeedyShip();
				System.out.println("label clicked");
			}
			if (chosenShip.getText().equals("Name: Chunky Ship")) {
				spaceShip.changeToBulkyShip();
				System.out.println("label clicked");
			}
			if (chosenShip.getText().equals("Name: Balanced Ship")) {
				spaceShip.changeToBalancedShip();
			}
		}

	}
}
