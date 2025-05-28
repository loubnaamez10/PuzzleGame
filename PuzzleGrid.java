package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class PuzzleGrid {
    private int gridSize;
    private Button[][] tiles;
    private int emptyRow, emptyCol;
    private GameController controller;
    private List<Integer> initialState;

    public PuzzleGrid(int gridSize, GameController controller) {
        this.gridSize = gridSize;
        this.controller = controller;
    }

    public GridPane getGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        tiles = new Button[gridSize][gridSize];

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < gridSize * gridSize; i++) {
            numbers.add(i);
        }
        numbers.add(0); 
        Collections.shuffle(numbers);
        // Sauvegarde l'état initial
        initialState = new ArrayList<>(numbers);

        int index = 0;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                int num = numbers.get(index++);
                Button tile = new Button(num == 0 ? "" : String.valueOf(num));
                tile.setMinSize(80, 80);
                tile.setStyle("-fx-font-size: 20px");
                tile.getStyleClass().add("tile-button");


                if (num == 0) {
                    emptyRow = row;
                    emptyCol = col;
                }

                final int r = row;
                final int c = col;
                tile.setOnAction(_ -> handleClick(r, c));
                tiles[row][col] = tile;
                grid.add(tile, col, row);
            }
        }
        return grid;
    }

    private void handleClick(int row, int col) {
        if (isAdjacent(row, col)) {
            swapTiles(row, col, emptyRow, emptyCol);
            emptyRow = row;
            emptyCol = col;
            controller.incrementMoves();
            if (isSolved()) {
                controller.calculateScore();
                controller.showGameOver(true);
            }


        }
    }

    private boolean isAdjacent(int r, int c) {
        return (Math.abs(r - emptyRow) + Math.abs(c - emptyCol)) == 1;
    }

    private void swapTiles(int r1, int c1, int r2, int c2) {
        String text = tiles[r1][c1].getText();
        tiles[r1][c1].setText("");
        tiles[r2][c2].setText(text);
    }

    private boolean isSolved() {
        int expected = 1;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                String text = tiles[row][col].getText();
                if (row == gridSize - 1 && col == gridSize - 1) {
                    return text.equals(""); // dernière case vide
                }
                if (!text.equals(String.valueOf(expected))) {
                    return false;
                }
                expected++;
            }
        }
        return true;
    }
    
    public void resetToInitialState() {
        int index = 0;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                int num = initialState.get(index++);
                Button tile = tiles[row][col];
                tile.setText(num == 0 ? "" : String.valueOf(num));
                if (num == 0) {
                    emptyRow = row;
                    emptyCol = col;
                }
            }
        } 
    }

}
