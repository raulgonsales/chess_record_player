package main.java.models;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoardField extends StackPane {
    private int col;
    private int row;

    public BoardField(int col, int row) {
        this.col = col;
        this.row = row;

        Rectangle border = new Rectangle(60, 60);
        border.setFill(null);
        border.setStroke(Color.BLACK);
        getChildren().addAll(border);

        this.setOnMouseClicked(event -> {
            System.out.println(this.col);
            System.out.println(this.row);
        });
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
