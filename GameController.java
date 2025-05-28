package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController {
	 private Stage stage;
	    private int gridSize;
	    private boolean isMode; 
	    private int moveCount = 0; 
	    private int timeRemaining; 
	    private Label moveLabel, timerLabel; 
	    private Timeline timeline;
	    private int score = 0;
	    private Label scoreLabel;
	    private PuzzleGrid puzzleGrid;


	    
	    public GameController(Stage stage, int gridSize, boolean isMode) {
	        this.stage = stage;
	        this.gridSize = gridSize;
	        this.isMode = isMode;
	    }
	   
	    
	    private void startTimer() {
	        timeline = new Timeline(new KeyFrame(Duration.seconds(1), _ -> {
	            if (isMode) {
	                timeRemaining--;
	                if (timeRemaining <= 0) {
	                    timeline.stop();
	                    showGameOver(false);
	                }
	            } else {
	                timeRemaining++;
	            }
	            timerLabel.setText("Temps : " + timeRemaining + "s");
	            calculateScore();
	        }));
	        timeline.setCycleCount(Timeline.INDEFINITE);
	        timeline.play();
	    }

	    private void startCountdown() {
	        startTimer(); 
	    }

	    
	     public void startGame() {
	            moveCount = 0;
	            score = 0;

	            puzzleGrid = new PuzzleGrid(gridSize, this);
	            GridPane grid = puzzleGrid.getGrid();

	            moveLabel = new Label("Clicks : 0");
	            timerLabel = new Label();
	            scoreLabel = new Label("Score : 0");

	            if (isMode) {
	                timeRemaining = gridSize == 3 ? 60 : (gridSize == 4 ? 120 : 180);
	                timerLabel.setText("Temps : " + timeRemaining + "s");
	                startCountdown();
	            } else {
	                timeRemaining = 0;
	                timerLabel.setText("Temps : 0s");
	                startTimer();
	            }

	            Button shuffleButton = new Button("Shuffle");
	            shuffleButton.setOnAction(_ -> {
	                stopTimer();
	                startGame();
	            });

	            Button backButton = new Button("Back to Menu");
	            backButton.setOnAction(_ -> {
	                stopTimer();
	                new MenuScreen(stage).show();
	            });
	            
	            Button rearrangeButton = new Button("Rearrange");
	            rearrangeButton.setOnAction(_ -> puzzleGrid.resetToInitialState());


	            VBox layout = new VBox(10);
	            layout.setAlignment(Pos.CENTER);
	            layout.getChildren().addAll(moveLabel, timerLabel, scoreLabel, grid, shuffleButton, rearrangeButton, backButton);

	            Scene scene = new Scene(layout, 400, 500);
	            stage.setTitle("Your Puzzle " + gridSize + "×" + gridSize);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.setScene(scene);
	            stage.show();
	        }
    
	    
	    public void stopTimer() {
	        if (timeline != null) {
	            timeline.stop();
	        }
	    }
	        
	     
	    public void showGameOver(boolean won) {
	    	    if (timeline != null) {
	    	        timeline.stop();
	    	    }
	    	    

	    	    String message = won ? "Well done, you've won!" : "Time's up, you've lost.. No score sorry";
	    	    Label endMessage = new Label(message);
	    	    endMessage.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-font-family: 'Times New Roman';");

	    	    Label scoreLabel = new Label();
	    	    Label highScoreLabel = new Label();

	    	    if (won) {
	    	        calculateScore();
	    	        scoreLabel.setText("Your score: " + score);
	    	        scoreLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-font-family: 'Times New Roman';");

	    	        int previousHighScore = HighScoreManager.loadHighScore();

	    	        if (score > previousHighScore) {
	    	            HighScoreManager.saveHighScore(score);
	    	            highScoreLabel.setText("New High Score! Previous: " + previousHighScore);
	    	            highScoreLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-font-family: 'Times New Roman';");
	    	        } else {
	    	            highScoreLabel.setText("High Score: " + previousHighScore);
	    	            highScoreLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-font-family: 'Times New Roman';");
	    	        }     
	    	    }

	    	    Button backButton = new Button("Back to Menu");
	    	    backButton.setOnAction(_ -> new MenuScreen(stage).show());
	    	    backButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #6a11cb, #2575fc);\r\n"
	    	    		+ "    -fx-text-fill: white;\r\n"
	    	    		+ "    -fx-font-size: 13px;\r\n"
	    	    		+ "    -fx-font-family:'Times New Roman';\r\n"
	    	    		+ "    -fx-font-weight: bold;\r\n"
	    	    		+ "    -fx-padding: 10px;\r\n"
	    	    		+ "    -fx-background-radius: 12px;\r\n"
	    	    		+ "    -fx-cursor: hand;\r\n"
	    	    		+ "    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 1);");

	    	    VBox layout = new VBox(10, endMessage, scoreLabel, highScoreLabel, backButton);
	    	    layout.setAlignment(Pos.CENTER);
	    	    layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #dfe9f3, #ffffff);");
	    	    stage.setScene(new Scene(layout, 600, 500));

	    	}

	     
	     public void incrementMoves() {
	         moveCount++;
	         moveLabel.setText("Clicks : " + moveCount);
	         calculateScore();
	     }
	     
	     public void calculateScore() {
	    	    if (isMode) {
	    	    	score = (gridSize + moveCount * 1000 ) / timeRemaining;

	    	    } else {
	    	    	score = timeRemaining * 15 + (gridSize * 100) - moveCount * 3;

	    	    }
	    	    
	    	    if (scoreLabel != null) {
	    	        scoreLabel.setText("Score : " + score);
	    	    }
	    	}

	    }



