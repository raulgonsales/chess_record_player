package main.java.models;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board extends StackPane {
    private BoardField[][] fields;
    private int boardSize;

    public Board(int boardSize) {
        Rectangle border = new Rectangle(600, 600);
        border.setFill(null);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(5);
        setAlignment(Pos.CENTER);
        getChildren().addAll(border);

        this.boardSize = boardSize;

        this.fields = new BoardField[this.boardSize + 2][this.boardSize + 2];

        initBoardFields();
    }

    private void initBoardFields() {
        for (int i = 0; i < this.boardSize + 2; i++) {
            for (int j = 0; j < this.boardSize + 2; j++) {
                BoardField field = new BoardField(i, j);
                this.fields[i][j] = field;
                field.setAlignment(Pos.BOTTOM_LEFT);
                field.setTranslateX(i*60);
                field.setTranslateY(-j*60);

                this.getChildren().add(field);
            }
        }
    }
}
