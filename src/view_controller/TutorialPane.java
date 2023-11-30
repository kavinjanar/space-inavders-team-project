package view_controller;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * The TutorialPane class represents a custom JavaFX BorderPane used for
 * displaying a tutorial with multiple pages. It includes navigation buttons and
 * different tutorial content pages.
 */
public class TutorialPane extends BorderPane {
	private Label prevLabel = new Label("Previous");
	private Label nextLabel = new Label("Next");
	private Label backLabel = new Label("< Back");
	private Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);
	private Font spaceFontSmall = Font.loadFont("file:fonts/space_invaders.ttf", 36);
	private ArrayList<Node> tutorialPaneList = new ArrayList<>();
	private int tutorialPageIndex;

	/**
	 * Construct a TutorialPane object.
	 */
	public TutorialPane() {
		tutorialPageIndex = 0;
		initTutorialPanes();
		layoutGUI();
		registerHandlers();
		updateButtonState();
	}

	/**
	 * Initialize basic layout for all panes.
	 */
	private void layoutGUI() {
		backLabel.setFont(spaceFont);
		backLabel.setStyle("-fx-text-fill: #00ff5a");
		prevLabel.setFont(spaceFont);
		prevLabel.setStyle("-fx-text-fill: #00ff5a");
		nextLabel.setFont(spaceFont);
		nextLabel.setStyle("-fx-text-fill: #00ff5a");

		GridPane buttonGrid = new GridPane();
		buttonGrid.add(prevLabel, 0, 0);
		buttonGrid.add(nextLabel, 1, 0);
		buttonGrid.setAlignment(Pos.CENTER);
		buttonGrid.setHgap(40);

		BorderPane.setMargin(backLabel, new Insets(20, 0, 0, 20));
		GridPane.setMargin(prevLabel, new Insets(0, 0, 20, 0));
		GridPane.setMargin(nextLabel, new Insets(0, 0, 20, 0));

		this.setStyle("-fx-background-color: black;");
		this.setTop(backLabel);
		this.setBottom(buttonGrid);
	}

	/**
	 * Return the "back" label object.
	 * 
	 * @return backLabel The "back" label object.
	 */
	public Label getBackLabel() {
		return backLabel;
	}

	/**
	 * Updates the state of the on buttons to enabled/disabled depending on the
	 * current page.
	 */
	private void updateButtonState() {
		prevLabel.setDisable(tutorialPageIndex == 0);
		nextLabel.setDisable(tutorialPageIndex == tutorialPaneList.size() - 1);
	}

	/**
	 * Register event handlers for mouse clicks and hovers.
	 */
	private void registerHandlers() {
		prevLabel.setOnMouseClicked(event -> {
			this.setCenter(tutorialPaneList.get(--tutorialPageIndex));
			updateButtonState();
		});
		prevLabel.setOnMouseEntered(event -> {
			prevLabel.setStyle("-fx-text-fill: #79FFA8");
		});
		prevLabel.setOnMouseExited(event -> {
			prevLabel.setStyle("-fx-text-fill: #00ff5a");
		});
		nextLabel.setOnMouseClicked(event -> {
			this.setCenter(tutorialPaneList.get(++tutorialPageIndex));
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

	/**
	 * Initializes the tutorial panes with content and sets up the initial layout.
	 */
	private void initTutorialPanes() {
		Label tutorialText1 = new Label("Player 1 controls");
		Label tutorialText2 = new Label("Player 2 controls");
		Label tutorialText3 = new Label("Each invader awards different point values");
		Label tutorialText4 = new Label("After completing a level, the player can change their ship");
		setLabelProperties(tutorialText1);
		setLabelProperties(tutorialText2);
		tutorialText2.setStyle("-fx-text-fill: #ff7f7f");
		setLabelProperties(tutorialText3);
		setLabelProperties(tutorialText4);

		ColumnConstraints col0Constraints = new ColumnConstraints();
		col0Constraints.setPercentWidth(100);

		RowConstraints row0Constraints = new RowConstraints();
		row0Constraints.setPercentHeight(30);

		RowConstraints row3Constraints = new RowConstraints();
		row3Constraints.setPercentHeight(40);

		GridPane player1Controls = getPlayer1Controls();
		GridPane page1 = new GridPane();
		page1.getColumnConstraints().setAll(col0Constraints);
		page1.getRowConstraints().setAll(row0Constraints);
		page1.add(tutorialText1, 0, 0);
		page1.add(player1Controls, 0, 1);

		GridPane keysGrid2 = getPlayer2Controls();
		GridPane page2 = new GridPane();
		page2.getColumnConstraints().setAll(col0Constraints);
		page2.getRowConstraints().setAll(row0Constraints);
		page2.add(tutorialText2, 0, 0);
		page2.add(keysGrid2, 0, 1);

		GridPane alienPointGrid = getInvaderPointGrid();
		GridPane page3 = new GridPane();
		page3.getColumnConstraints().setAll(col0Constraints);
		page3.getRowConstraints().setAll(row3Constraints);
		page3.add(tutorialText3, 0, 0);
		page3.add(alienPointGrid, 0, 1);
		
		Image changeShipScreenshot = new Image("file:images/changeShip.png", 600, 0, true, false);
		ImageView changeShipScreenshotView = new ImageView(changeShipScreenshot);
		GridPane.setHalignment(changeShipScreenshotView, HPos.CENTER);
		
		GridPane page4 = new GridPane();
		page4.getColumnConstraints().addAll(col0Constraints);
		page4.getRowConstraints().setAll(row3Constraints);
		page4.add(tutorialText4, 0, 0);
		page4.add(changeShipScreenshotView, 0, 1);

		tutorialPaneList.add(page1);
		tutorialPaneList.add(page2);
		tutorialPaneList.add(page3);
		tutorialPaneList.add(page4);

		this.setCenter(page1);
	}

	/**
	 * Creates a GridPane with controls and instructions for Player 1.
	 *
	 * @return GridPane representing Player 1 controls.
	 */
	private GridPane getPlayer1Controls() {
		Image keysImage = new Image("file:images/a_d_keys.png");
		ImageView keysImageView = new ImageView(keysImage);
		GridPane.setMargin(keysImageView, new Insets(10, 0, 40, 0));
		GridPane.setHalignment(keysImageView, HPos.CENTER);

		Image shiftKeyImage = new Image("file:images/shift_key.png");
		ImageView shiftKeyImageView = new ImageView(shiftKeyImage);
		GridPane.setMargin(shiftKeyImageView, new Insets(10, 0, 10, 0));
		GridPane.setHalignment(shiftKeyImageView, HPos.CENTER);

		Label moveText = new Label("Move left and right");
		setSubLabelProperties(moveText);
		GridPane.setMargin(moveText, new Insets(0, 0, 0, 0));
		GridPane.setHalignment(moveText, HPos.CENTER);

		Label shootText = new Label("Shoot");
		setSubLabelProperties(shootText);
		GridPane.setMargin(shootText, new Insets(0, 0, 0, 0));
		GridPane.setHalignment(shootText, HPos.CENTER);

		GridPane keysGrid = new GridPane();
		keysGrid.setAlignment(Pos.CENTER);
		keysGrid.add(moveText, 0, 0);
		keysGrid.add(keysImageView, 0, 1);
		keysGrid.add(shootText, 0, 2);
		keysGrid.add(shiftKeyImageView, 0, 3);

		return keysGrid;
	}

	/**
	 * Creates a GridPane with controls and instructions for Player 2.
	 *
	 * @return GridPane representing Player 2 controls.
	 */
	private GridPane getPlayer2Controls() {
		Image keysImage = new Image("file:images/arrow_keys.png");
		ImageView keysImageView = new ImageView(keysImage);
		GridPane.setMargin(keysImageView, new Insets(10, 0, 40, 0));
		GridPane.setHalignment(keysImageView, HPos.CENTER);

		Image shiftKeyImage = new Image("file:images/space_bar.png");
		ImageView shiftKeyImageView = new ImageView(shiftKeyImage);
		GridPane.setMargin(shiftKeyImageView, new Insets(10, 0, 10, 0));
		GridPane.setHalignment(shiftKeyImageView, HPos.CENTER);

		Label moveText = new Label("Move left and right");
		setSubLabelProperties(moveText);
		GridPane.setMargin(moveText, new Insets(0, 0, 0, 0));
		GridPane.setHalignment(moveText, HPos.CENTER);

		Label shootText = new Label("Shoot");
		setSubLabelProperties(shootText);
		GridPane.setMargin(shootText, new Insets(0, 0, 0, 0));
		GridPane.setHalignment(shootText, HPos.CENTER);

		GridPane keysGrid = new GridPane();
		keysGrid.setAlignment(Pos.CENTER);
		keysGrid.add(moveText, 0, 0);
		keysGrid.add(keysImageView, 0, 1);
		keysGrid.add(shootText, 0, 2);
		keysGrid.add(shiftKeyImageView, 0, 3);

		return keysGrid;
	}

	/**
	 * Creates a GridPane with images and labels representing different point values
	 * for alien invaders.
	 *
	 * @return GridPane representing alien point values.
	 */
	private GridPane getInvaderPointGrid() {
		Label pointLabel10 = new Label("10 points");
		Label pointLabel20 = new Label("20 points");
		Label pointLabel30 = new Label("30 points");
		Label pointLabelMystery = new Label("?? points");
		setSubLabelProperties(pointLabel10);
		setSubLabelProperties(pointLabel20);
		setSubLabelProperties(pointLabel30);
		setSubLabelProperties(pointLabelMystery);

		Image invader1 = new Image("file:images/enemy1_1.png", 70, 0, true, false);
		Image invader2 = new Image("file:images/enemy2_1.png", 70, 0, true, false);
		Image invader3 = new Image("file:images/enemy3_1.png", 70, 60, false, false);
		Image invader4 = new Image("file:images/ufo.png", 90, 0, true, false);
		ImageView invader1ImageView = new ImageView(invader1);
		ImageView invader2ImageView = new ImageView(invader2);
		ImageView invader3ImageView = new ImageView(invader3);
		ImageView invader4ImageView = new ImageView(invader4);

		GridPane.setMargin(invader1ImageView, new Insets(5, 0, 0, 40));
		GridPane.setMargin(invader2ImageView, new Insets(5, 0, 0, 40));
		GridPane.setMargin(invader3ImageView, new Insets(5, 0, 0, 40));
		GridPane.setMargin(invader4ImageView, new Insets(5, 0, 0, 30));

		ColumnConstraints col1Constraints = new ColumnConstraints();
		col1Constraints.setPercentWidth(52);

		ColumnConstraints col2Constraints = new ColumnConstraints();
		col2Constraints.setPercentWidth(48);

		GridPane invaderGrid = new GridPane();
		invaderGrid.getColumnConstraints().addAll(col1Constraints, col2Constraints);
		invaderGrid.add(invader1ImageView, 1, 0);
		invaderGrid.add(pointLabel10, 0, 0);
		invaderGrid.add(invader2ImageView, 1, 1);
		invaderGrid.add(pointLabel20, 0, 1);
		invaderGrid.add(invader3ImageView, 1, 2);
		invaderGrid.add(pointLabel30, 0, 2);
		invaderGrid.add(invader4ImageView, 1, 3);
		invaderGrid.add(pointLabelMystery, 0, 3);

		return invaderGrid;
	}

	/**
	 * Sets the common properties for the provided label, such as font, text color,
	 * and alignment.
	 *
	 * @param label The Label to set properties for.
	 */
	private void setLabelProperties(Label label) {
		label.setFont(spaceFont);
		label.setStyle("-fx-text-fill: #00ff5a");
		label.setTextAlignment(TextAlignment.CENTER);
		label.setWrapText(true);
		GridPane.setMargin(label, new Insets(20, 20, 0, 20));
		GridPane.setHalignment(label, HPos.CENTER);
	}

	/**
	 * Sets the properties for sub-labels, which are smaller labels used for
	 * additional information.
	 *
	 * @param label The Label to set properties for.
	 */
	private void setSubLabelProperties(Label label) {
		label.setFont(spaceFontSmall);
		label.setStyle("-fx-text-fill: #ffffff");
		label.setTextAlignment(TextAlignment.CENTER);
		label.setWrapText(true);
		GridPane.setHalignment(label, HPos.RIGHT);
		GridPane.setMargin(label, new Insets(10, 0, 0, 20));
	}
}
