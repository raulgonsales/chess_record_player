package main.java.models;

import javafx.scene.layout.*;

public class BoardField extends StackPane {
    private int col;
    private int row;

    BoardField(int col, int row) {
        this.col = col;
        this.row = row;
        setMaxWidth(70);
        setMaxHeight(70);

        this.setOnMouseClicked(event -> {
            System.out.println(this.col);
            System.out.println(this.row);
            System.out.println(this);
        });
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
