/**
 * 
 */
package view_controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.UnaryOperator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public final class GameOverPane extends GridPane {
	private Label gameOverLabel = new Label("Game Over");
	private Label restartLabel = new Label("Restart");
	private Label mainMenuLabel = new Label("Main Menu");
	private Label quitLabel = new Label("Quit");
	private Label leaderboardLabel = new Label("Leaderboard");
    private GridPane leaderboardPane = new GridPane();
    private int score;
    private List<LeaderboardEntry> leaderboardEntries;

    public GameOverPane() {
        initializeGUI();
        registerHandlers();
        flashGameOverLabel();
        loadAndUpdateLeaderboard();
    }
    
    
    public void setScore(int s) {
        this.score = s;
//        loadAndUpdateLeaderboard();
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
		
		ColumnConstraints leftColConstraints = new ColumnConstraints();
        leftColConstraints.setPercentWidth(50);

        ColumnConstraints rightColConstraints = new ColumnConstraints();
        rightColConstraints.setPercentWidth(50); // column for leaderboard
        
        this.getColumnConstraints().addAll(leftColConstraints, rightColConstraints);

		GridPane.setHalignment(gameOverLabel, HPos.CENTER);
		GridPane.setHalignment(restartLabel, HPos.CENTER);
		GridPane.setHalignment(mainMenuLabel, HPos.CENTER);
		GridPane.setHalignment(quitLabel, HPos.CENTER);
		GridPane.setMargin(gameOverLabel, new Insets(-74, 0, 30, 0));

		Image logo = new Image("file:images/logo.png", 450, 195, true, true);
		ImageView logoImageView = new ImageView(logo);

		GridPane.setHalignment(logoImageView, HPos.CENTER);
		
		ColumnConstraints col1Constraints = new ColumnConstraints();
		col1Constraints.setPercentWidth(100);

		RowConstraints row1Constraints = new RowConstraints();
		row1Constraints.setPercentHeight(50);

		this.add(logoImageView, 0, 0);
		this.add(gameOverLabel, 0, 1);
		this.add(restartLabel, 0, 2);
		this.add(mainMenuLabel, 0, 3);
		this.add(quitLabel, 0, 4);
		this.setStyle("-fx-background-color: black;");
//		this.getColumnConstraints().addAll(col1Constraints);
		this.getRowConstraints().addAll(row1Constraints);
		this.add(leaderboardPane, 1, 0, 1, 5);
	}
	

	
	private void loadAndUpdateLeaderboard() {
        // Set up leaderboard label with custom font and style
        Font spaceFontLarge = Font.loadFont("file:fonts/space_invaders.ttf", 72);
        leaderboardLabel.setFont(spaceFontLarge);
        leaderboardLabel.setStyle("-fx-text-fill: #FFFFFF");
        GridPane.setHalignment(leaderboardLabel, HPos.CENTER);
        Insets leaderboardPadding = new Insets(20, 0, 0, 0); 
        GridPane.setMargin(leaderboardLabel, leaderboardPadding);
        leaderboardPane.add(leaderboardLabel, 0, 0);

        // Load and display leaderboard entries
        leaderboardEntries = LeaderboardManager.loadLeaderboard("leaderboard.dat");
        updateLeaderboardDisplay();
        
        if (qualifiesForLeaderboard(score)) {
            promptForLeaderboardEntry();
        }
    }
	
	private boolean qualifiesForLeaderboard(int score) {
        // Check if the score is higher than the lowest score in the leaderboard
        // Or if the leaderboard has less than 8 entries
        if (leaderboardEntries.size() < 8) {
            return true;
        }
        return score >= leaderboardEntries.get(7).getScore();
    }

	private void promptForLeaderboardEntry() {
	    // Load the font
	    Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);

	    // Create the text field for name entry
	    TextField nameInput = new TextField();
	    nameInput.setFont(spaceFont); // Set the custom font
	    nameInput.setStyle(
	        "-fx-text-fill: yellow; " + // Set the text color to yellow
	        "-fx-background-color: transparent; " + // Set the background to transparent
	        "-fx-prompt-text-fill: #FFFFFF; " + // Set the prompt text color to white
	        "-fx-background-insets: 0; " + // No padding within the background
	        "-fx-border-color: yellow; " + // Set border color if needed
	        "-fx-border-width: 2; " + // Set border width if needed
	        "-fx-border-insets: 0; " + // No padding within the border
	        "-fx-border-radius: 0; " + // Set to '0' to have no rounded corners
	        "-fx-background-radius: 0;" // Set to '0' to have no rounded corners
	    );

	    nameInput.setPromptText("Enter Name");

	    // Limit the number of characters to 10
	    UnaryOperator<TextFormatter.Change> characterLimitFilter = change -> {
	        if (change.getControlNewText().length() <= 10) {
	            return change; // Allow the change
	        }
	        return null; // Prevent the change
	    };
	    nameInput.setTextFormatter(new TextFormatter<>(characterLimitFilter));

	    nameInput.setOnAction(event -> {
	        String playerName = nameInput.getText();
	        if (playerName != null && !playerName.trim().isEmpty()) {
	            addScoreToLeaderboard(playerName, score);
	        }
	    });

	    // Add the text field to the leaderboardPane below the label
	    leaderboardPane.add(nameInput, 0, leaderboardEntries.size() + 1); // Adjust the row index as needed
	}


    private void addScoreToLeaderboard(String name, int score) {
        // Add new score to leaderboard
        leaderboardEntries.add(new LeaderboardEntry(name, score));

        // Sort the leaderboard in descending order of scores
        Collections.sort(leaderboardEntries, new Comparator<LeaderboardEntry>() {
            @Override
            public int compare(LeaderboardEntry entry1, LeaderboardEntry entry2) {
                return Integer.compare(entry2.getScore(), entry1.getScore()); // Note the order of entry2 and entry1
            }
        });

        // If the leaderboard has more than 8 entries, remove the lowest one
        if (leaderboardEntries.size() > 8) {
            leaderboardEntries.remove(8); // Remove the 11th entry (index 8)
        }

        // Save the sorted leaderboard
        LeaderboardManager.saveLeaderboard(leaderboardEntries, "leaderboard.dat");
        updateLeaderboardDisplay();
    }

    private void updateLeaderboardDisplay() {
        leaderboardPane.getChildren().clear(); // Clear existing entries
        leaderboardPane.add(leaderboardLabel, 0, 0, 2, 1); // Span 2 columns for the leaderboard title

        // Load the font
        Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);

        // Create labels for each leaderboard entry with the specified font and color
        int entriesToShow = Math.min(leaderboardEntries.size(), 8);
        for (int i = 0; i < entriesToShow; i++) {
            LeaderboardEntry entry = leaderboardEntries.get(i);

            // Name label
            Label nameLabel = new Label(entry.getName());
            nameLabel.setFont(spaceFont);
            nameLabel.setStyle("-fx-text-fill: yellow");
            GridPane.setHalignment(nameLabel, HPos.LEFT); // Align name to the left

            // Score label
            Label scoreLabel = new Label(String.format("%d", entry.getScore()));
            scoreLabel.setFont(spaceFont);
            scoreLabel.setStyle("-fx-text-fill: yellow");
            GridPane.setHalignment(scoreLabel, HPos.RIGHT); // Align score to the right

            // Add name and score labels to the grid pane
            leaderboardPane.add(nameLabel, 0, i + 1); // Add name label in the first column
            leaderboardPane.add(scoreLabel, 1, i + 1); // Add score label in the second column
        }

        // Adjust the column constraints if necessary
        ColumnConstraints nameCol = new ColumnConstraints();
        nameCol.setHgrow(Priority.ALWAYS); // Allow name column to grow
        nameCol.setHalignment(HPos.LEFT); // Align content to the left

        ColumnConstraints scoreCol = new ColumnConstraints();
        scoreCol.setHalignment(HPos.RIGHT); // Align content to the right

        // Set the column constraints on the leaderboardPane
        leaderboardPane.getColumnConstraints().setAll(nameCol, scoreCol);
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
	
	private void flashGameOverLabel() {
	    Timeline timeline = new Timeline(
	            new KeyFrame(Duration.seconds(0.75), new EventHandler<ActionEvent>() {
	                private boolean white = true;

	                @Override
	                public void handle(ActionEvent event) {
	                    if (white) {
	                        gameOverLabel.setStyle("-fx-text-fill: #000000");
	                    } else {
	                        gameOverLabel.setStyle("-fx-text-fill: #FFFFFF");
	                    }
	                    
	                    white = !white;
	                }
	            })
	    );
	    
	    timeline.setCycleCount(Timeline.INDEFINITE);
	    timeline.play();
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
