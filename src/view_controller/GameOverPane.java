/**
 * 
 */
package view_controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
//        setupLeaderboard();
        loadAndUpdateLeaderboard();
    }
    
    public void setScore(int s) {
        this.score = s;
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
	
//	private void setupLeaderboard() {
//		Font spaceFontLarge = Font.loadFont("file:fonts/space_invaders.ttf", 72);
//        leaderboardLabel.setFont(spaceFontLarge);
//        leaderboardLabel.setStyle("-fx-text-fill: #FFFFFF");
//        GridPane.setHalignment(leaderboardLabel, HPos.CENTER);
//        Insets leaderboardPadding = new Insets(20, 0, 0, 0); 
//        GridPane.setMargin(leaderboardLabel, leaderboardPadding);
//        leaderboardPane.add(leaderboardLabel, 0, 0);
//	}
	
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
        // Or if the leaderboard has less than 10 entries
        if (leaderboardEntries.size() < 10) {
            return true;
        }
        return score > leaderboardEntries.get(9).getScore();
    }

    private void promptForLeaderboardEntry() {
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter Name");
        nameInput.setOnAction(event -> {
            String playerName = nameInput.getText();
            if (playerName != null && !playerName.trim().isEmpty()) {
                addScoreToLeaderboard(playerName, score);
            }
        });
        leaderboardPane.add(nameInput, 0, leaderboardEntries.size() + 1); // Add below the last entry
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

        // If the leaderboard has more than 10 entries, remove the lowest one
        if (leaderboardEntries.size() > 10) {
            leaderboardEntries.remove(10); // Remove the 11th entry (index 10)
        }

        // Save the sorted leaderboard
        LeaderboardManager.saveLeaderboard(leaderboardEntries, "leaderboard.dat");
        updateLeaderboardDisplay();
    }

    private void updateLeaderboardDisplay() {
        leaderboardPane.getChildren().clear(); // Clear existing entries
        leaderboardPane.add(leaderboardLabel, 0, 0); // The leaderboardLabel is already set up with a large font size and white color

        // Load the font
        Font spaceFont = Font.loadFont("file:fonts/space_invaders.ttf", 44);

        // Create labels for each leaderboard entry with the specified font and yellow color
        for (int i = 0; i < leaderboardEntries.size(); i++) {
            Label entryLabel = new Label(leaderboardEntries.get(i).toString());
            entryLabel.setFont(spaceFont); // Set the custom font
            entryLabel.setStyle("-fx-text-fill: yellow"); // Set the text color to yellow

            leaderboardPane.add(entryLabel, 0, i + 1); // Add below the leaderboard title
        }
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
